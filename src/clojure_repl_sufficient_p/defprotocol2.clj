(ns clojure-repl-sufficient-p.defprotocol2)

(defprotocol Chromatophoric
  (color-change [chromatophoric color] "Alters `chromatophoric` to be `color`"))

(defrecord Octopus [arms hearts eyes beaks color]
  Chromatophoric
  (color-change [octopus color]
    (assoc octopus :color color)))

(def pre-iteration-result (color-change (Octopus. 8 3 2 1 :white) :red))

(defprotocol Chromatophoric
  (color-change [chromatophoric color] "Alters `chromatophoric` to be `color`"))

(try
  (def post-iteration-result (color-change (Octopus. 8 3 2 1 :white) :green))
  (catch Exception e
    (def post-iteration-result {:result :compilation-failure
                                :exception e})))
