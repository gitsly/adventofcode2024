(ns adventofcode2024.utils
  (:require [clojure.string :as str]))

(defn get-lines [file-path]
  (str/split-lines (slurp file-path)))

(defn parse-integer-line
  "parses a line of integer numbers"
  [line]
  (->> line
       (re-seq #"(\d+)\s*")
       (map last)
       (map Integer/parseInt)))

(defn parse-integer-lines [lines]
  (map parse-integer-line lines))

(defn find-first
  [f coll]
  (first (filter f coll)))

(defn in? 
  "true if coll contains elm"
  [coll element]  
  (some #(= element %) coll))

(defn drop-nth [n coll]
  (concat 
   (take n coll)
   (drop (inc n) coll)))
