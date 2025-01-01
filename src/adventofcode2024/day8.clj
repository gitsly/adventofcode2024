(ns adventofcode2024.day8
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [zprint.core :as zp]
            [clojure.set :as set]))

(let [
      input "resources/day8/sample" ;(12x12)
      ;; input "resources/day8/input"
      ;;      input "resources/day7/input" ; pt1: 6392012777720
      lines (u/get-lines input)

      parse-line (fn [line y]
                   (for [x (range (count line))]
                     {:char (get line x)
                      :pos [x y]}))
      parse-lines (fn[lines]
                    (for [y (range (count lines))]
                      (parse-line (get lines y) y)))

      test1  (apply str
                    (map :char
                         (first
                          (parse-lines lines))))
      ;;...O.....0...............................p..k.....

      get-char (fn [state xy]
                 (if-let [antenna (first (get (group-by :pos
                                                        (:antennas state))
                                              xy))]
                   (:char antenna)
                   \.)
                 )

      print-state (fn [state]
                    "Visualize in ASCII art"
                    (let [grid-size (count lines)]
                      (for [y (range grid-size)]
                        (println
                         (apply str 
                                (for [x (range grid-size)]
                                  (get-char state [x y])))))))

      antennas (filter #(not (= \. (:char %)))
                       (flatten (parse-lines lines)))
      state {:antennas antennas}
      ]

  (print-state state)

  

  )
