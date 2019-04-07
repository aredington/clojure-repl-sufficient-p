(ns clojure-repl-sufficient-p.defmulti)

(defmulti color-change (fn [x & _] (:animal x)))

(defmethod color-change :octopus
  [octopus color]
  (assoc octopus :color color))

(defmethod color-change nil
  [_ _]
  :default-implementation)

(def pre-iteration-result-dispatch-fn (color-change {:animal :octopus :color :white} :red))

(defmulti color-change (fn [x & _] (:or (:animal x)
                                        (:plant  x))))

(defmethod color-change :hydrangea
  [hydrangea color]
  (assoc hydrangea :color color))

(def post-iteration-result-dispatch-fn (color-change {:plant :hydrangea :color :blue} :pink))

(defmulti sound (fn [animal] animal) :default :default)

(defmethod sound :dog
  [dog]
  "Woof")

(defmethod sound :cat
  [cat]
  "Meow")

(defmethod sound :echidna
  [echidna]
  ;; https://www.youtube.com/watch?v=gAJJeHdspwc
  "Pfsssssst!")

(defmethod sound :default
  [animal]
  "I don't know what sound that makes!")

(def pre-iteration-result-default (map sound [:dog :cat :echidna :kangaroo]))

(defmulti sound (fn [animal] animal) :default ::default-animal)

(defmethod sound ::default-animal
  [animal]
  (str "I don't know what sound " animal " makes."))

(def post-iteration-result-default (map sound [:dog :cat :echidna :kangaroo]))
