(ns clojure-repl-sufficient-p.defprotocol)

;; a = Record, Type
;; b = inside (I), extend-protocol (P), extend-type (T)
;; c = redefine type (Rt), redefine protocol (Rp)

;; test a x b x c
;; that means 12 different tests

;; PLUS

;; a = String
;; b = extend-protocol (P), extend-type (T)
;; c = redefine protocol (RP)

;; test a x b x c
;; which means two more tests

;; I propose a systematic way to name everything to make it
;; easy to find what you're looking for.

;; example Record name: "RecordRIRt" means testing a Record
;; with the protocol extended inside the defrecord and the
;; type is redefined

(defprotocol ProtocolRIRt
  (rirt [x]))

(defrecord RecordRIRt []
  ProtocolRIRt
  (rirt [x] :before))

(defrecord RecordRIRt [])



(defprotocol ProtocolTIRt
  (tirt [x]))

(deftype TypeTIRt []
  ProtocolTIRt
  (tirt [x] :before))

(deftype TypeTIRt []
  ProtocolTIRt)




(defprotocol ProtocolRPRt
  (rprt [x]))

(defrecord RecordRPRt [])

(extend-protocol ProtocolRPRt
  RecordRPRt
  (rprt [x] :before))

(defrecord RecordRPRt [])




(defprotocol ProtocolTPRt
  (tprt [x]))

(deftype TypeTPRt [])

(extend-protocol ProtocolTPRt
  TypeTPRt
  (tprt [x] :before))

(deftype TypeTPRt [])




(defprotocol ProtocolRTRt
  (rtrt [x]))

(defrecord RecordRTRt [])

(extend-type RecordRTRt
  ProtocolRTRt
  (rtrt [x] :before))

(defrecord RecordRTRt [])




(defprotocol ProtocolTTRt
  (ttrt [x]))

(deftype TypeTTRt [])

(extend-type TypeTTRt
  ProtocolTTRt
  (ttrt [x] :before))

(deftype TypeTTRt [])




(defprotocol ProtocolRIRp
  (rirp [x]))

(defrecord RecordRIRp []
  ProtocolRIRp
  (rirp [x] :before))

(defprotocol ProtocolRIRp
  (rirp [x]))



(defprotocol ProtocolTIRp
  (tirp [x]))

(deftype TypeTIRp []
  ProtocolTIRp
  (tirp [x] :before))

(defprotocol ProtocolTIRp
  (tirp [x]))




(defprotocol ProtocolRPRp
  (rprp [x]))

(defrecord RecordRPRp [])

(extend-protocol ProtocolRPRp
  RecordRPRp
  (rprp [x] :before))

(defprotocol ProtocolRPRp
  (rprp [x]))




(defprotocol ProtocolTPRp
  (tprp [x]))

(deftype TypeTPRp [])

(extend-protocol ProtocolTPRp
  TypeTPRp
  (tprp [x] :before))

(defprotocol ProtocolTPRp
  (tprp [x]))




(defprotocol ProtocolRTRp
  (rtrp [x]))

(defrecord RecordRTRp [])

(extend-type RecordRTRp
  ProtocolRTRp
  (rtrp [x] :before))

(defprotocol ProtocolRTRp
  (rtrp [x]))





(defprotocol ProtocolTTRp
  (ttrp [x]))

(deftype TypeTTRp [])

(extend-type TypeTTRp
  ProtocolTTRp
  (ttrp [x] :before))

(defprotocol ProtocolTTRp
  (ttrp [x]))



(defprotocol ProtocolSTRp
  (strp [x]))

(extend-type String
  ProtocolSTRp
  (strp [x] :before))

(defprotocol ProtocolSTRp
  (strp [x]))



(defprotocol ProtocolSPRp
  (sprp [x]))

(extend-protocol ProtocolSPRp
  String
  (sprp [x] :before))

(defprotocol ProtocolSPRp
  (sprp [x]))
