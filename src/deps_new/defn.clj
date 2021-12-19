(ns deps-new.defn)

(defmacro mk-fn [name args body]
  `(defn ~name [~@args]
     ~body))

(defn mk-defn [name args body]
  `(mk-fn ~name ~args ~body))

(defn mk-main [code-vec]
  (conj
   code-vec
   '(defn -main [& args] (println "hello world"))))

(defn mk-test [code-vec]
  (conj
   code-vec
   '(defn setup [f] (f))
   '(use-fixtures :once setup)
   '(defexpect fix-me-I-fail
      (expect 1 0))
   '(comment
      *e
      (run-tests)
      "see https://github.com/clojure-expectations/clojure-test for examples")))

;;(use-fixtures :once dir-cleanup)))


(comment
  (mk-test [])
  (mk-main [])
  ;;
  )
