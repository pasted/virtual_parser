(ns virtual-parser.parser-test
  (:require [clojure.test :refer :all]
            [virtual_parser.parser :refer :all]))

(deftest read-gene-list-test
    (testing "read-gene-list: should return a set of gene symbols"
      (is (= (virtual_parser.parser/read-gene-list "resources/test.gene-list.txt") #{"RAD51C" "SOX2" "POMT2" "TBX15" "RAD51" "ALX3"}))))