(ns adventofcode2024.day2
  (:require [clojure.string :as str]
            [adventofcode2024.utils :as u]))

(def input [[7 6 4 2 1]
            [1 2 7 8 9]
            [9 7 6 2 1]
            [1 3 2 4 5]
            [8 6 4 4 1]
            [1 3 6 7 9]])

(def input (u/parse-integer-lines (u/get-lines "resources/day2/input")))

(let [safe-check (fn [pred
                      a b]
                   (or (nil? a)
                       (nil? b)
                       (pred a b)))

      max-diff (fn [a b]
                 (let [diff (abs
                             (- a b))]
                   (and
                    (>= diff 1)
                    (<= diff 3)))
                 )

      tostr {> ">"
             < "<"
             max-diff "D" } 

      bad-level-count (fn [pred
                           col]
                        (loop [prev nil
                               col col]
                          (let [item (first col)
                                eval (safe-check pred prev item)] 
                            (if (or (not eval)
                                    (empty? col))
                              eval
                              (recur (first col) (rest col))))))


      permutate (fn [col] 
                  (for [i (range (count col))]
                    (vec (u/drop-nth i col))))

      part1-check (fn[report]
                    (and
                     (or
                      (bad-level-count > report)
                      (bad-level-count < report))
                     (bad-level-count max-diff report)))

      part2-check (fn [report]
                    (or (part1-check report)
                        (some true? (map part1-check  
                                         (permutate report)))))

      ]

  {:part1 (count (filter part1-check input))
   :part2 (count (filter part2-check input))})


