(ns modern-cljs.remotes
  (:require [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]
            [modern-cljs.login.validators :as v]))

(defremote calculate [quantity price tax discount]
  (let [q (read-string quantity)
        p (read-string price)
        t (read-string tax)
        d (read-string discount)]
    (-> (* q p)
        (* (+ 1 (/ t 100)))
        (- d))))

(defremote email-domain-errors [email]
  (v/email-domain-errors email))