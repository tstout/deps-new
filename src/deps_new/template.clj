(ns deps-new.template)

(defn mk-pairs
  "Transform a map into a vector of key value pairs.
  For example: {:a 1 :b 2} becomes [[:a 1] [:b 2]]"
  [m]
  (for [[k v] m] [k v]))

(defn txt-from-template
  "Given a map of parameters, treat the key as a variable
  name and the value as a replacement value to be applied to
  an arbitrary text template"
  [parms template]
  (->>
   parms
   mk-pairs
   (reduce
    (fn [t kv] (let [[k v] kv] (.replace t k v)))
    template)))

(comment
  (in-ns 'deps-new.template)

   ;;org.clojure/java.jdbc {:mvn/version "0.7.5"}

  (def template "
     {:deps {$jdbc-group {:mvn/version $jdbc-version}}}
     ")

  (def vars {"$jdbc-group" "org.clojure/java.jdbc"
             "$jdbc-version" "0.7.5"})

  (txt-from-template vars template))