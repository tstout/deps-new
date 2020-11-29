(ns deps-new.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [deps-new.files :refer [mk-dirs]]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-n" "--namespace NS" "Project root namespace (required)"]
   ["-p" "--path RP" "Specify root path"
    :default "."]
   ["-r" "--repo repo-name" "Repository Name (required)"]
   ["-h" "--help"]])

(defn missing-required?
  "Returns true if opts is missing any of the required-opts"
  [opts]
  (not-every? opts #{:namespace :repo}))

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
      (missing-required? options) (do
                                    (println "Missing required arguments - Usage:")
                                    (println summary))
      (:help options) (println summary)
      :else (run options))))





