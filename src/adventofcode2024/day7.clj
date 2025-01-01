(ns adventofcode2024.day7
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [clojure.set :as set]))

(defn evaluate
  [equation]
  (let [{test-value :test-value
         numbers :numbers
         result :result }
        equation]
    (println equation)
    (if (empty? numbers)
      equation
      (evaluate
       (-> equation
           (update :numbers rest)
           (assoc :result (+ result (first numbers ))))
       )))
  )

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
                      :numbers numbers
                      :result 0 }
                     ))

      equations (map parse-line lines)
      ]

  (evaluate
   (first
    equations))
  )
