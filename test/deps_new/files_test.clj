(ns deps-new.files-test
  (:require [expectations.clojure.test :refer [defexpect expect expecting]]
            [clojure.test :refer [use-fixtures]]
            [deps-new.files :refer [mk-dirs prj-dirs]]
            [clojure.java.io :as io]))

(def root (str (System/getProperty "user.home") "/test-prj"))
(def test-ns "tstout.deps-new")
(def dirs (prj-dirs root test-ns))

(defn delete-dirs
  "Recursively delete a directory."
  [^java.io.File file]
  (when (.isDirectory file)
    (doseq [file-in-dir (.listFiles file)]
      (delete-dirs file-in-dir)))
  (io/delete-file file))

(defn dir-cleanup [f]
  (f)
  (delete-dirs (io/as-file root)))

(use-fixtures :once dir-cleanup)

(defn file-exists [x]
  (-> x io/file .exists))

(defexpect generates-expected-directories
  (mk-dirs root test-ns)
  (doseq [d (vals (:dirs dirs))]
    (expect true (file-exists d) (format "Expected directory %s to exist" d))))

(comment
  (file-exists (System/getProperty "user.home"))


  (mk-dirs root test-ns)
  ;;
  )