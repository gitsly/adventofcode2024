(ns adventofcode2024.day6
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [clojure.set :as set]))

(def start-char \^)
(def obstacle-char \#)

(let [
      input "resources/day6/sample"
      input "resources/day6/hasobstacle"

      grid (u/get-lines input)

      in-grid (fn [grid
                   xy]
                (let [[x y] xy]
                  (get
                   (get grid y) x)
                  ))

      ;; constants, like everything else... but...

      find-start (fn [grid]
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

      turn (fn [dirs
                dir]
             "Rotate 90 degrees right"
             (let [dir-index (.indexOf dirs dir)
                   next-index (mod
                               (inc dir-index)
                               (count dirs))]
               (get dirs next-index)))

      state {:pos (find-start grid)
             :grid grid
             :dir (first dirs) }


      test-dir-fn (map dir-to-char (take 12 (iterate #(turn dirs %)
                                                     [0 -1]))) ; ^>v<^>v<^>v<
      print-state (fn [state]
                    "Visualize grid in ascii art"
                    (let [{dir :dir
                           grid :grid
                           pos :pos} state
                          grid-size (count grid)
                          get-char (fn [xy]
                                     (let [ch (in-grid grid xy)]
                                       (if (= pos xy)
                                         (get  dir-to-char dir)
                                         (if (= ch start-char)
                                           \.
                                           ch)))
                                     )
                          ]
                      (for [y (range grid-size)]
                        (println
                         (apply str 
                                (for [x (range grid-size)]
                                  (get-char [x y])))))))

      vector-add (fn [a b]
                   (let [[a1 a2] a
                         [b1 b2] b]
                     [(+ a1 b1)
                      (+ a2 b2)]))

      has-obstacle (fn [state]
                     (let [{dir :dir
                            grid :grid
                            pos :pos} state
                           check-pos (in-grid grid (vector-add pos dir))]
                       (= check-pos obstacle-char)))

      do-move (fn [state]
                (let [{dir :dir
                       grid :grid
                       pos :pos} state]
                  ))

      ]

  (doall
   (print-state state))

  (has-obstacle state)
  )
