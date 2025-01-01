(ns adventofcode2024.day7
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [zprint.core :as zp]
            [clojure.set :as set]))

(def text-formatting-width 80)

(defn concatenation
  [a b]
  (Long/parseLong
   (str a b)))

(defn evaluate
  [equs
   ops]
  (let [doit (fn [equ] 
               (let [{test-value :test-value
                      numbers :numbers
                      result :result } equ
                     dopup (fn [equ op-fn]
                             (-> equ
                                 (update :numbers rest)
                                 (update :ops
                                         #(if (nil? %)
                                            []
                                            (conj % op-fn)))
                                 (assoc :result (op-fn result (first numbers )))))
                     ]
                 (if (empty? numbers)
                   equ
                   (evaluate ; recurse
                    (map #(dopup equ %) ops)
                    ops)
                   )))
        ]
    (map doit equs)))

(let [
      input "resources/day7/sample" ;pt1 3749
      input "resources/day7/input" ; pt1: 6392012777720
      lines (u/get-lines input)

      parse-line (fn [line]
                   (let [[test-value numbers] (str/split line #":" )
                         [res] (re-seq #"(\d+):( \d+)+" line)
                         numbers (vec (->> (re-seq #" \d+" numbers)
                                           (map str/trim)
                                           (map Integer/parseInt)))
                         ]
                     {:test-value (Long/parseLong test-value)
                      :numbers numbers
                      :result 0 }
                     ))

      equs (map parse-line lines)

      check-equ (fn [equ ops]
                  "Returns first equ with ops where result matches test-value"
                  (->> (evaluate [equ] ops)
                       flatten 
                       (filter #(= (:result %)
                                   (:test-value %)))
                       first))

      not-nil? (fn [item]
                 (not (nil? item)))

      total-calibration-result (fn [equs ops]
                                 (->> equs
                                      (map #(check-equ % ops))
                                      (filter not-nil?)
                                      (map :result)
                                      (reduce +))                     )

      part1  (total-calibration-result equs [+ *])
      part2  (total-calibration-result equs [+ * concatenation])]
  (println
   {:part1 part1
    :part2 part2 }))
