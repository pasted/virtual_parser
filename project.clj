(defproject virtual_parser "0.1.0-SNAPSHOT"
  :description "Cellbase annotation of VCF data via RESTful resource"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "3.6.1"]
                 [com.tbaldridge/odin "0.2.0"]
                 [com.github.samtools/htsjdk "2.10.0"]
                 [org.clojure/tools.cli "0.3.5"]]
  :profiles {:uberjar {:aot :all}}
  :main virtual_parser.core)