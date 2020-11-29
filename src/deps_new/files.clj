(ns deps-new.files
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(defn normalize-ns-name [ns-name]
  (->
   ns-name
   (str/replace "-" "_")
   (str/replace "." "/")))

(defn prj-dirs
  "Define the project directory layout. Returns a map of project directories."
  [root ns-name]
  (let [n-ns (normalize-ns-name ns-name)]
    {:src (str root "/src/" n-ns)
     :res (str root "/resources")
     :dev (str root "/dev")
     :test (str root "/test/" n-ns)
     :test-res (str root "/test/resources/")
     :root root}))

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

;; TODO - this should return the prj-dirs map
(defn mk-dirs [root ns-name]
  (doseq [d (vals (prj-dirs root ns-name))]
    (io/make-parents (str d "/_"))))

(def std-prj-layout
  {:dirs ["src" "test" "resources"]})

(def std-files [["src" "user.clj"]
                ["." "deps.edn"]])

(defn mk-path [root fname]
  (str root "/" fname))

(defn cp-res [dirs res-src dest]
  (let [in (-> 
            res-src 
            io/resource 
            io/reader)
        out (-> 
             dest 
             dirs 
             (str "/" res-src)
             io/as-file)]
    (io/copy in out)))

(defn mk-files [opts]
  (let [{:keys [files root-dir]} opts]
    (doseq [[path res] files]
      (let [in (-> res io/resource io/reader)
            out (io/as-file (mk-path (str root-dir "/" path) res))]
        (io/copy in out)))))

(comment
  (def root (str (System/getProperty "user.home") "/test-prj"))
  (def dirs (prj-dirs root "foo.bar-t"))

  (mk-dirs root "foo.bar-t")
  (cp-res dirs "deps.edn" :root)

  (io/resource "deps.edn")
  
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