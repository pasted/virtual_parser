(ns virtual-parser.parser-test
  (:require [clojure.test :refer :all]
            [virtual_parser.parser :refer :all]))

(deftest read-gene-list-test
    (testing "FIXME, I fail."
      (is (= (virtual_parser.parser/read-gene-list "resources/test.gene-list.txt")))))