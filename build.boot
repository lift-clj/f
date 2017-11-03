(def +project+ 'lift/f)
(def +version+ "0.1.0")
(def +description+ "Efficient Functors for Clojure")

(def dependencies
  '[[org.clojure/clojure "1.8.0"]])

(set-env! :dependencies dependencies
          :source-paths #{"src"}
          :resource-paths #{"src"})

(task-options!
 pom {:project +project+
      :version +version+
      :description +description+
      :license {"Eclipse Public License"
                "http://www.eclipse.org/legal/epl-v10.html"}
      :scm {:url "git@github.com:andrewmcveigh/f.git"}}
 push {:repo "clojars"}
 target {:dir #{"target"}})

(defn cider? []
  (get (ns-publics 'boot.user) 'cider))

(replace-task!
 [r repl] (comp ((or (cider?) (constantly identity))) r))

(deftask build []
  (comp (pom) (jar) (target)))

(deftask deploy []
  (comp (build) (install) (push)))
