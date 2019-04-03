(ns clojure-repl-sufficient-p.inlining)

(defn zottify
  "Appends '-zot' to the str representation of `v`. Inlines."
  {:inline (fn [v] `(.concat (.toString ~v) "-zot"))
   :inline-arities #{1}}
  [v]
  (str v "-zot"))

(defn zottify-client
  "Uses zottify to create a string prepended by 'baz-bar-'."
  [v]
  (str "baz-bar-" (zottify v)))

(def pre-iteration-result (zottify-client "foo"))

(defn zottify
  "Redefs zottify to change the semantic."
  {:inline (fn [v] `(.concat (.toString ~v) "-zott"))
   :inline-arities #{1}}
  [v]
  (str v "-zott"))

(def post-iteration-result (zottify-client "foo"))
