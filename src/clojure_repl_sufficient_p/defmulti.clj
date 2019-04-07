(ns clojure-repl-sufficient-p.defmulti)

(defmulti color-change (fn [x & _] (:animal x)))

(defmethod color-change :octopus
  [octopus color]
  (assoc octopus :color color))

(defmethod color-change nil
  [_ _]
  :default-implementation)

(def pre-iteration-result (color-change {:animal :octopus :color :white} :red))

(defmulti color-change (fn [x & _] (:or (:animal x)
                                        (:plant  x))))

(defmethod color-change :hydrangea
  [hydrangea color]
  (assoc hydrangea :color color))

(def post-iteration-result (color-change {:plant :hydrangea :color :blue} :pink))



 
