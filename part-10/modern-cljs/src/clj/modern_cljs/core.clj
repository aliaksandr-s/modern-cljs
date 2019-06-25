(ns modern-cljs.core
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found files resources]]
            ))

(defroutes handler
  (GET "/" [] "Hello from Compojure")
  (files "/" {:root "target"})
  (resources "/" {:root "target"})
  (not-found "Page Not Found"))