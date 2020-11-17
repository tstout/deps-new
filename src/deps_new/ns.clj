(ns deps-new.ns
  "Tools for generating ns forms for the purpose of source code generation."
  (:require [deps-new.codegen :refer [pp-code]]))

(def requires {:java-io '[clojure.java.io :as io]
               :shell '[clojure.java.shell :as shell]})

(defn select-deps [deps]
  (->>
   deps
   (select-keys requires)
   vals))

(defmacro mk-ns [name deps]
  `(ns ~name
     (:require ~@deps)))

(defn mk-require
  "Generate a namespace form as a string, 
   for the purpose of writing this string to a source file."
  [ns-name namespaces]
  {:pre [(symbol? ns-name)
         (vector? namespaces)]}
  (pp-code
   `(mk-ns ~ns-name ~(select-deps namespaces))))

(comment
  (pp-code '(mk-ns sample [[clojure.java.io :as io]
                           [clojure.java.shell :as shell]]))

  (select-deps [:java-io :shell])

  (pp-code `(mk-ns sample ~(select-deps [:java-io :shell])))

  ;; Intended usage
  (mk-require 'sample [:java-io :shell])
  
  ;;
  )