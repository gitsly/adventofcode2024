(ns adventofcode2024.day3
  (:require [clojure.string :as str]
            [adventofcode2024.utils :as u]))

(def input "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

(re-seq #"mul\(\d{0,3}.*\)" input)

(->> ["123", "1231" "aba"]
     (map #(re-matches #"\d{0,3}" %))
     )





