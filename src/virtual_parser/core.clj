(ns virtual_parser.core
  (:require [virtual_parser.client :as cl]))

(defn run-cellbase
  "Cellbase annotation of VCF data via RESTful resource"
  [& args]
  (println "Cellbase annotation of VCF data via RESTful resource")
  (println (cl/query-cellbase :biotype 16 16244622 "C" "A"))
  )

(defn -main
  []
  (run-cellbase))
