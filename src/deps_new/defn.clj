(ns deps-new.defn
  (:require [deps-new.codegen :refer [pp-code]]))

(defmacro mk-fn [name args body]
  `(defn ~name [~@args]
     ~body))

;; (defn mk-defn [name args body]
;;   (pp-code
;;    `(mk-fn ~name ~args ~body)))

(defn mk-defn [name args body]
   `(mk-fn ~name ~args ~body))

(defn mk-main []
  '(defn -main [& args] (println "hello world")))

(comment
  (pp-code (mk-main))
  ;;
  )
