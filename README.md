# deps-new
Create a new Clojure deps.edn project

# Usage
Add this to your :aliases in ~/.clojure/deps.edn
```
:deps-new {:extra-deps
                      {deps-new/deps-new
                       {:git/url "https://github.com/tstout/deps-new"
                        :sha "997facf2135ec0c18f6674e27c4fb9422792a018"}}
                      :main-opts ["-m" "deps-new.core"]}
```
Example usage:
```
clj -M:deps-new -n gitlab-io -r gitlab-io
```
This will create a project with a core namespace of _gitlab-io_. The repository name specified by -r
is _gitlab-io_, the same as the core namespace. This will create the following project structure:
```
gitlab-io
├── .gitignore
├── deps.edn
├── dev
│   └── user.clj
├── resources
├── src
│   └── gitlab_io
│       └── core.clj
└── test
    ├── gitlab_io
    │   └── core_test.clj
    └── resources
```
Execute main:
```
clj -M:gitlab-io
```

Execute tests:
```
clj -M:test:runner
```
# TODO
- include more detailed instructions