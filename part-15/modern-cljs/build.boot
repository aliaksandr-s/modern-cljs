(set-env!
 :source-paths #{"src/clj" "src/cljs" "src/cljc"}
 :resource-paths #{"html"}

 :dependencies '[[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.473"]
                 [adzerk/boot-cljs "1.7.228-2"]
                 [pandeiro/boot-http "0.7.6"]         ;; add http dependency
                 [org.clojure/tools.nrepl "0.2.12"]
                 [adzerk/boot-reload "0.5.1"]
                 [adzerk/boot-cljs-repl "0.3.3"]
                 [adzerk/boot-test "1.2.0"]
                 [com.cemerick/piggieback "0.2.1"]
                 [weasel "0.7.0"]
                 [org.clojars.magomimmo/domina "2.0.0-SNAPSHOT"]
                 [hiccups "0.3.0"]
                 [compojure "1.5.2"]
                 [org.clojars.magomimmo/shoreleave-remote-ring "0.3.3"]
                 [org.clojars.magomimmo/shoreleave-remote "0.3.1"]
                 [javax.servlet/javax.servlet-api "3.1.0"]
                 [org.clojars.magomimmo/valip "0.4.0-SNAPSHOT"]
                 [enlive "1.1.6"]
                 [crisptrutski/boot-cljs-test "0.2.1-SNAPSHOT"]]) ;; required by boot-http

(require '[adzerk.boot-cljs :refer [cljs]]
         '[pandeiro.boot-http :refer [serve]]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-test :refer [test]]
         '[crisptrutski.boot-cljs-test :refer [test-cljs]]) ;; make serve task visible

(deftask testing
  "Add test/cljc for CLJ/CLJS testing purpose"
  []
  (set-env! :source-paths #(conj % "test/cljc"))
  identity)

(deftask tdd
  []
  (comp
   (serve :handler 'modern-cljs.core/app
          :resource-root "target"
          :reload true)
   (testing)
   (watch)
   (reload :ws-host "localhost")
   (cljs-repl)
   (test-cljs :out-file "main.js"
              :js-env :phantom
              :namespaces '#{modern-cljs.shopping.validators-test}
              :update-fs? true)
   (test :namespaces '#{modern-cljs.shopping.validators-test})
   (target :dir #{"target"})))

(deftask dev
  "Launch Immediate Feedback Development Environment"
  []
  (comp
   (serve :handler 'modern-cljs.core/app  ;; add ring handler
          :resource-root "target"            ;; add resource-path
          :reload true)                      ;; reload server side ns
   (watch)
   (reload)
   (cljs-repl) ;; before cljs task
   (cljs)
   (target :dir #{"target"})))