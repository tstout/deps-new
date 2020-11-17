(ns deps-new.files
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(defn normalize-ns-name [ns-name]
  (str/replace ns-name "-" "_"))

(defn prj-dirs [root ns-name]
  [(str root "/src/" (normalize-ns-name ns-name))
   (str root "/resources")
   (str root "/dev")])

(defn load-res [res]
  (-> res io/resource slurp))

(defn load-edn-res [res]
  (->
   res
   load-res
   edn/read-string))

(defn mk-dirs [root ns-name]
  (doseq [d (prj-dirs root ns-name)]
    (io/make-parents (str d "/_"))))

(def std-prj-layout
  {:dirs ["src" "test" "resources"]})

(def std-files [["src" "user.clj"]
                ["." "deps.edn"]])

;; (defn mk-dirs [opts]
;;   (let [{:keys [dirs root-dir]} opts]
;;     (doseq [d dirs]
;;       (io/make-parents (str root-dir "/" d "/_")))))

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

  (io/make-parents (str root "/test/test-dir1/test-dir2/test-dir3"))

  (normalize-ns-name "deps.new")

  (prj-dirs "foo" "foo.bar")

  (mk-dirs (str (System/getProperty "user.home") "/gen-test") "foo-bar")

  ;;
  )