(ns adventofcode2024.day3
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]))

(def input-pt1 "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

(def input-pt2 "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

;; 161 (2*4 + 5*5 + 11*8 + 8*5).
(def input (slurp "resources/day3/input")) ; Pt1 -> 184576302 
(def input input-pt2) ; Pt1 -> 184576302 


(let [keywords ["mul"
                "do()"
                "don't()"]

      ops (re-seq #"mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)" input) ;; sec of [a b c] will be produced

      mul-fn (fn[state op]
               state) 

      do-fn (fn[state op]
              state) 

      dont-fn (fn[state op]
                state) 

      parse-mul (fn[mul] 
                  (let [[_ & factors] mul
                        factors (map Integer/parseInt factors)]
                    {:op mul-fn
                     :name (str (first factors) "*" (second factors))
                     :param factors}))

      parse-op (fn[input]
                 (if (str/starts-with? (first input) "mul")
                   (parse-mul input)
                   (condp = (first input)
                     "do()" {:op do-fn :name "do"}
                     "don't()" {:op dont-fn :name "dont" })))

      parsed-ops (map parse-op ops)

      traverse (fn [state op]
                 (let [start-state { :enabled true }
                       op-fn (:op op)
                       param (:param op)]
                   (if (not (map? state))
                     start-state
                     (op-fn state param)))) 
      ]

  (println (map :name parsed-ops))
  (reduce traverse parsed-ops))
