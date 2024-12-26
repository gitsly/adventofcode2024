(ns adventofcode2024.day5
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [clojure.set :as set]))

(let [input "resources/day5/input" ; pt1 = 4996
      input "resources/day5/sample" ; pt1 = 143

      ;; PART 2 (sort the incorrect ones)
      ;; 75,97,47,61,53 becomes 97,75,47,61,53.
      ;; 61,13,29 becomes 61,29,13.
      ;; 97,13,75,29,47 becomes 97,75,47,29,13.

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
               (get coll
                    (int (/ (count coll) 2))))

      sample (first upd)

      get-rules (fn [item
                     rules]
                  (filter #(= item (first %)) rules))

      sorter  (fn [a b]
                ;; (> a b)
                (println a b)
                false)

      sort-upd  (fn [coll]
                  (sort sorter coll))

      correct-order? (fn [coll rules]
                       (= (sort-upd coll) coll))

      ;;      part1 (reduce + (map middle (filter #(correct-order? % rules) upd)))
      ]
  (sort-upd sample) ;; [75 47 61 53 29]

  )
