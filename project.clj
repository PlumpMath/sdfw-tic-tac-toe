(defproject sdfw-tic-tac-toe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [domina "1.0.0"]]
  :plugins [[lein-cljsbuild "0.2.9"]]
  :source-paths ["src-clj"]
  :test-paths ["test-clj"]
  :cljsbuild {
    :builds [{
        ; The path to the top-level ClojureScript source directory:
        :source-path "src-cljs"
        ; The standard ClojureScript compiler options:
        ; (See the ClojureScript compiler documentation for details.)
        :compiler {
          :output-to "resources/public/tictactoe.js"  ; default: main.js in current directory
          :optimizations :whitespace
                    :pretty-print true}}]})

