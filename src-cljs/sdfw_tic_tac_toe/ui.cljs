(ns sdfw-tic-tac-toe.ui
(:require 
  [domina :as d]
  [domina.events :as de]))

(defn marker-chosen []
  (if (d/has-class? (d/by-id "x-marker-choose") "active") "x" "o"))

(defn transform-tile [tile]
  (cond
    (d/has-class? tile "x") :x
    (d/has-class? tile "o") :o
    :else nil))

(defn page-to-board []
  (let [tiles (d/by-class "tile")]
    (partition 3 (map transform-tile (d/nodes tiles)))))

(de/listen! (d/by-id "o-marker-choose") :click
  (fn [evt]
    (-> (de/target evt)
      (d/remove-class! "inactive")
      (d/add-class! "active"))
    (-> (d/by-id "x-marker-choose")
      (d/remove-class! "active")
      (d/add-class! "inactive"))))

(de/listen! (d/by-id "x-marker-choose") :click
  (fn [evt]
    (-> (de/target evt) (d/remove-class! "inactive") (d/add-class! "active"))
    (-> (d/by-id "o-marker-choose")
      (d/remove-class! "active")
      (d/add-class! "inactive"))))

(de/listen! (d/by-class "blank") :click
  (fn [evt]
    (-> (de/target evt)
      (d/remove-class! "blank")
      (d/add-class! (marker-chosen)))
    (d/log (page-to-board))))


(de/listen! (d/by-id "new-game") :click
  (fn [evt]
    (-> (d/by-class "tile")
      (d/remove-classes! "x")
      (d/remove-class! "o")
      (d/add-class! "blank"))))

