(ns deps-new.core
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [clojure.edn :as edn]))

(def std-prj-layout
  {:dirs ["src" "test" "resources"]})

(def std-files [["src" "user.clj"]
                ["." "deps.edn"]])

(defn load-res [res]
  (-> res io/resource slurp))

(defn load-edn-res [res]
  (->
   res
   load-res
   edn/read-string))

(defn mk-dirs [opts]
  (let [{:keys [dirs root-dir]} opts]
    (doseq [d dirs]
      (io/make-parents (str root-dir "/" d "/stub")))))

(defn mk-path [root fname]
  (str root "/" fname))

(defn mk-files [opts]
  (let [{:keys [files root-dir]} opts]
    (doseq [[path res] files]
      (let [in (-> res io/resource io/reader)
            out (io/as-file (mk-path (str root-dir "/" path) res))]
        (io/copy in out)))))

(defn -main [& args]
  (prn "main invoked"))


(comment
  (in-ns 'deps-new.core)
  (def dirs (merge std-prj-layout {:root-dir "/Users/btstout/repl"}))

  (def files (merge dirs {:files std-files}))

  (mk-files files)


  (mk-dirs dirs))




