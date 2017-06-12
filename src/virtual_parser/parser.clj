(ns virtual_parser.parser
  "Parse VCF format file"
  (:import (htsjdk.variant.vcf VCFFileReader)
           (java.io FileReader)))

(defn read-vcf-file
  "Reads in VCF format file returns a map"
   [filename]
  (VCFFileReader. (clojure.java.io/file filename))
  )
