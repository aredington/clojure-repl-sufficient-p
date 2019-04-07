(ns clojure-repl-sufficient-p.language-test
  (:require [clojure.test :refer :all]))

(deftest redefining-inline
  (testing "An inlined function that gets redefined will have its redefinition used by its clients."
    (require 'clojure-repl-sufficient-p.inlining)
    (is (= "baz-bar-foo-zot" @(resolve 'clojure-repl-sufficient-p.inlining/pre-iteration-result)))
    (is (= "baz-bar-foo-zott" @(resolve 'clojure-repl-sufficient-p.inlining/post-iteration-result)))))

(deftest rirt
  (testing "Defining a protocol, then implementing the protocol with a record, then redefining the record without the implementation should get rid of the protocol implementation."
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->r (resolve 'clojure-repl-sufficient-p.defprotocol/->RecordRIRt)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/rirt)]
      (is (thrown? IllegalArgumentException
                   (m (->r)))))))

(deftest tirt
  (testing "Defining a protocol, then implementing the protocol with a type, then redefining the type without the implementation should get rid of the protocol implementation."
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->t (resolve 'clojure-repl-sufficient-p.defprotocol/->TypeTIRt)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/tirt)]
      (is (thrown? AbstractMethodError
                   (m (->t)))))))

(deftest rprt
  (testing "Defining a protocol, then a record, then extending the protocol with the record, then redefining the record without the implementation should still implement the protocol"
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->r (resolve 'clojure-repl-sufficient-p.defprotocol/->RecordRPRt)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/rprt)]
      (is (= :before (m (->r)))))))

(deftest tprt
  (testing "Defining a protocol, then a type, then extending the protocol with the type, then redefining the type without the implementation should still implement the protocol"
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->r (resolve 'clojure-repl-sufficient-p.defprotocol/->TypeTPRt)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/tprt)]
      (is (= :before (m (->r)))))))

(deftest rtrt
  (testing "Defining a protocol, then defining a record, then extending the record to the protocol, then redefining the record without the implementation should still implement the protocol"
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->r (resolve 'clojure-repl-sufficient-p.defprotocol/->RecordRTRt)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/rtrt)]
      (is (= :before (m (->r)))))))

(deftest ttrt
  (testing "Defining a protocol, then defining a type, then extending the type to the protocol, then redefining the type without the implementation should still implement the protocol"
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->r (resolve 'clojure-repl-sufficient-p.defprotocol/->TypeTTRt)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/ttrt)]
      (is (= :before (m (->r)))))))



(deftest rirp
  (testing "Defining a protocol, then implementing the protocol with a record, then redefining the protocol should keep the implementation."
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->r (resolve 'clojure-repl-sufficient-p.defprotocol/->RecordRIRp)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/rirp)]
      (is (= :before (m (->r)))))))

(deftest tirp
  (testing "Defining a protocol, then implementing the protocol with a type, then redefining the protocol should keep the implementation."
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->t (resolve 'clojure-repl-sufficient-p.defprotocol/->TypeTIRp)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/tirp)]
      (is (= :before (m (->t)))))))

(deftest rprp
  (testing "Defining a protocol, then a record, then extending the protocol with the record, then redefining the record without the implementation should still implement the protocol."
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->r (resolve 'clojure-repl-sufficient-p.defprotocol/->RecordRPRp)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/rprp)]
      (is (= :before (m (->r)))))))

(deftest tprp
  (testing "Defining a protocol, then a type, then extending the protocol with the type, then redefining the type without the implementation should still implement the protocol."
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->r (resolve 'clojure-repl-sufficient-p.defprotocol/->TypeTPRp)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/tprp)]
      (is (= :before (m (->r)))))))

(deftest rtrp
  (testing "Defining a protocol, then defining a record, then extending the record to the protocol, then redefining the record without the implementation should still implement the protocol"
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->r (resolve 'clojure-repl-sufficient-p.defprotocol/->RecordRTRp)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/rtrp)]
      (is (= :before (m (->r)))))))

(deftest ttrp
  (testing "Defining a protocol, then defining a type, then extending the type to the protocol, then redefining the type without the implementation should still implement the protocol"
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [->r (resolve 'clojure-repl-sufficient-p.defprotocol/->TypeTTRp)
          m (resolve 'clojure-repl-sufficient-p.defprotocol/ttrp)]
      (is (= :before (m (->r)))))))



(deftest strp
  (testing "Defining a protocol, then extending String to that protocol, then redefining the protocol should still implement the protocol."
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [m (resolve 'clojure-repl-sufficient-p.defprotocol/strp)]
      (is (= :before (m ""))))))

(deftest sprp
  (testing "Defining a protocol, then extending the protocol to String, then redefining the protocol should still implement the protocol."
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [m (resolve 'clojure-repl-sufficient-p.defprotocol/sprp)]
      (is (= :before (m ""))))))


(deftest redefining-multimethod-dispatch-fn
  (testing "Redefining a multimethod function's dispatch function should have use the new dispatch function."
    ;; cleanly tear down the namespace so it is reloadable ;)
    (remove-ns 'clojure-repl-sufficient-p.defmulti)
    (require 'clojure-repl-sufficient-p.defmulti :reload)
    (is (= {:animal :octopus  :color :red}  @(resolve 'clojure-repl-sufficient-p.defmulti/pre-iteration-result)))
    (is (= {:plant :hydrangea :color :pink} @(resolve 'clojure-repl-sufficient-p.defmulti/post-iteration-result)))
    ;; clean up after ourselves
    (remove-ns 'clojure-repl-sufficient-p.defmulti)))
