(ns clojure-repl-sufficient-p.language-test
  (:require [clojure.test :refer :all]))

;; Inline Functions

(deftest redefining-inline
  (testing "An inlined function that gets redefined will have its redefinition used by its clients."
    (remove-ns 'clojure-repl-sufficient-p.inlining)
    (require 'clojure-repl-sufficient-p.inlining)
    (is (= "baz-bar-foo-zot" @(resolve 'clojure-repl-sufficient-p.inlining/pre-iteration-result)))
    (is (= "baz-bar-foo-zott" @(resolve 'clojure-repl-sufficient-p.inlining/post-iteration-result)))
    (remove-ns 'clojure-repl-sufficient-p.inlining)))

;; Protocol / Record / Type / extend-protocol

;; These cover the gamut of Clojure type and protocol definition, and
;; the consequences of redefinition.  Reasoning about this it's
;; important to consider the "things" to be preserved or destroyed,
;; and cause for doing so. The "things" to be preserved are the
;; intersections between Types and Protocols. For this discussion
;; let's call these (big I) Implementations. The causes for doing so are what
;; expressions evaluated by Clojure cause semantic evaluations to
;; become or terminate their useful function, for this discussion
;; we'll just call them (big E) Expressions.

;; The expectation broadly consistent here is:
;;
;; - An Expression which is not the provenance of an Implementation
;;   should not invalidate that Implementation when
;;   redefined. (e.g. the 'rirp' test in this NS, which creates the
;;   Implementation in a defrecord Expression, and the Implementation is
;;   invalidated when the protocol is redefined)
;;
;; - An Expression which is the provenance of an Implementation should
;;   destroy an Implementation if it is redefined without including
;;   the Implementation.
;;
;; - extend-protocol for a given [Protocol, <Type or Record>] tuple
;;   should be its own source of provenance, and redefining either the
;;   subject protocol or subject record should not destroy the
;;   Implementation, while re-evaluating any `extend-protocol`
;;   Expression with the tuple should destroy Implementations.
;;
;; Providing these guarantees would not be free, so I think it's
;; entirely fair to say that these expected behaviors ONLY can and
;; will be satisfied when one of either the Protocol or the
;; Type/Record defined with ^:redef or ^:dynamic (or similar,
;; orthogonal) metadata. A requirement that BOTH possess it would be
;; far less desirable as it prevents achieving iterative development
;; on protocols / types that the developer has required through
;; dependencies. We will happily redefine these tests to reflect the
;; above with input from the Clojure team.

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
  (testing "Defining a protocol, then extending String to that protocol, then redefining the protocol, should leave String still implementing the protocol."
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [m (resolve 'clojure-repl-sufficient-p.defprotocol/strp)]
      (is (= :before (m ""))))))

(deftest sprp
  (testing "Defining a protocol, then extending the protocol to String, then redefining the protocol, should leave String still implementing the protocol."
    (remove-ns 'clojure-repl-sufficient-p.defprotocol)
    (require 'clojure-repl-sufficient-p.defprotocol :reload)
    (let [m (resolve 'clojure-repl-sufficient-p.defprotocol/sprp)]
      (is (= :before (m ""))))))

;; Multimethods

(deftest redefining-multimethod-dispatch-fn
  (testing "Redefining a multimethod function's dispatch function should have use the new dispatch function."
    ;; cleanly tear down the namespace so it is reloadable ;)
    (remove-ns 'clojure-repl-sufficient-p.defmulti)
    (require 'clojure-repl-sufficient-p.defmulti :reload)
    (is (= {:animal :octopus  :color :red}  @(resolve 'clojure-repl-sufficient-p.defmulti/pre-iteration-result-dispatch-fn)))
    (is (= {:plant :hydrangea :color :pink} @(resolve 'clojure-repl-sufficient-p.defmulti/post-iteration-result-dispatch-fn)))
    ;; clean up after ourselves
    (remove-ns 'clojure-repl-sufficient-p.defmulti)))

(deftest redefining-multimethod-default-value
  (testing "Redefining a multimethod function's default value should use the new dispatch function."
    (remove-ns 'clojure-repl-sufficient-p.defmulti)
    (require 'clojure-repl-sufficient-p.defmulti :reload)
    (is (= ["Woof" "Meow" "Pfsssssst!" "I don't know what sound that makes!"] @(resolve 'clojure-repl-sufficient-p.defmulti/pre-iteration-result-default)))
    (is (= ["Woof" "Meow" "Pfsssssst!" "I don't know what sound :kangaroo makes."] @(resolve 'clojure-repl-sufficient-p.defmulti/post-iteration-result-default)))
    (remove-ns 'clojure-repl-sufficient-p.defmulti)))

;; Function composition

;; Whenever a composite function is constructed by composing existing
;; functions, the standard usage is to refer to the constituent
;; functions as values. This takes the value of the constituent Vars
;; at the time the composite is defined, ignoring future redefinition
;; of those Vars.
;;
;; This is a common case in Clojure applications where simple
;; reevaluation does not work, particularly in web apps using the
;; "middleware" pattern. When all the definitions are in the same
;; file, as in these trivial examples, it is easy to work around, but
;; larger applications may have multiple layers of composition spread
;; across many source files.
;;
;; The first two cases are easy to work around by referencing the
;; constituent functions as literal Vars with #', but this requires
;; some understanding of how Vars work.

(deftest redefining-comp
  (testing "Functions passed as arguments to `comp` will have their
redefinitions used in the composite function"
    (remove-ns 'clojure-repl-sufficient-p.comp)
    (require 'clojure-repl-sufficient-p.comp :reload)
    (is (= {:a "pre-iteration", :b "pre-iteration"}
           @(resolve 'clojure-repl-sufficient-p.comp/pre-iteration-result)))
    (is (= {:a "post-iteration", :b "post-iteration"}
           @(resolve 'clojure-repl-sufficient-p.comp/post-iteration-result)))))

(deftest redefining-wrapped-function
  (testing "A function used as an argument to construct a composite
function will have its redefinition used in the composite function"
    (remove-ns 'clojure-repl-sufficient-p.wrapped)
    (require 'clojure-repl-sufficient-p.wrapped :reload)
    (is (= {:constituent "pre-iteration", :wrapper true}
           @(resolve 'clojure-repl-sufficient-p.wrapped/pre-iteration-result)))
    (is (= {:constituent "post-iteration", :wrapper true}
           @(resolve 'clojure-repl-sufficient-p.wrapped/post-iteration-result)))))

;; In this last case, it is the composite constructor which is being
;; modified rather than the constituent functions. This is difficult
;; to avoid without completely changing the way the composite is
;; defined.

(deftest redefining-wrapper-function
  (testing "A function called to construct a composite function will
have its redefinition used in the composite function"
    (remove-ns 'clojure-repl-sufficient-p.wrapper)
    (require 'clojure-repl-sufficient-p.wrapper :reload)
    (is (= {:wrapper "pre-iteration", :constituent true}
           @(resolve 'clojure-repl-sufficient-p.wrapper/pre-iteration-result)))
    (is (= {:wrapper "post-iteration", :constituent true}
           @(resolve 'clojure-repl-sufficient-p.wrapper/post-iteration-result)))))
