(ns clojure-repl-sufficient-p.wrapper)

(defn constituent [m]
  (assoc m :constituent true))

(defn wrapper [f]
  (fn [input]
    (assoc (f input) :wrapper "pre-iteration")))

(def composite (wrapper constituent))

;; Alternate form with the desired dynamic behavior,
;; at the cost of constructing a new Fn on every call:
;;
;; (defn composite [& args]
;;   (apply (wrapper constituent) args))

(def pre-iteration-result (composite {}))

(defn wrapper [f]
  (fn [input]
    (assoc (f input) :wrapper "post-iteration")))

(def post-iteration-result (composite {}))
