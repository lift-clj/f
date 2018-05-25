(ns lift.f.functor
  (:refer-clojure :exclude [map])
  (:import [clojure.lang Fn IFn ISeq IPersistentCollection IPersistentMap
            IPersistentVector IPersistentList])
  (:require [clojure.core :as c]))

(alias 'c 'clojure.core)

(defprotocol Functor
  (-map [x f]))

(extend-protocol Functor
  IPersistentMap
  (-map [^IPersistentMap x ^IFn f]
    (.kvreduce x
               (fn [^IPersistentMap i k v]
                 (.assoc i k (.invoke f v)))
               x))

  IPersistentList
  (-map [x f]
    (c/map f x))

  IPersistentVector
  (-map [x f]
    (mapv f x))

  IPersistentCollection
  (-map [x f]
    (into x (c/map f x)))

  Fn
  (-map [x f]
    (comp f x))

  Object
  (-map [x f] x))

(defn map [f x]
  (-map x f))
