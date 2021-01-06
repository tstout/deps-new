(ns deps-new.gen-src
  (:require [deps-new.codegen :refer [pp-code]]
            [deps-new.defn :refer [mk-defn mk-main]]
            [deps-new.ns :refer  [mk-ns]]))

;; TODO - revist this with nested map destructuring...
(defn gen-main [opt]
  (let [src (-> :dirs
                opt
                :src)
        ns-org (-> :namespaces
                   opt
                   :ns-org
                   symbol)]
    (str (mk-ns ns-org :cli)
         \newline
         (mk-main))))



(comment


  (require '[deps-new.files :refer [prj-dirs]])
  (require '[clojure.pprint :as pp])

  (def dirs (prj-dirs
             (str (System/getProperty "user.home") "/test-prj")
             "foo.bar-t"))

  ;; intended usage
  (gen-main dirs)

  ;;
  )