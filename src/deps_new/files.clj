(ns deps-new.files
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(defn normalize-ns-name [ns-name]
  (-> ns-name
      (str/replace "-" "_")
      (str/replace "." "/")))

(defn prj-dirs
  "Define the project directory layout. Returns a map of project directories and root ns.
   Here is an example to demonstrate the shape of the returned map from invoking
   (prj-dirs \"/Users/tstout/test-prj\" \"foo.bar-t\")
   
   {:dirs
     {:src \"/Users/tstout/test-prj/src/foo/bar_t\"
      :res \"/Users/tstout/test-prj/resources\"
      :dev \"/Users/tstout/test-prj/dev\"
      :test \"/Users/tstout/test-prj/test/foo/bar_t\"
      :test-res \"/Users/tstout/test-prj/test/resources\"
      :root \"/Users/tstout/test-prj\"}
    :namespaces {:ns-org \"foo.bar-t\", :ns-normalized \"foo/bar_t\"}}
   "
  [root ns-name]
  (let [n-ns (normalize-ns-name ns-name)]
    {:dirs {:src (str root "/src/" n-ns)
            :res (str root "/resources")
            :dev (str root "/dev")
            :test (str root "/test/" n-ns)
            :test-res (str root "/test/resources/")
            :root root}
     :namespaces {:ns-org ns-name
                  :ns-normalized n-ns}}))

(defn gen-file
  "Create a java.io file corresponding to path-key/fname"
  [dirs path-key fname]
  (-> :dirs
      dirs
      path-key
      (str "/" fname)
      io/as-file))

(defn load-res [res]
  (-> res
      io/resource
      slurp))

(defn load-edn-res [res]
  (-> res
      load-res
      edn/read-string))

(defn mk-dirs [root ns-name]
  (let [dirs (prj-dirs root ns-name)]
    (doseq [d (vals (:dirs dirs))]
      (io/make-parents (str d "/_")))
    dirs))

;; TODO make a spec out of the dirs map.
(defn cp-res [dirs res-src dest]
  (println dirs)
  (let [in (-> res-src
               io/resource
               io/reader)
        out (-> :dirs
                dirs
                dest
                (str "/" res-src)
                io/as-file)]
    (io/copy in out)
    dirs))

(comment
  (def root (str (System/getProperty "user.home") "/test-prj"))
  (def dirs (prj-dirs root "foo.bar-t"))

  (gen-file dirs :src "main.clj")

  (->
   (mk-dirs root "foo.bar-t")
   (cp-res "deps.edn" :root)
   (cp-res "user.clj" :dev))

  (io/resource "deps.edn")

  (macroexpand-1 '(->
                   res
                   io/resource
                   slurp))

  (io/resource "user.clj")


  (time (merge {:a 1} {:b 2}))


  (io/make-parents (str root "/test/test-dir1/test-dir2/test-dir3"))

  (normalize-ns-name "corpname.deps-new")

  (prj-dirs "home" "foo.bar-t")

  (mk-dirs (str (System/getProperty "user.home") "/gen-test") "myprj.foo-bar")

  ;;
  )