(ns sdfw-tic-tac-toe.game)

(def beliefs
  { :win "I am going to win."
    :lose "My opponent is going to win."
    :noone "No one is going to win."})

(def belief-action-preferences
  { (beliefs :win) 1
    (beliefs :lose) 2
    (beliefs :noone) 3})

(defn win-across [s board]
  (some true?
    (map #(every? #{s} %1) board)))

(defn extract-down [board]
  [(map first board) (map second board) (map last board)])

(defn win-down [s board]
  (some true?
    (map #(every? #{s} %1) (extract-down board))))

(defn extract-diag [board]
  [ [(first (first board)) (second (second board)) (last (last board))]
    [(first (last board)) (second (second board)) (last (first board))]])

(defn win-diag [s board]
  (some true?
    (map #(every? #{s} %1) (extract-diag board))))

(defn win [s board]
  (or (win-across s board)
    (win-down s board)
    (win-diag s board)))

(defn possible-moves [s board]
  (remove nil?
  (for [ x (range 3)
        y (range 3)]
   (let [spot (nth (nth board x) y)]
     (if (nil? spot)
       (assoc-in board [x y] s))))))

(defn opponent [s]
  (if (= s :x) :o :x))

(defn belief-about-move [s move]
  (cond
    (win s move) (beliefs :win)
    (win (opponent s) move) (beliefs :lose)
    :else (beliefs :noone)))

(defn add-beliefs-to-pmoves [s board]
  (reduce #(conj %1 {:move %2
                      :belief (belief-about-move s %2)})
    [] (possible-moves s board)))


(defn merge-beliefs [my-move op-move]
  (if (= (:belief op-move) (beliefs :win))
    (assoc my-move :belief (beliefs :lose))
    my-move))


(defn calculate-beliefs [s board]
  (let [my-p-moves (add-beliefs-to-pmoves s board)
         op-p-moves (add-beliefs-to-pmoves (opponent s) board)
         merged-belief-moves (map merge-beliefs my-p-moves op-p-moves)]
    merged-belief-moves))


(defn choose-move [moves-with-beliefs]
  (let [ranked-moves
         (sort-by :rank
           (map #(assoc %1 :rank (belief-action-preferences (%1 :belief))) moves-with-beliefs))
         top-move (first ranked-moves)]
    (cond
      (= 1 (:rank top-move)) top-move
      (= 2 (:rank top-move)) top-move
      (= 3 (:rank top-move)) (first (shuffle ranked-moves))
      :else nil)))

(defn game-move [s board]
  (dissoc
    (choose-move
     (calculate-beliefs s board)) :rank))

