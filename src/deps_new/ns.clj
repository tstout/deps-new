(ns deps-new.ns
  "Tools for generating ns forms for the purpose of source code generation."
  (:require [clojure.pprint :as pp]))

(def requires {:java-io '[clojure.java.io :as io]
               :shell '[clojure.java.shell :as shell]})

(defn select-deps [deps]
  (->>
   deps
   (select-keys requires)
   vals))

(defn pp-code [code]
  (with-out-str
    (binding [pp/*print-suppress-namespaces* true]
      (pp/pprint (macroexpand-1 code)))))

(defmacro mk-ns [name deps]
  `(ns ~name
     (:require ~@deps)))

(defn mk-require
  "Generate a namespace form as a string, 
   with the intention of writing this string to a source file."
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