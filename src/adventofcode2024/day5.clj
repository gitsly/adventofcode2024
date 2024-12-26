(ns adventofcode2024.day5
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [clojure.set :as set]))

(let [input "resources/day5/input" ; pt1 = 4996
      ;;     input "resources/day5/sample" ; pt1 = 143

      lines (u/get-lines input)

      [rules upd] (let [coll lines
                        pred (fn [item] (not (empty? item)))]
                    [(take-while pred coll)
                     (drop 1 (drop-while pred coll))])

      parse-page-order (fn[line]
                         "Returns a vector page number rule [before, after]"
                         (let [[_ before after] (first(re-seq #"(\d+)\|(\d+)" line))]
                           [(Integer/parseInt before)
                            (Integer/parseInt after)]))

      parse-update (fn [line]
                     (vec (map Integer/parseInt
                               (str/split line #","))))

      rules (map parse-page-order rules)
      upd (map parse-update upd)

      middle (fn [coll]
               (get (vec coll)
                    (int (/ (count coll) 2))))

      get-rules (fn [item
                     rules]
                  (filter #(= item (first %)) rules))

      sorter  (fn [x y]
                "The notation X|Y means that if both page number X and
                page number Y are to be produced as part of an update,
                page number X must be printed at some point before page number Y" 
                (let [rule [x y]
                      check (u/in? 
                             (get-rules x rules)
                             rule)]
                  ;;(println x y ":" (get-rules x rules) "->" check)
                  (case check
                    true true
                    nil false)))

      sort-upd  (fn [coll]
                  (sort sorter coll))

      correct-order? (fn [coll rules]
                       (= (sort-upd coll) coll))

      part1 (reduce + (map middle (filter #(correct-order? % rules) upd)))

      part2 (->> upd
                 (filter #(not (correct-order? % rules)))
                 (map sort-upd)
                 (map middle)
                 (reduce +))]

  {:part1 part1 
   :part2 part2})
