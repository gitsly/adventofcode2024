(defproject adventofcode2024 "0.1.0-SNAPSHOT"
  :description "Advent of code solutions 2024"
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [zprint "1.2.8"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
