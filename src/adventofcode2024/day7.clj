(ns adventofcode2024.day7
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [zprint.core :as zp]
            [clojure.set :as set]))

(def text-formatting-width 80)

(defn evaluate
  [equs]
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
                   (evaluate
                    [(dopup equ +)
                     (dopup equ *)]
                    )
                   )))]
    (map doit equs)))

(let [
      input "resources/day7/sample" ;pt1 3749
      ;;      input "resources/day7/input" ; pt1: 6392012777720
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


      test1 (flatten (evaluate [(first equs)]))

      check-equ (fn [equ]
                  (->> (evaluate [equ])
                       flatten 
                       (filter #(= (:result %)
                                   (:test-value %)))
                       first))

      not-nil? (fn [item]
                 (not (nil? item)))

      part1 (->> equs
                 (map check-equ)
                 (filter not-nil?)
                 (map :result)
                 (reduce +))
      ]
  ;;  (zp/zprint test1 text-formatting-width)
  

  )
