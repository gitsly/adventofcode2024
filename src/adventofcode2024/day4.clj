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
                  "Returns sequences of found word"
                  (loop [col col
                         curword word
                         matched-words []]
                    (if (empty? col)
                      matched-words; iteration complete
                      (let [letter (first col)
                            letter-match (= (first curword) letter)
                            complete-word (and (= (count curword) 1)
                                               letter-match)]
                        (println (apply str curword))
                        (recur
                         ;; remaining of collection
                         (rest col)
                         ;; Current remaining word to match
                         (if (and letter-match
                                  (not complete-word))
                           (rest curword) word)
                         ;; Matched words
                         (if complete-word
                           (conj matched-words word)
                           matched-words))))))

      dirs [[ 1 0]
            [-1 0]
            [0 1]
            [0 -1]
            [1 1]
            [-1 -1]]

      ]

  ;; "MMMSXXMASM"
  ;;"XMASAXMAS" 
  (find-word "" word)
  )


