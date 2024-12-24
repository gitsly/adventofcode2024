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
                            letter-match (= nextmatch letter)]
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
                           matched-words))))))

      get-letter (fn [input xy]
                   "input is two dimensional array of letters, returns nil if outside bounds"
                   (let [[x y] xy
                         width (count (first input))
                         height (count input)
                         ]
                     (if (and (>= x 0) (< x width)
                              (>= y 0) (< y height))
                       (nth (nth input y) x))))

      letter-seq (fn [input start dir]
                   "returns a lazy sequence of letters from start in direction"
                   (loop [p start
                          letters []]
                     (let [letter (get-letter input p)]
                       (if (nil? letter)
                         letters

                         (recur
                          ;; calculate next p 
                          (let [[dx dy] dir
                                [px py] p]
                            [(+ px dx) (+ py dy)])
                          ;; letters
                          (conj letters letter))))))

      cols (apply
            concat
            (let [size (count input)
                  sizedec (dec size)]
              [(for [y (range size)] (letter-seq input [0 y] [1 0])) ; -->
               (for [x (range size)] (letter-seq input [x 0] [0 1])) ; V
               (for [x (range size)] (letter-seq input [x 0] [1 1])) ; \ upper right
               (for [y (range 1 size)] (letter-seq input [0 y] [1 1])) ; \ lower right
               (for [y (range size)] (letter-seq input [0 y] [1 -1])) ; / upper 
               (for [x (range 1 size)] (letter-seq input [x sizedec] [1 -1])) ; / lower
               ]))

      cols (concat
            cols 
            (map reverse cols))

      ;; 2633
      part1 (reduce + (map count
                           (map #(find-word % word) 
                                cols)))

      box (fn [input xy]
            (let [[x y] xy]
              (for [y (range y (+ y 3))
                    x (range x (+ x 3))]
                (get-letter input [x y]  )
                ))) 

      ]

  (box input [0 0])
  

  )

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
