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

      get-rules (fn [item
                     rules]
                  (filter #(= item (first %)) rules))

      after-check (fn [curr after]
                    (let [after-rules (get-rules curr rules)
                          after-nums (set/difference 
                                      (set after)
                                      (set (map second after-rules)))]
                      (empty? after-nums)

                      ))


      before-check (fn [before curr]
                     (let [before-rules (get-rules curr rules)
                           numbers-not-to-be-found-in-before (map second before-rules)]
                       (every? #(not (u/in? before %)) numbers-not-to-be-found-in-before)))

      ]

  ;; 75 is correctly first because there are rules that put each other page after it: 75|47, 75|61, 75|53, and 75|29. 
  (after-check 75 [47,61,53,29]) ;-> true

  ;; 47 is correctly second because 75 must be before it (75|47) and
  ;; every other page must be after it according to 47|61, 47|53, 47|29.
  (after-check 47 [61,53,29])

  ;; The fourth update, 75,97,47,61,53, is not in the correct order:
  ;; it would print 75 before 97, which violates the rule 97|75
  (after-check 75 [97,47,61,53])

  ;; The fifth update, 61,13,29, is also not in the correct order,
  ;; since it breaks the rule 29|13
  (before-check [61 13] 29)

  (before-check [75 47,61,53] 29) ;-> true
  )
