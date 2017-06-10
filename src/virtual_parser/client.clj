(ns virtual_parser.client
  (:require [clj-http.client :as client]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [com.tbaldridge.odin :as odin]
            [com.tbaldridge.odin.contexts.data :as odin-data]))

(def uri-string-map
  {:base-uri "http://bioinfo.hpc.cam.ac.uk/cellbase/webservices/rest"
   :version "v3"
   :organism "hsapiens"
   :query-type "genomic/variant"
   :query-type-suffix "annotation?type=json"
   })

(defn get-annotation
  "Return Cellbase annotation on a variant by variant basis"
  [chromosome genomic-coordinates ref alt]
  (clj-http.client/get (str/join "/" [(get uri-string-map :base-uri)
                                      (get uri-string-map :version)
                                      (get uri-string-map :organism)
                                      (get uri-string-map :query-type)
                                      #_"16:16244622:C:A"
                                      (str/join ":" [chromosome genomic-coordinates ref alt] )
                                      (get uri-string-map :query-type-suffix)] )))
(defn as-json
  "Convert response to JSON"
  [response]
  (json/read-str (:body response)
                 :key-fn keyword)
  )

(defn query-cellbase
  "Use Odin to retrieve values from nested map"
  [field-name chromosome genomic-coordinate ref-allele alt-allele]

  (set (odin/for-query
         (odin-data/query (as-json (get-annotation chromosome genomic-coordinate ref-allele alt-allele )) ?path field-name ?val) ?val)))
