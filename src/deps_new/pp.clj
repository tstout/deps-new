(ns deps-new.pp
  (:require [clojure.pprint :as pp]))

(defn with-pp [f options]
  {:pre [(fn? f)]}
  (let [opts (apply hash-map options)
        {:keys [suppress-ns namespace-maps]
         :or {suppress-ns true namespace-maps false}} opts]
    (with-out-str
      (binding [pp/*print-suppress-namespaces* suppress-ns
                *print-namespace-maps* namespace-maps]
        (f)))))

(defn pp-code
  "Pretty print code. Options include:
   :suppress-ns    true/false  - Do not print namespaces (default true)
   :namespace-maps true/false  - Show map namespaces (default false)"
  [code & opts]
  (with-pp
    (fn []
      (pp/with-pprint-dispatch
        pp/code-dispatch
        (pp/pprint code)))
    (if (nil? opts) '() opts)))

(comment
  (pp-code '(defn foo "this is a test of doc string" [] (println "foo")))

  (pp-code
   '(defn foo "this is a test of doc string" [] (println "foo"))
   :suppress-ns false)

  (pp-code '(defn foo "A function for doing all kinds of things" [] (prn "hello") (prn "goodbye")))

  (pp/pprint " ")

  (str \newline)

  (pp/pprint #{1 2 3})
  (pp/pprint '(1 2 3))
  (pp/pprint [1 2 3])


  ()

  ;;
  )