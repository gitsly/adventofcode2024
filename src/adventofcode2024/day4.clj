(ns adventofcode2024.day4
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]))


;; 10 x 10
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

;; 140 x 140
(def input (u/get-lines "resources/day4/input"))

(let [word "XMAS"

      dirs [[ 1  0]
            [-1  0]
            [ 0  1]
            [ 0 -1]
            [ 1  1]
            [-1 -1]]

      find-word (fn [col word]
                  "Returns sequences of found word"
                  (loop [col col
                         matched-letters []
                         matched-words []]
                    (if (empty? col)
                      matched-words; iteration complete
                      (let [letter (first col)
                            nextmatch (first (drop (count matched-letters) word))

                            restart (= letter (first word))

                            letter-match (= nextmatch letter)
                            ]

                        (comment
                          (println
                           {:letter letter
                            :nextmatch nextmatch
                            :restart restart 
                            :matched-letters matched-letters
                            :letter-match letter-match }))

                        (recur
                         ;; remaining of collection
                         (rest col)
                         ;; Matched letters
                         (if  letter-match
                           (conj matched-letters letter)
                           (if restart
                             [ letter]
                             []))
                         ;; Matched words
                         (if (= (conj matched-letters letter)
                                (seq word))
                           (conj matched-words word)
                           matched-words)

                         )))))


      get-letter (fn [input x y]
                   "input is two dimensional array of letters"
                   (let [width (count (first input))
                         height (count input)]
                     (nth (nth input y) x)))


      ]
  ;; (find-word "MMMSXXMASM" word)
  ;; (find-word "XMASAXMAS" word)
  ;; (find-word "MMMSXXMASM" word)
  ;; (find-word "XXMAS"  word)

  (get-letter input 4 2)

  

  )

(drop 3 [1 2 3 4 5])
