(ns adventofcode2024.day2
  (:require [clojure.string :as str]
            [adventofcode2024.utils :as u]))

(def input [[7 6 4 2 1]
            [1 2 7 8 9]
            [9 7 6 2 1]
            [1 3 2 4 5]
            [8 6 4 4 1]
            [1 3 6 7 9]])

(def input (u/get-lines "resources/day2/input"))

(let [parse-line (fn[line]
                   (->> line
                        (re-seq #"(\d+)\s*")
                        (map last)
                        (map Integer/parseInt)))

      parse-input (fn[lines]
                    (map parse-line lines))

      safe-check (fn [pred
                      a b]
                   (or (nil? a)
                       (nil? b)
                       (pred a b)))

      eval-with-prev (fn [pred
                          col]
                       "applies pred over sequence passing previous element as first param early out whenenver evaluates to false"
                       (loop [prev nil
                              col col]
                         (let [item (first col)
                               eval (safe-check pred prev item)] 
                           (println prev item eval)
                           (if (or (not eval)
                                   (empty? col))
                             eval
                             (recur (first col) (rest col))))))


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

      ]

  (filter part1-check
          (parse-input input)
          )

  ;;  {:a1 (eval-with-prev > safe)
  ;;   :a2 (eval-with-prev < safe)
  ;;   :max-diff (eval-with-prev max-diff safe) }




  )


