(ns deps-new.files
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]
            [deps-new.pp :refer [pp-code]]
            [deps-new.gen-src :refer [gen-main
                                      gen-test
                                      gen-build]]))

(defn normalize-ns-name [ns-name]
  (-> ns-name
      (str/replace "-" "_")
      (str/replace "." "/")))

(defn file-exists [x]
  (-> x io/file .exists))

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
  "Create a java.io file corresponding to path-key/fname for the specified project."
  [prj path-key fname]
  (-> :dirs
      prj
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

(defn write-main [prj]
  (->> prj
       gen-main
       (apply str)
       (spit (gen-file prj :src "core.clj")))
  prj)

(defn write-test [prj]
  (->> prj
       gen-test
       (apply str)
       (spit (gen-file prj :test "core_test.clj")))
  prj)

(defn write-build [prj]
  (->> prj
       gen-build
       (apply str)
       (spit (gen-file prj :root "build.clj")))
  prj)

(defn mk-dirs [root ns-name]
  (let [dirs (prj-dirs root ns-name)]
    (doseq [d (vals (:dirs dirs))]
      (io/make-parents (str d "/_")))
    dirs))

;; TODO make a spec out of the prj map.
(defn cp-res [prj res-src dest]
  (let [in (-> res-src
               io/resource
               io/reader)
        out (-> :dirs
                prj
                dest
                (str "/" res-src)
                io/as-file)]
    (io/copy in out)
    prj))

(defn merge-into-deps-edn
  "Mege data into a deps.edn file and save the result."
  ([x file]
   (if-let [deps (some-> file
                         #_io/resource
                         io/reader
                         slurp
                         edn/read-string)]
     (as-> deps d
       (merge-with into x d)
       (pp-code d :suppress-ns false)
       (spit file d))
     (throw (Exception. (str "Could not find " file)))))
  ([x]
   (merge-into-deps-edn x "./deps.edn")))

(defn write-main-alias [prj]
  (let [main-ns (-> :namespaces prj :ns-org)
        main-ns-key (keyword main-ns)
        deps-edn (-> :dirs prj :root (str "/deps.edn"))]
    (merge-into-deps-edn
     {:aliases {main-ns-key
                {:main-opts ["-m" (str main-ns ".core")]}}}
     deps-edn)
    prj))

(comment
  (def root (str (System/getProperty "user.home") "/test-prj"))
  (def dirs (prj-dirs root "foo.bar-t"))

  (gen-file dirs :src "main.clj")

  (merge-into-deps-edn {:deps {'http-kit {:mvn/version "2.1.19"}}})

  (merge-into-deps-edn {:aliases {:test-prj {:main-opts ["-m" "test-prj.core"]}}})

  (normalize-ns-name "corpname.deps-new")

  (prj-dirs "home" "foo.bar-t")

  (mk-dirs (str (System/getProperty "user.home") "/gen-test") "myprj.foo-bar")

  ;;
  )