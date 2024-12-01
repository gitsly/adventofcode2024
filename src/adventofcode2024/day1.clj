(ns adventofcode2024.day1
  (:require [clojure.string :as str]
            [adventofcode2024.utils :as u]))

(def input (u/get-lines "resources/day1/sample"))

(let [
      parse-line (fn[line]
                   (->> line
                        (re-seq #"(\d+)\s*")
                        (map last)
                        (map Integer/parseInt)))

      parse-input (fn[lines]
                    (map parse-line lines))

      input (parse-input input)
      a (map first input)
      b (map second input)]

  {:a a
   :b b})

