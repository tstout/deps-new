(ns deps-new.ns
  "Tools for generating ns forms for the purpose of source code generation."
  (:require [deps-new.pp :refer [pp-code]]))

(def requires
  "Mapping of require dependencies available for use in ns form generation"
  {:java-io '[clojure.java.io :as io]
   :shell '[clojure.java.shell :as shell]
   :test '[clojure.test :refer [use-fixtures]]
   :test-expectations '[expectations.clojure.test :refer [defexpect expect expecting]]
   :cli '[clojure.tools.cli :refer [parse-opts]]})

(defn select-deps [& deps]
  (->> deps
       (select-keys requires)
       vals))

(defn mk-ns [name & deps]
  [`(ns ~name
      (:require ~@(apply select-deps deps)))])

(comment
  (select-deps :java-io :shell)

  ;; Intended usage
  (->
   (mk-ns 'foo-bar :shell :java-io)
   pp-code)
  ;;
  )