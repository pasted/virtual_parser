(ns virtual_parser.client
  (:require [clj-http.client :as client]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [com.tbaldridge.odin :as odin]
            [com.tbaldridge.odin.contexts.data :as odin-data]
            [me.raynes.conch :as sh])
  (:import (htsjdk.tribble.readers TabixReader)))

(def cellbase-uri-string-map
  {:base-uri "http://bioinfo.hpc.cam.ac.uk/cellbase/webservices/rest"
   :version "v3"
   :organism "hsapiens"
   :query-type "genomic/variant"
   :response-type-suffix "annotation?type=json"
   })

(def hgnc-uri-string-map
  {:base-uri "http://rest.genenames.org"
   :query-type "search/symbol"
   :response-type {:accept :json}}
  )

(defn get-cellbase-annotation
  "Return Cellbase annotation on a variant by variant basis"
  [contig genomic-coordinates ref alt]
  (clj-http.client/get (str/join "/" [(get cellbase-uri-string-map :base-uri)
                                      (get cellbase-uri-string-map :version)
                                      (get cellbase-uri-string-map :organism)
                                      (get cellbase-uri-string-map :query-type)
                                      (str/join ":" [contig genomic-coordinates ref alt] )
                                      (get cellbase-uri-string-map :response-type-suffix)] )))

(defn get-hgnc-annotation
  "Return HGNC annotation for a given gene symbol"
  [gene-symbol]
  (clj-http.client/get (str/join "/" [(get hgnc-uri-string-map :base-uri)
                                      (get hgnc-uri-string-map :query-type)
                                      gene-symbol]) (get hgnc-uri-string-map :response-type)))

(defn as-json
  "Convert response to JSON"
  [response]
  (json/read-str (:body response)
                 :key-fn keyword))

(defn query-cellbase
  "Use Odin to retrieve values from nested map"
  [field-name chromosome genomic-coordinate ref-allele alt-allele]

  (set (odin/for-query
         (odin-data/query (as-json (get-cellbase-annotation chromosome genomic-coordinate ref-allele alt-allele )) ?path field-name ?val) ?val)))

(defn query-hgnc
  "Query HGNC api and return entry or empty seq is symbol is an exact match for top HGNC hit"
  [gene-symbol]
  (let [response (:response (as-json (get-hgnc-annotation gene-symbol)))
        max-score (:maxScore response)
        top-hit (filter (fn [this-map] (if (= (:score this-map) max-score) this-map)) (:docs response))]
    top-hit))

(defn query-gnomad
  "Use Tabix to retrieve GnomAD variant data"
  [url-str contig genomic-coordinates]
  (iterator-seq (.query (TabixReader. url-str) (str contig ":" genomic-coordinates))))

(defn sh-query-gnomad
  "Fall-over to shell command Tabix, require tabix to be installed in path"
  [url-str contig genomic-coordinates]
  (do
    (sh/programs tabix)
    (tabix url-str (str contig ":" genomic-coordinates))))

(defn sh-return-gnomad-header
  "Returns VCF header for GnomAD"
  [url-str]
  (do
    (sh/programs tabix)
    (tabix "-H" url-str )))

