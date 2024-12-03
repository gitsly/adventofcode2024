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
  (map parse-line lines))

(defn find-first
  [f coll]
  (first (filter f coll)))
