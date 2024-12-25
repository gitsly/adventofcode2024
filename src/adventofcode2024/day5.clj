(ns adventofcode2024.day5
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]))


(let [lines (u/get-lines "resources/day5/sample")
      input (let [coll lines
                  pred (fn [item] (not (empty? item)))]
              [(take-while pred coll) (drop 1 (drop-while pred coll))])] ; vector of ['page ordering rules', 'page numbers of each update']


  )
