(ns adventofcode2024.day3
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]))

(def input-pt1 "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

(def input-pt2 "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

;; 161 (2*4 + 5*5 + 11*8 + 8*5).
(def input (slurp "resources/day3/input")) ; Pt1 -> 184576302 
(def input input-pt2) ; Pt1 -> 184576302 

(s/def ::do (s/and string? #(= "do()" %)))
(s/def ::don't (s/and string? #(= "don't()" %)))
(s/def ::mul (s/coll-of integer? :count 2))

(s/valid? ::mul [2 2])
(s/valid? ::do "do()")
(s/valid? ::don't "don't()")

(let [muls (re-seq #"mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)" input) ;; sec of [a b c] will be produced

      parse-mul (fn[mul] 
                  (let [[_ & factors] mul] (map Integer/parseInt factors)))

      ;;      part1 (->> muls
      ;;                 (map parse-mul)
      ;;                 (map #(reduce * %))
      ;;                 (reduce +))



      ]
  muls
  )
