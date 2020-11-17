(ns deps-new.codegen
  (:require [clojure.pprint :as pp]))

(defn with-pp [f]
  {:pre [(fn? f)]}
  (with-out-str
    (binding [pp/*print-suppress-namespaces* true]
      (f))))

(defn pp-code [code]
  (with-pp #(pp/pprint (macroexpand-1 code))))


(comment
  (pp-code '(defn foo [] (println "foo")))

  ;;
  )