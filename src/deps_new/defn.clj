(ns deps-new.defn
  (:require [deps-new.codegen :refer [pp-code]]))

(defmacro mk-fn [name args body]
  `(defn ~name [~@args]
     ~body))

(defn mk-defn [name args body]
  (pp-code
   `(mk-fn ~name ~args ~body)))

(comment
  (macroexpand-1 '(mk-fn foo [a b c] (println "foo")))

  (mk-defn 'foo '(a b c) '(println "foo"))

  (pp-code '(defn foo [a b c] (println "foo")))
  
  (mk-defn 'bar [] '(println "bar"))
  ;;
  )
