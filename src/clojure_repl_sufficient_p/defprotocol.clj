(ns clojure-repl-sufficient-p.defprotocol)

(defrecord Octopus [arms hearts eyes beaks color])

(defprotocol Chromatophoric
  (color-change [chromatophoric color] "Alters `chromatophoric` to be `color`"))

(extend-protocol Chromatophoric
  Octopus
  (color-change [octopus color]
    (assoc octopus :color color)))

(def pre-iteration-result (color-change (Octopus. 8 3 2 1 :white) :red))

(defrecord Octopus [arms hearts eyes beaks color suckers])

(try
  (def post-iteration-result (color-change (Octopus. 8 3 2 1 :white 2240) :green))
  (catch Exception e
    (def post-iteration-result {:result :compilation-failure
                                :exception e})))
