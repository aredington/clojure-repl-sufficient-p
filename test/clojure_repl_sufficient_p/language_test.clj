(ns clojure-repl-sufficient-p.language-test
  (:require [clojure.test :refer :all]))

(deftest redefining-inline
  (testing "An inlined function that gets redefined will have its redefinition used by its clients."
    (require 'clojure-repl-sufficient-p.inlining)
    (is (= "baz-bar-foo-zot" @(resolve 'clojure-repl-sufficient-p.inlining/pre-iteration-result)))
    (is (= "baz-bar-foo-zott" @(resolve 'clojure-repl-sufficient-p.inlining/post-iteration-result)))))

(deftest redefining-defrecord
  (testing "Redefining a defrecord with a protocol preserves that record's ability to participate in the protocol."
    (require 'clojure-repl-sufficient-p.defprotocol)
    (is (= {:arms 8 :hearts 3 :eyes 2 :beaks 1 :color :red} (into {} @(resolve 'clojure-repl-sufficient-p.defprotocol/pre-iteration-result))))
    (is (= {:arms 8 :hearts 3 :eyes 2 :beaks 1 :color :green :suckers 2240} (into {} @(resolve 'clojure-repl-sufficient-p.defprotocol/post-iteration-result))))))

(deftest redefining-protocol
  (testing "Redefining a protocol preserves the membership of types to that protocol."
    (require 'clojure-repl-sufficient-p.defprotocol2)
    (is (= {:arms 8 :hearts 3 :eyes 2 :beaks 1 :color :red} (into {} @(resolve 'clojure-repl-sufficient-p.defprotocol2/pre-iteration-result))))
    (is (= {:arms 8 :hearts 3 :eyes 2 :beaks 1 :color :green} (into {} @(resolve 'clojure-repl-sufficient-p.defprotocol2/post-iteration-result))))))

(deftest redefining-multimethod-dispatch-fn
  (testing "Redefining a multimethod function's dispatch function should have use the new dispatch function."
    (require 'clojure-repl-sufficient-p.defmulti)
    (is (= {:animal :octopus :color :red} (into {} @(resolve 'clojure-repl-sufficient-p.defmulti/pre-iteration-result))))
    (is (= {:plant :hydrangea :color :pink} (into {} @(resolve 'clojure-repl-sufficient-p.defmulti/post-iteration-result))))))
