(ns virtual_parser.parser
  "Parse VCF format file"
  (:require [clojure.string :as str]
            [clojure.set :as set])
  (:import (htsjdk.variant.vcf VCFFileReader)
           (java.io FileReader)
           (htsjdk.variant.variantcontext VariantContext)))

(defn read-vcf-file
  "Reads in VCF format file returns a VCFFileReader"
   [filename]
  (VCFFileReader. (clojure.java.io/file filename)))

(defn get-genotypes
  "Iterates over VCFFileReader returns genotypes from each VariantContext"
  [filename]
  (doseq [^VariantContext variant (read-vcf-file filename)] (.getGenotypes variant)))

(defn get-sample-names
  "Accesses VCFFileReader returns first set of SampleNames"
  [filename]
  (let [rdr (read-vcf-file filename)] (.getSampleNames (first rdr))))

(defn get-variant-map
  "Returns map with keys :chr; :gstart; :ref; :alt from VariantContext"
  [^VariantContext variant-context]
  (let [chr (.getContig variant-context)
        gstart (.getStart variant-context)
        ref (clojure.string/replace (str (.getReference variant-context)) #"\*" "")
        alt (str (first (.getAlternateAlleles variant-context)))]
    (into {} [[:chr chr]
              [:gstart gstart]
              [:ref ref]
              [:alt alt]])))

(defn return-header
  "Returns VCF header"
  [^VCFFileReader filereader]
  (.getInfoHeaderLines (.getFileHeader filereader)))

(defn read-gene-list
  "Reads list of gene symbols - one per line, returns a set"
  [filename]
  (set (str/split (slurp filename) #"\n")))
