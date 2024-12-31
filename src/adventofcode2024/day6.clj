(ns adventofcode2024.day6
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [clojure.set :as set]))

(let [input "resources/day6/sample"
      lines (u/get-lines input)

      parse-line (fn[line]
                   (loop [line line
                          coll []]
                     (if (empty? line)
                       coll
                       (recur (rest line)
                              (conj coll
                                    (first line))
                              ))))

      ]
  (-> lines
      first
      parse-line)
  )

(re-seq #"a|c" "abcde")
