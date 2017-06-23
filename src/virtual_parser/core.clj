(ns virtual_parser.core
  (:require [virtual_parser.client :as cl]
            [virtual_parser.parser :as parser]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-v" "--vcf FILEPATH" "VCF filepath"]
   ["-g" "--genelist FILEPATH" "Gene symbol list filepath"]
   ["-h" "--help"]])

(defn run-cellbase
  "Cellbase annotation of VCF data via RESTful resource"
  [opts]
  (println opts)
  (println "Cellbase annotation of VCF data via RESTful resource")
  (let [{:keys [vcf genelist]} opts]
    (parser/read-vcf-file vcf))
  #_(println (cl/query-cellbase :biotype 16 16244622 "C" "A"))
  )

(defn -main
  [& args]
  (run-cellbase (parse-opts args cli-options)))
