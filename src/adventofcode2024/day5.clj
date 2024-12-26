(ns adventofcode2024.day5
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [clojure.set :as set]))

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


      before (fn [curr
                  rules]
               "Get sequence of pages that must be printed before 'curr'"
               (filter #(not (nil? %)) 
                       (map 
                        #(get % curr) rules)))
      
      check-after (fn [curr
                       rules]
                    (let [before (before curr rules)]
                      (every? #(< % curr) before)))

      sample (first upd)

      get-after-rules (fn [item
                           rules]
                        (filter #(= item (first %)) rules))

      into-hash-map     (fn [rules]
                          (map #(hash-map
                                 (first %) (second %)) rules))
      into-hash-map-rev (fn [rules]
                          (map #(hash-map
                                 (second %) (first %)) rules))

      ]

  ;; note: potential tree structure since multiple
  ;; keys are identical (before) 

  ;; 75 is correctly first because there are rules that put each other page after it: 75|47, 75|61, 75|53, and 75|29. 
  (let [curr 75
        before []
        after [47,61,53,29]

        after-rules (get-after-rules curr rules)

        after-nums (set/intersection 
                    (set after)
                    (set (map second after-rules)))

        after-check (every? #(< % curr) after-nums)
        ]

    after-check

    ))
