(ns deps-new.gen-src
  (:require [deps-new.codegen :refer [pp-code]]
            [deps-new.defn :refer [mk-main]]
            [deps-new.ns :refer  [mk-ns]]))


;; TODO - Combine this with codegen ns

(defn gen-main
  "Create a collection of source code lines representing the main source file.
   The source file is assumed to be named core.clj"
  [prj]
  (let [ns-org (-> :namespaces
                   prj
                   :ns-org
                   (str ".core")
                   symbol)]
    (->> (mk-ns ns-org :cli)
         mk-main
         (map pp-code))))

(comment
  (require '[deps-new.files :refer [prj-dirs]])

  (def prj (prj-dirs
            (str (System/getProperty "user.home") "/test-prj")
            "foo.bar-t"))

  ;; intended usage
  (gen-main prj)

  ;; Create a single string of source code
  (->> prj
       gen-main
       (apply str))

  ;;
  )