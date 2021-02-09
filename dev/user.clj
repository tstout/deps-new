(ns user)
;; Your REPL Customizations here...
(def debug (atom []))

(defn add-to-debug [x]
  (swap! debug conj x))

(add-tap add-to-debug)

(defmacro debug* [args]
  `(do
     (tap> (sorted-map :fn (quote ~args) :ret ~args))
     ~args))

(defmacro debug->> [& fns]
  (reset! debug [])
  `(->> ~@(interleave fns (repeat 'debug*))))

(println "loaded user.clj")

(defn foo-t [] (prn "hello..."))


(comment
  (debug->> "a" identity)
  ;;
  )