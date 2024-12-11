(ns adventofcode2024.day3
  (:require [clojure.string :as str]
            [adventofcode2024.utils :as u]))

(def input "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
;; 161 (2*4 + 5*5 + 11*8 + 8*5).

(let [muls (re-seq #"mul\((\d{1,3}),(\d{1,3})\)" input)

      parse-mul (fn[mul] 
                  (let [[_ & factors] mul] (map Integer/parseInt factors)))
      ]
  (parse-mul
   (first muls)))


(->> ["123", "1231" "aba", "1"]
     (map #(re-matches #"\d{1,3}" %))
     (filter #(not (nil? %))))





