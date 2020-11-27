(ns deps-new.files
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(defn normalize-ns-name [ns-name]
  (->
   ns-name
   (str/replace "-" "_")
   (str/replace "." "/")))

(defn prj-dirs [root ns-name]
  (let [n-ns (normalize-ns-name ns-name)]
    {:src (str root "/src/" n-ns)
     :res (str root "/resources")
     :dev (str root "/dev")
     :test (str root "/test/" n-ns)}))

(defn load-res [res]
  (->
   res
   io/resource
   slurp))

(defn load-edn-res [res]
  (->
   res
   load-res
   edn/read-string))

(defn mk-dirs [root ns-name]
  (doseq [d (vals (prj-dirs root ns-name))]
    (io/make-parents (str d "/_"))))

(def std-prj-layout
  {:dirs ["src" "test" "resources"]})

(def std-files [["src" "user.clj"]
                ["." "deps.edn"]])

(defn mk-path [root fname]
  (str root "/" fname))

(defn mk-files [opts]
  (let [{:keys [files root-dir]} opts]
    (doseq [[path res] files]
      (let [in (-> res io/resource io/reader)
            out (io/as-file (mk-path (str root-dir "/" path) res))]
        (io/copy in out)))))


;; (defn prj-tree [root ns-name]
;;   (let [src-path (str root "/src/" (normalize-ns-name ns-name))]
;;     [(str src-path "/core.clj")]))


(comment
  (def root (System/getProperty "user.home"))

  (macroexpand-1 '(->
                   res
                   io/resource
                   slurp))


  (io/make-parents (str root "/test/test-dir1/test-dir2/test-dir3"))

  (normalize-ns-name "corpname.deps-new")

  (prj-dirs "home" "foo.bar-t")

  (mk-dirs (str (System/getProperty "user.home") "/gen-test") "myprj.foo-bar")

  ;;
  )