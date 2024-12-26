(ns adventofcode2024.day5
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]))

(let [lines (u/get-lines "resources/day5/sample")
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
                               (str/split
                                line #","))))

      rules (map parse-page-order rules)

      upd (map parse-update upd)

      check-update (fn [upd rules]
                     nil)

      ]

  [rules
   upd]
  )
