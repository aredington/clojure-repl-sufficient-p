(ns clojure-repl-sufficient-p.defmulti)

(defmulti color-change (fn [x & _] (:animal x)))

(defmethod color-change :octopus
  [octopus color]
  (assoc octopus :color color))

(def pre-iteration-result (color-change {:animal :octopus :color :white} :red))

(defmulti color-change (fn [x & _] (:or (:animal x)
                                        (:plant  x))))

(try
  (def post-iteration-result (color-change {:plant :hydrangea :color :blue} :pink))
  (catch Exception e
    (def post-iteration-result {:result :compilation-failure
                                :exception e})))



 
