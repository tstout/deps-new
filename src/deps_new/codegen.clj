(ns deps-new.codegen
  (:require [clojure.pprint :as pp]))

(defn with-pp [f]
  {:pre [(fn? f)]}
  (with-out-str
    (binding [pp/*print-suppress-namespaces* true]
      (f))))

;; (defn pp-code [code]
;;   (with-pp (fn []
;;              (pp/with-pprint-dispatch 
;;               pp/code-dispatch 
;;               (pp/pprint (macroexpand-1 code))))))

(defn pp-code [code]
  (with-pp (fn []
             (pp/with-pprint-dispatch
               pp/code-dispatch
               (pp/pprint code)))))




(comment
  
  (pp-code '(defn foo "this is a test of doc string" [] (println "foo")))
  
  (pp-code '(defn foo "A function for doing all kinds of things" [] (prn "hello") (prn "goodbye")))
  
  
  ;;
  )