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

(let [input "resources/day6/sample" ; pt1: 5409
      ;;     input "resources/day6/hasobstacle"

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

      turn (fn [dir]
             "Rotate 90 degrees right"
             (let [dir-index (.indexOf dirs dir)
                   next-index (mod
                               (inc dir-index)
                               (count dirs))]
               (get dirs next-index)))

      state {:pos (find-start grid)
             :grid grid
             :dir (first dirs) }

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
                                           ch))))]
                      (for [y (range grid-size)]
                        (println
                         (apply str 
                                (for [x (range grid-size)]
                                  (get-char [x y])))))))

      vector-add (fn [a b]
                   (let [[a1 a2] a
                         [b1 b2] b]
                     [(+ a1 b1) (+ a2 b2)]))

      inside-grid (fn [xy
                       size]
                    (let [[x y] xy]
                      (and (>= x 0)
                           (< x size)
                           (>= y 0)
                           (< y size))))

      has-obstacle (fn [grid dir pos]
                     (let [check-pos (in-grid grid (vector-add pos dir))]
                       (= check-pos obstacle-char)))

      pos-and-dir (fn [state]
                    (let [{dir :dir
                           grid :grid
                           pos :pos} state]
                      (if (has-obstacle grid dir pos)
                        (-> state (update :dir turn))
                        (-> state (update :pos #(vector-add % dir))))))

      inside (fn [state]
               (assoc state :inside
                      (inside-grid (:pos state) (count (:grid state)))))

      update-state (fn [state]
                     (let [{dir :dir
                            grid :grid
                            pos :pos } state]
                       (-> state
                           pos-and-dir
                           inside)))

      ;;      part1 (count (set (map :pos
      ;;                             (take-while #(inside-grid (:pos %) (count grid))
      ;;                                         (iterate update-state state))))) ;; [4 5]
      updated-state (update-state state)
      ]
  
  (doall (print-state
          updated-state))
  ;; part1
  )
