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

      ;; 75,47,61,53,29
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
                    nil false))
                )
      ;; 47 75
      ;; 75 47
      ;; 61 47
      ;; 47 61
      ;; 53 61
      ;; 61 53
      ;; 29 53
      ;; 53 29


      sort-upd  (fn [coll]
                  (sort sorter coll))

      correct-order? (fn [coll rules]
                       (= (sort-upd coll) coll))

      part1 (reduce + (map middle (filter #(correct-order? % rules) upd)))
      ]

  ;;  (sort-upd [75,97,47,61,53])

  part1
  )


;; [75 47 61 53 29]
;;------------------ (Sorted)
;; 47 75 : ([47 53] [47 13] [47 61] [47 29])
;; 75 47 : ([75 29] [75 53] [75 47] [75 61] [75 13])
;; 61 47 : ([61 13] [61 53] [61 29])
;; 47 61 : ([47 53] [47 13] [47 61] [47 29])
;; 53 61 : ([53 29] [53 13])
;; 61 53 : ([61 13] [61 53] [61 29])
;; 29 53 : ([29 13])
;; 53 29 : ([53 29] [53 13])

;; [75,97,47,61,53] becomes 97,75,47,61,53
;;------------------- (Invalid: 75 before 97 violates rule 97|75)
;; 97 75 : ([97 13] [97 61] [97 47] [97 29] [97 53] [97 75])
;; 75 97 : ([75 29] [75 53] [75 47] [75 61] [75 13])
;; 47 97 : ([47 53] [47 13] [47 61] [47 29])
;; 97 47 : ([97 13] [97 61] [97 47] [97 29] [97 53] [97 75])
;; 61 47 : ([61 13] [61 53] [61 29])
;; 47 61 : ([47 53] [47 13] [47 61] [47 29])
;; 53 61 : ([53 29] [53 13])
;; 61 53 : ([61 13] [61 53] [61 29])
