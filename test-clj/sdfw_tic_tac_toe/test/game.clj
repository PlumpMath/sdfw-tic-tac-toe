(ns sdfw-tic-tac-toe.test.game
  (:use [clojure.test])
  (:use [sdfw-tic-tac-toe.game]))


(deftest winning-across-test
  (is (win-across :x [[:x :x :x] [nil nil nil] [nil nil nil]]))
  (is (not (win-across :x [[:x :o :x] [nil nil nil] [nil nil nil]])))
  (is (win-across :x [[nil nil nil] [:x :x :x] [nil nil nil]]))
  (is (not (win-across :x [[nil nil nil] [:o :x :x] [nil nil nil]])))
  (is (win-across :x [[nil nil nil] [nil nil nil] [:x :x :x]]))
  (is (not (win-across :x [[nil nil nil] [nil nil nil] [:o :x :x]]))))

(deftest winning-down-test
  (is (win-down :x [[:x nil nil] [:x nil nil] [:x nil nil]]))
  (is (not (win-down :x [[:x nil nil] [:o nil nil] [:x nil nil]])))
  (is (win-down :x [[nil :x nil] [nil :x nil] [nil :x nil]]))
  (is (not (win-down :x [[nil :x nil] [nil :o nil] [nil :x nil]])))
  (is (win-down :x [[nil nil :x] [nil nil :x] [nil nil :x]]))
  (is (not (win-down :x [[nil nil :x] [nil nil :x] [nil nil :o]]))))

(deftest winning-diagonally-test
  (is (win-diag :x [[:x nil nil] [nil :x nil] [nil nil :x]]))
  (is (not (win-diag :x [[:x nil nil] [nil :o nil] [nil nil :x]])))
  (is (win-diag :x [[nil nil :x] [nil :x nil] [:x nil nil]]))
  (is (not (win-diag :x [[nil nil :x] [nil :o nil] [:x nil nil]]))))

(deftest winning
  (is (win :x [[:x :x :x] [nil nil nil] [nil nil nil]]))
  (is (win :o [[:o :o :o] [nil nil nil] [nil nil nil]]))
  (is (not (win :x [[:o :o :o] [nil nil nil] [nil nil nil]]))))

(deftest moves
  (is (= (possible-moves :x [[:x nil nil] [nil :x :o] [nil :o :x]])
       [
          [[:x :x nil] [nil :x :o] [nil :o :x]]
          [[:x nil :x] [nil :x :o] [nil :o :x]]
          [[:x nil nil] [:x :x :o] [nil :o :x]]
          [[:x nil nil] [nil :x :o] [:x :o :x]]
         ])))

(deftest opponent-symbols
  (is (= :o (opponent :x)))
  (is (= :x (opponent :o))))

(deftest beliefs-about-moves
  (let [  x-wins      [[:x :x :x] [nil nil nil] [nil nil nil]]
          o-wins      [[nil nil :o] [nil :x :o] [:x nil :o]]
          noone-wins  [[:x nil nil] [nil nil nil] [nil nil nil]]]
    (is (= (beliefs :win) (belief-about-move :x x-wins)))
    (is (= (beliefs :lose) (belief-about-move :x o-wins)))
    (is (= (beliefs :noone) (belief-about-move :x noone-wins))))
  )

(deftest choosing-moves
   (let [ moves [{:belief (beliefs :lose) :move :a} {:belief (beliefs :win) :move :b}]]
     (is (= {:rank 1, :belief "I am going to win.", :move :b}
           (choose-move moves))))
   (let [ moves [{:belief (beliefs :lose) :move :a} {:belief (beliefs :noone) :move :b}]]
     (is (=  {:rank 2, :belief "My opponent is going to win.", :move :a} (choose-move moves))))
   (let [ moves [{:belief (beliefs :noone) :move :a} {:belief (beliefs :noone) :move :b}]]
     (is (some #{:a :b} (vals (choose-move moves)))))
   (let [ moves []]
     (is (= nil (choose-move moves)))))

(deftest playing-game
  (let [board [[:x :x nil] [:o nil nil] [nil nil nil]]
         result {:move [[:x :x :x] [:o nil nil] [nil nil nil]], :belief "I am going to win."}]
    (is (= result (game-move :x board))))

  (let [board [[:x nil nil] [:o :o nil] [nil nil nil]]
         result {:move  [[:x nil nil] [:o :o :x] [nil nil nil]], :belief "My opponent is going to win."}]
     (is (= result (game-move :x board))))
  (let [board [[:o :o nil] [:x nil nil] [nil nil nil]]
         result {:move [[:o :o :o] [:x nil nil] [nil nil nil]], :belief "I am going to win."}]
    (is (= result (game-move :o board))))

  (let [board [[:o nil nil] [:x :x nil] [nil nil nil]]
         result {:move  [[:o nil nil] [:x :x :o] [nil nil nil]], :belief "My opponent is going to win."}]
    (is (= result (game-move :o board))))

  )

;(run-tests 'sdfw-tic-tac-toe.test)

