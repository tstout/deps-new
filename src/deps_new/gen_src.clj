(ns deps-new.gen-src
  (:require [deps-new.codegen :refer [pp-code]]
            [deps-new.defn :refer [mk-defn mk-fn]]
            [deps-new.ns :refer  [mk-require]]))

(defn gen-main [opt]
  (let [{:keys [dirs namespaces]} opt]
    ))


(mk-require 'deps-new.ns [:java-io :shell :cli])

(def hello "hello")

(type 'hello)

(meta (var hello))


(var hello)

((juxt take drop) 3 (range 9))

((juxt take drop) 3)

(take 3)

(drop 3)

(dotimes [x 10]
  (prn x))



(type #'hello)

(macroexpand-1 #'hello)