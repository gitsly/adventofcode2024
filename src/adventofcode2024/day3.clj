(ns adventofcode2024.day3
  (:require [clojure.string :as str]
            [clojure.spec.alpha :as s]
            [adventofcode2024.utils :as u]))

(def input-pt1 "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
(def input-pt2 "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

;; 161 (2*4 + 5*5 + 11*8 + 8*5).
(def input (slurp "resources/day3/input")) ; Pt1 -> 184576302 ,  Pt2; -> 118173507

(let [ops (re-seq #"mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)" input) ;; sec of [a b c] will be produced

      start-state {:enabled true
                   :op-count 0
                   :multiplications []}

      mul-fn (fn[state param]
               (let [enabled (:enabled state)]
                 (if enabled
                   (-> state
                       (update :multiplications #(conj % param)))
                   state))) 

      do-fn (fn[state param]
              (-> state
                  (assoc :enabled true))) 

      dont-fn (fn[state param]
                (-> state
                    (assoc :enabled false))) 

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
                 "Executes the op on the current state"
                 (let [op-fn (:op op)
                       param (:param op)]
                   ;;                   (println (:name op) ":" state)
                   (-> state
                       (update-in [:op-count] inc)
                       (op-fn param)))) 

      eval-state (fn[state ops]
                   (loop [state state
                          ops ops]
                     (if (empty? ops)
                       state
                       (recur (traverse state (first ops))
                              (rest ops)))))

      ]
  (->> (eval-state start-state parsed-ops)
       (:multiplications)
       (map #(reduce * %))
       (reduce +))
  )
