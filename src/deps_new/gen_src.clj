(ns deps-new.gen-src
  (:require [deps-new.pp :refer [pp-code]]
            [deps-new.defn :refer [mk-main mk-test mk-build]]
            [deps-new.ns :refer  [mk-ns mk-main-ns]]))

(defn gen-main
  "Create a collection of source code lines representing the main source file.
   The source file is assumed to be named core.clj."
  [prj]
  (let [ns-org (-> :namespaces
                   prj
                   :ns-org
                   (str ".core")
                   symbol)]
    (->> (mk-main-ns ns-org :cli)
         mk-main
         (map pp-code)
         (interpose \newline))))

(defn gen-test
  "Create a collection of source code lines representing an example test.
   The source file is assumed to be named core-test."
  [prj]
  (let [ns-org (-> :namespaces
                   prj
                   :ns-org
                   (str ".core-test")
                   symbol)]
    (->> (mk-ns ns-org :test :test-expectations)
         mk-test
         (map pp-code)
         (interpose \newline))))

(defn gen-build
  "Create a build.clj with typical build functions"
  [prj]
  (let [ns-org (-> :namespaces
                   prj
                   :ns-org
                   symbol)]
    (->> (mk-ns 'build :build)
         (mk-build ns-org)
         (map pp-code)
         (interpose \newline))))


(comment
  (require '[deps-new.files :refer [prj-dirs]])

  (def prj (prj-dirs
            (str (System/getProperty "user.home") "/test-prj")
            "foo.bar-t"))

  (pp-code \newline)

  (gen-test prj)

  ;; intended usage
  (gen-main prj)



  ;; Create a single string of source code
  (->> prj
       gen-main
       (apply str))

  ;;
  )