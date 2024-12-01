(ns adventofcode2024.day1
  (:require [clojure.string :as str]
            [adventofcode2024.utils :as u]))

(def input (u/get-lines "resources/day1/input"))

(let [
      parse-line (fn[line]
                   (->> line
                        (re-seq #"(\d+)\s*")
                        (map last)
                        (map Integer/parseInt)))
      parse-input (fn[lines]
                    (map parse-line lines))

      input (parse-input input)
      a (map first input) ; first liest
      b (map second input) ; second location list
      get-pair-info (fn[pair] (let [[a b] pair]
                                {:a a
                                 :b b
                                 :distance (abs (- a b) )}))

      similarity-score (fn[src
                           b]
                         (* src (count (filter #(= % src) b))))]

  (println 
   {:part1 (->> (map vector (sort a) (sort b))
                (map get-pair-info)
                (map :distance)
                (reduce +))
    :part2 
    (reduce + 
            (map #(similarity-score % b) a))}))



