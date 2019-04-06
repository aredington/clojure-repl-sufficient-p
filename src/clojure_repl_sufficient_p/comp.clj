(ns clojure-repl-sufficient-p.comp)

(defn constituent-a [m]
  (assoc m :a "pre-iteration"))

(defn constituent-b [m]
  (assoc m :b "pre-iteration"))

(def composite (comp constituent-a constituent-b))

;; Alternate form with the desired dynamic behavior,
;; at the cost of extra Var lookups:
;;
;; (def composite (comp #'constituent-a #'constituent-b))

(def pre-iteration-result (composite {}))

(defn constituent-a [m]
  (assoc m :a "post-iteration"))

(defn constituent-b [m]
  (assoc m :b "post-iteration"))

(def post-iteration-result (composite {}))
