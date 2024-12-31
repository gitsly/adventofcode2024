(ns adventofcode2024.day6
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [clojure.set :as set]))

(let [input "resources/day6/sample"
      grid (u/get-lines input)

      in-grid (fn [grid
                   xy]
                (let [[x y] xy]
                  (get
                   (get grid y) x)
                  ))

      start-char \^

      find-start (fn [grid start-char]
                   "Returns [x,y] vector where ^ is located"
                   (let [
                         start-line (first (filter #(not (nil? (str/index-of % start-char))) grid))
                         x (str/index-of start-line start-char)
                         y (.indexOf grid start-line)]
                     [x y]))

      dirs [[0 -1]
            [1  0]
            [0  1]
            [-1 0]]

      dir-to-char {[0 -1] \^
                   [1  0] \>
                   [0  1] \v
                   [-1 0] \< }


      state {:pos (find-start grid start-char)
             :grid grid
             :dir (first dirs) }

      turn (fn [dirs
                dir]
             "rotate 90 degrees right"
             (let [dir-index (.indexOf dirs dir)
                   next-index (mod
                               (inc dir-index)
                               (count dirs))]
               (get dirs next-index)))

      do-move (fn [state]
                (let [{dir :dir} state]
                  ))

      

      test-dir-fn (map dir-to-char (take 12 (iterate #(turn dirs %)
                                                     [0 -1]))) ; ^>v<^>v<^>v<
      print-state (fn [state start-char]
                    "Visualize grid in ascii art"
                    (let [{dir :dir
                           grid :grid
                           pos :pos} state
                          grid-size (count grid)

                          get-char (fn [xy]
                                     (let [ch (in-grid grid xy)]
                                       ch)
                                     )
                          ]
                      (for [y (range grid-size)]
                        (println
                         (apply str 
                                (for [x (range grid-size)]
                                  (get-char [x y])))))))

      ]

  (print-state state start-char)

  )
