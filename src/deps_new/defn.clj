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

(comment
  (mk-main [])

  ;;
  )
