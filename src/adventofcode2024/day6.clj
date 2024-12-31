(ns adventofcode2024.day6
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [clojure.set :as set]))

(let [input "resources/day6/sample"
      grid (u/get-lines input)

      parse-line (fn[line]
                   "Maybe not needed"
                   (loop [line line
                          coll []]
                     (if (empty? line)
                       coll
                       (recur (rest line)
                              (conj coll
                                    (first line))))))

      in-grid (fn [grid
                   xy]
                (let [[x y] xy]
                  (get
                   (get grid y) x)
                  ))


      ]
  ;;  (-> lines first parse-line)
  (in-grid grid [4 6])

  )

(re-seq #"a|c" "abcde")
