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

(let [

      safe-check (fn [pred
                      a b]
                   (or (nil? a)
                       (nil? b)
                       (pred a b)))


      eval-with-prev-badlevels (fn [pred
                                    col]
                                 "applies pred over sequence passing previous element as first param early out whenenver evaluates to false"
                                 (loop [prev nil
                                        col col
                                        badlevels 0]
                                   (let [item (first col)
                                         eval (safe-check pred prev item)] 
                                     (if (empty? col)
                                       badlevels
                                       (recur (first col) (rest col) (if eval
                                                                       badlevels
                                                                       (inc badlevels))
                                              )))))

      eval-with-prev (fn [pred col]
                       (= 0
                          (eval-with-prev-badlevels pred col )))

      max-diff (fn [a b]
                 (let [diff (abs
                             (- a b))]
                   (and
                    (>= diff 1)
                    (<= diff 3)))
                 )

      part1-check (fn[report]
                    (and
                     (or
                      (eval-with-prev > report)
                      (eval-with-prev < report))
                     (eval-with-prev max-diff report)))

      sample  (nth input 0)

      safe [1 3 6 7 9]

      part1 (count (filter part1-check input))
      ]

  ;;  {:a1 (eval-with-prev > safe)
  ;;   :a2 (eval-with-prev < safe)
  ;;   :max-diff (eval-with-prev max-diff safe) }
  {:part1 part1
   :part2 (eval-with-prev-badlevels < sample)
   }

  )


