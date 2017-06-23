(ns virtual_parser.parser
  "Parse VCF format file"
  (:import (htsjdk.variant.vcf VCFFileReader)
           (java.io FileReader)))

(defn read-vcf-file
  "Reads in VCF format file returns a VCFFileReader"
   [filename]
  (VCFFileReader. (clojure.java.io/file filename)))

(defn return-header
  "Returns VCF header"
  [^VCFFileReader filereader]
  (.getInfoHeaderLines (.getFileHeader filereader)))
