(ns adventofcode2024.day4
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]))


(def input ["MMMSXXMASM"
            "MSAMXMSMSA"
            "AMXSXMAAMM"
            "MSAMASMSMX"
            "XMASAMXAMM"
            "XXAMMXXAMA"
            "SMSMSASXSS"
            "SAXAMASAAA"
            "MAMMMXMMMM"
            "MXMXAXMASX"]) ; pt1 -> 18


(def input (slurp "resources/day4/input"))

(let [word "XMAS"

      find-word (fn [col word]
                  (loop [col col
                         curword word
                         matches []]
                    (if (empty? col)
                      matches ; iteration complete
                      (let [letter (first col)
                            check  (= (first curword) letter)
                            matches  (if check
                                       (conj matches letter)
                                       matches)
                            curword (if check
                                      (rest curword)
                                      curword)
                            ]

                        (print letter)

                        (recur
                         (rest col)
                         curword
                         matches
                         )))))
      ]

  ;; "MMMSXXMASM"
  (find-word (first input) word)
  )

