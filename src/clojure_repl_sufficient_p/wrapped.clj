(ns clojure-repl-sufficient-p.wrapped)

(defn constituent [m]
  (assoc m :constituent "pre-iteration"))

(defn wrapper [f]
  (fn [input]
    (assoc (f input) :wrapper true)))

(def composite (wrapper constituent))

;; Alternate form with the desired dynamic behavior,
;; at the cost of an extra Var lookup:
;;
;; (def composite (wrapper #'constituent))

(def pre-iteration-result (composite {}))

(defn constituent [m]
  (assoc m :constituent "post-iteration"))

(def post-iteration-result (composite {}))
