(ns adventofcode2024.day2
  (:require [clojure.string :as str]
            [adventofcode2024.utils :as u]))

(def input (u/get-lines "resources/day2/sample"))

(let [apa (fn [pred
               col]
            (loop [prev nil
                   col col]
              (println (first col))
              (let [item (first col)
                    eval (or (nil? prev)
                             (pred prev item))]
                (if (not eval)
                  false
                  (recur (first col) (rest col)))))
            )
      ]

  (apa nil? [1 2 3 4])  )






