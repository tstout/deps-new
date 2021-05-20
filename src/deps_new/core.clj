(ns deps-new.core
  (:require [deps-new.files :refer [mk-dirs
                                    cp-res
                                    write-main
                                    write-test
                                    write-main-alias]]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  [["-n" "--namespace NS" "Project root namespace (required)"]
   ["-p" "--path RP" "Specify root path"
    :default "."]
   ["-r" "--repo repo-name" "Repository Name (required)"]
   ["-h" "--help"]])

(defn missing-required?
  "Returns true if opts is missing any of the required options"
  [opts]
  (not-every? opts #{:namespace :repo}))

(defn run [options]
  (let [{:keys [namespace path repo]} options]
    (->
     (mk-dirs (str path "/" repo) namespace)
     (cp-res "deps.edn" :root)
     (cp-res "user.clj" :dev)
     (cp-res ".gitignore" :root)
     write-main
     write-main-alias
     write-test)))

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


(comment
  (missing-required? {:namespace 1 :repo 2})
  ;;
  )


