(ns deps-new.defn)

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

(defn mk-build [ns-org code-vec]
  (conj
   code-vec
   '(def version (format "1.0.%s" (b/git-count-revs nil)))
   '(def class-dir "target/classes")
   '(def basis (b/create-basis {:project "deps.edn"}))
   (-> `(def uber-file (format "target/%s-%s.jar" ~(name ns-org) version))
       list
       first)

   '(defn clean [_]
      (b/delete {:path "target"}))

   (-> `(defn uberjar [_]
          (clean nil)
          (b/copy-dir {:src-dirs ["src" "resources"]
                       :target-dir class-dir})
          (b/compile-clj {:basis basis
                          :src-dirs ["src"]
                          :class-dir class-dir})
          (b/uber {:class-dir class-dir
                   :uber-file uber-file
                   :basis basis
                   :main (symbol ~(str ns-org ".core"))}))
       list
       first)))

(comment
  (mk-test [])
  (mk-main [])
  (mk-build 'ns-org2 [])

  ;;
  )
