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

      eval-with-prev (fn [pred
                          col]
                       "applies pred over sequence passing previous element as first param early out whenenver evaluates to false"
                       (println "\t" (get tostr pred) col)
                       (loop [prev nil
                              col col]
                         (let [item (first col)
                               eval (safe-check pred prev item)] 
                           ;;(println prev item eval)
                           (if (or (not eval)
                                   (empty? col))
                             eval
                             (recur (first col) (rest col))))))



      part1-check (fn[report]
                    (and
                     (or
                      (eval-with-prev > report)
                      (eval-with-prev < report))
                     (eval-with-prev max-diff report)))

      sample  (nth input 0)

      safe [1 3 6 7 9]


      permutate (fn [col] (cons col
                                (for [i (range (count col))]
                                  (vec (u/drop-nth i col)))))

      eval-with-possible-one-bad (fn [pred
                                      report]
                                   (println (get tostr pred) report)
                                   (some true?
                                         (map #(eval-with-prev pred %) (permutate report))))

      part2-check (fn[report]
                    (and
                     (or
                      (eval-with-possible-one-bad < report)
                      (eval-with-possible-one-bad > report))
                     (eval-with-possible-one-bad max-diff report)))

      ;;part1 (count (filter part1-check input))
      ;;part2 (count (filter part2-check input))
      ]

  (part2-check sample)

  ;;  (filter part2-check input)


  (count (filter part2-check input))
  )


