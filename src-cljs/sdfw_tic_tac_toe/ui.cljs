(ns sdfw-tic-tac-toe.ui
(:require 
  [domina :as d]
  [domina.events :as de]
  [sdfw-tic-tac-toe.game :as game]))

(defn marker-chosen []
  (if (d/has-class? (d/by-id "x-marker-choose") "active") "x" "o"))

(defn opponent [s]
  (if (= s "x") :o :x))

(defn transform-tile [tile]
  (cond
    (d/has-class? tile "x") :x
    (d/has-class? tile "o") :o
    :else nil))

(defn page-to-board []
  (let [tiles (d/by-class "tile")
         s-tiles (d/nodes tiles)
         t-tiles (map transform-tile s-tiles)
         p-tiles (partition 3 t-tiles)]
    (reduce #(conj %1 (vec %2)) [] p-tiles)))

(defn transform-move-tile [tile new-s]
  (if new-s
    (if-not
     (d/has-class? tile new-s)
     (-> tile
       (d/remove-class! "blank")
       (d/add-class! (name new-s))))))

(defn board-to-page [board]
  (let [tiles (d/by-class "tile")
         fboard (flatten board)
         s-tiles (d/nodes tiles)]
    (doall (map transform-move-tile s-tiles fboard))))


(de/listen! (d/by-id "o-marker-choose") :click
  (fn [evt]
    debugger
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
    (let [pb (page-to-board)
           my-marker (opponent (marker-chosen))
           nm (game/game-move my-marker (page-to-board))
           nb (:move nm)
           nbelief (:belief nm)]
      (d/log my-marker)
      (d/log pb)
      (d/log nm)
      (d/log nb)
      (d/log nbelief)
      (board-to-page nb)
      (d/set-text! (d/by-id "last-belief") nbelief))))


(de/listen! (d/by-id "new-game") :click
  (fn [evt]
    (doseq [n (d/nodes (d/by-class "tile"))]
      (d/remove-class! n "x")
      (d/remove-class! n "o")
      (d/add-class! n "blank")
      (d/set-text! (d/by-id "last-belief") "None"))))

