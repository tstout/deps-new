# deps-new
Create a new Clojure deps.edn project

# Usage
Add this to your :aliases in ~/.clojure/deps.edn
```
:deps-new {:extra-deps
                      {deps-new/deps-new
                       {:git/url "https://github.com/tstout/deps-new"
                        :sha "a9960a8548f203589c7974a0bfcec082298b5c5b"}}
                      :main-opts ["-m" "deps-new.core"]}
```


# TODO
- include more detailed instructions
- Document project structure
- Add funcitioning test template
- Adjust formatting in main
- remove unused deps in generated code