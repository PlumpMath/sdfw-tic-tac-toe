(ns sdfw-tic-tac-toe.ui
  (:require 
    [domina :as d]
    [domina.css :as dc]
    [domina.events :as de]))


(de/listen! (d/by-id "o-marker-choose") :click
  (fn [evt]
    (-> (de/target evt) (d/remove-class! "active") (d/add-class! "inactive"))
    (-> (d/by-id "x-marker-choose") (d/remove-class! "inactive") (d/add-class! "active"))))

(de/listen! (d/by-id "x-marker-choose") :click
  (fn [evt]
    (-> (de/target evt) (d/remove-class! "active") (d/add-class! "inactive"))
    (-> (d/by-id "o-marker-choose") (d/remove-class! "inactive") (d/add-class! "active"))))

