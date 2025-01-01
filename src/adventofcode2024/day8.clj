(ns adventofcode2024.day8
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [zprint.core :as zp]
            [clojure.set :as set]))

(defn )

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

      get-char (fn [state xy]
                 (if-let [antenna (first (get (group-by
                                               :pos
                                               (:antennas state))
                                              xy))]
                   (:char antenna)
                   \.))


      undirectional-pairs (fn [coll ]
                            (filter #(not (nil? %))
                                    (for [i (range (count coll))
                                          j (range (count coll))]
                                      (if (> j i)
                                        [(get coll i) (get coll j)]))))

      print-state (fn [state]
                    "Visualize in ASCII art"
                    (let [grid-size (count lines)]
                      (for [y (range grid-size)]
                        (println
                         (apply str 
                                (for [x (range grid-size)]
                                  (get-char state [x y])))))))

      add-antinode (fn [state xy]
                     (let [[x y] xy
                           size (:size state)
                           antinode {:char \#
                                     :pos xy}]
                       (if (and (>= x 0) (< x size)
                                (>= y 0) (< y size))
                         (update state :antennas
                                 #(conj % antinode))
                         state))
                     )

      antennas (filter #(not (= \. (:char %)))
                       (flatten (parse-lines lines)))

      state {:antennas antennas
             :size (count lines)}

      calculate-antinodes (fn [state
                               a b]
                            "calculate positions and add antinodes for two given antennas"
                            (let [pa (:pos a)
                                  pb (:pos b)
                                  diff (u/vector-sub pa pb)
                                  a1 (u/vector-add pa diff)
                                  a2 (u/vector-add pb (u/vector-mul [-1 -1] diff))]
                              (-> state
                                  (add-antinode a1)
                                  (add-antinode a2))))

      sample-of-0 (take 2 (drop 1 (val (first (group-by :char antennas)))))

      count-unique-antinodes (fn [state]
                               (count
                                (group-by :pos
                                          (filter #(= \# (:char %))
                                                  (:antennas state)))))
      simple (fn [antenna]
               (:pos antenna))


      add-antinodes-for-frequency (fn [state
                                       antennas]
                                    (let [pairs (undirectional-pairs antennas)]
                                      (loop [pairs pairs
                                             state state]
                                        (if (empty? pairs)
                                          state
                                          (recur (rest pairs)
                                                 (let [[a b] (first pairs)]
                                                   (calculate-antinodes state a b)))))
                                      )
                                    )

      test2 (-> state
                (add-antinode  [-5 1])
                (add-antinode  [6 2]))
      ]

  ;;  (count-unique-antinodes
  ;;   (calculate-antinodes state
  ;;                        (first sample-of-0)
  ;;                        (second sample-of-0)))
  (print-state
   (add-antinodes-for-frequency state
                                (get (group-by :char antennas) \0)))


  ;;  (get (group-by :char antennas) \0)
  )
