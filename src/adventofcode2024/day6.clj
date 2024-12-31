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

      find-start (fn [grid]
                   "Returns [x,y] vector where ^ is located"
                   (let [start-char \^
                         start-line (first (filter #(not (nil? (str/index-of % start-char))) grid))
                         x (str/index-of start-line start-char)
                         y (.indexOf grid start-line)]
                     [x y]))

      turn (fn [state]
             (let [dir (:dir state)]
               dir))

      state {:pos (find-start grid)
             :dir [0 -1]}
      ]
  ;;(in-grid grid [4 6])
  state


  )

;;(def v ["one" "two" "three" "two"])
;;(.indexOf v "two")

(str/index-of "cba" \a)
