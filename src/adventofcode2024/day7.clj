(ns adventofcode2024.day7
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [clojure.set :as set]))

(let [input "resources/day7/sample"
      lines (u/get-lines input)

      parse-line (fn [line]
                   (let [[test-value numbers] (str/split line #":" )
                         [res] (re-seq #"(\d+):( \d+)+" line)
                         numbers (vec (->> (re-seq #" \d+" numbers)
                                           (map str/trim)
                                           (map Integer/parseInt)))
                         ]
                     {:test-value (Integer/parseInt test-value)
                      :numbers numbers}
                     ))

      equations (map parse-line lines)
      ]

  equations
  )
