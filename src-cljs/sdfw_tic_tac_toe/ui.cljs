(ns sdfw-tic-tac-toe.ui
  (:require 
    [domina :as d]
    [clojure.browser.event :as event]))

(def search-button (d/by-id "search-btn"))

; Event handler for the button
(event/listen search-button "click"
 (fn [] (js/alert (d/value (d/by-id "lname")))))