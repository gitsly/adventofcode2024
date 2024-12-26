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
                           {(Integer/parseInt before)
                            (Integer/parseInt after)}))

      parse-update (fn [line]
                     (vec (map Integer/parseInt
                               (str/split
                                line #","))))

      rules (map parse-page-order rules)

      upd (map parse-update upd)


      before (fn [curr
                  rules]
               "Get sequence of pages that must be printed before 'curr'"
               (filter #(not (nil? %)) 
                       (map 
                        #(get % curr) rules)))

      heppas (fn [col  ; previously checked
                  rules]
               (let [curr (last col) ; current value to check
                     check (fn[i col]
                             (println col ":" i)
                             (every? #(> % i) col))
                     before (before curr rules)]

                 (every? #(check % (drop-last col)) before)

                 ))

      ]

  ;; note: potential tree structure since multiple
  ;; keys are identical (before) 

  (heppas [74 47] rules) ; true
  (heppas [74 47 61] rules) ; true

  )
