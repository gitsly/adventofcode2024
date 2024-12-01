(ns adventofcode2024.utils
  (:require [clojure.string :as str]))

(defn get-lines [file-path]
  (str/split-lines (slurp file-path)))

(defn find-first
  [f coll]
  (first (filter f coll)))
