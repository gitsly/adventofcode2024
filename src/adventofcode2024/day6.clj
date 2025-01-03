(ns adventofcode2024.day6
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]
            [clojure.set :as set]))

(def start-char \^)
(def obstacle-char \#)
(def dirs [[0 -1]
           [1  0]
           [0  1]
           [-1 0]])

(def dir-to-char {[0 -1] \^
                  [1  0] \>
                  [0  1] \v
                  [-1 0] \< })

(let [input "resources/day6/input" ; pt1: 5409, pt2: 2022 (terribly slow though, no optimizations done, took about a day to compute)
      input "resources/day6/sample" ; pt1: 41
      ;;input "resources/day6/printingpress" ; 18 unique steps
      ;;     input "resources/day6/hasobstacle"

      grid (u/get-lines input)

      in-grid (fn [grid
                   xy]
                "Gets the character from grid at position xy"
                (let [[x y] xy]
                  (get
                   (get grid y) x)))

      

      find-start (fn [grid]
                   "Returns [x,y] vector where ^ is located"
                   (let [
                         start-line (first (filter #(not (nil? (str/index-of % start-char))) grid))
                         x (str/index-of start-line start-char)
                         y (.indexOf grid start-line)]
                     [x y]))

      turn (fn [dir]
             "Rotate 90 degrees right"
             (let [dir-index (.indexOf dirs dir)
                   next-index (mod
                               (inc dir-index)
                               (count dirs))]
               (get dirs next-index)))

      start-pos (find-start grid)

      state (let [start-dir (first dirs)]
              {:pos start-pos
               :grid grid
               :dir start-dir
               :visited [{:pos start-pos}]})

      print-state (fn [state]
                    "Visualize grid in ASCII art"
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
                                           ch))))]
                      (for [y (range grid-size)]
                        (println
                         (apply str 
                                (for [x (range grid-size)]
                                  (get-char [x y])))))))


      inside-grid (fn [xy
                       size]
                    (let [[x y] xy]
                      (and (>= x 0) (< x size)
                           (>= y 0) (< y size))))

      has-obstacle (fn [grid dir pos]
                     (let [check-pos (in-grid grid (u/vector-add pos dir))]
                       (= check-pos obstacle-char)))

      move (fn [state]
             (let [{dir :dir
                    grid :grid
                    pos :pos} state
                   next-pos (vector-add pos dir)]
               (-> state
                   (update :visited #(conj % {:pos next-pos
                                              :dir dir }))
                   (assoc :pos next-pos))))

      turn-or-move (fn [state]
                     (let [{dir :dir
                            grid :grid
                            pos :pos} state]
                       (if (has-obstacle grid dir pos)
                         (-> state (update :dir turn))
                         (-> state move))))

      inside (fn [state]
               (assoc state :inside
                      (inside-grid (:pos state) (count (:grid state)))))

      infinite (fn [state]
                 (let [{visited :visited} state]
                   (-> state
                       (assoc :infinite (not
                                         (= (count visited)
                                            (count (set visited))))))))

      update-state (fn [state]
                     (let [{dir :dir
                            grid :grid
                            pos :pos } state]
                       (-> state
                           turn-or-move
                           infinite
                           inside)))

      is-not-done (fn [state]
                    (and 
                     (:inside state (count grid))
                     (not (:infinite state))))

      part1 (count (set (map :pos
                             (:visited
                              (last
                               (take-while is-not-done
                                           (iterate update-state state)))))))

      sample-state (last (take 14 (iterate update-state state)))

      add-obstacle (fn [grid
                        xy]
                     "Adds an obstacle at position (x, y) to the grid"
                     (vec (let [[x y] xy
                                replace-at (fn [s idx replacement]
                                             (str (subs s 0 idx) replacement (subs s (inc idx))))
                                row (nth grid y)]
                            (concat 
                             (conj (vec (take y grid))
                                   (replace-at row x obstacle-char))
                             (drop (inc y) grid)))))

      check-infinite (fn [state]
                       (:infinite 
                        (update-state 
                         (last 
                          (take-while is-not-done
                                      (iterate update-state state))))))

      ;; except starting position
      all-permutations (for [y (range (count grid))
                             x (range (count grid))]
                         (if (= [x y] start-pos)
                           state
                           (update state :grid #(add-obstacle % [x y]))))

      row6-of-all-permutations (map #(nth % 6)
                                    (map :grid
                                         all-permutations))

      part2 (count (filter true? (pmap check-infinite all-permutations))) ; This one is very slow! (with real input)
      ]

  {:part1 part1
   :part2 part2
   :size (count grid) ; 10 in sample, 130 in real input => 16900
   })

