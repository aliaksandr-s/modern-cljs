(ns modern-cljs.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found files resources]]
            [modern-cljs.login :refer [authenticate-user]]))

(defroutes handler
  (GET "/" [] "Hello from Compojure")
  (files "/" {:root "target"})
  (POST "/login" [email password] (authenticate-user email password))
  (resources "/" {:root "target"})
  (not-found "Page Not Found"))