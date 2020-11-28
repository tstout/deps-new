(ns deps-new.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [deps-new.files :refer [mk-dirs]]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-n" "--namespace ns" "Project root namespace"]
   ["-p" "--path root-path" "Specify root path"
    :default "."]
   ["-r" "--repo repo-name" "Repository Name"
    :default "foo"]
   ["-h" "--help"]])

(defn run [options]
  (let [{:keys [namespace path repo]} options]
    (println (format "generate for namespace %s :path %s :repo %s" 
                     namespace 
                     path
                     repo))
    (mk-dirs (str path "/" repo) namespace)))

(defn -main [& args]
  (let [{:keys [options
                arguments
                summary
                errors]} (parse-opts args cli-options)]
    (cond
      errors (println errors)
      (:help options) (println summary)
      :else (run options))))





