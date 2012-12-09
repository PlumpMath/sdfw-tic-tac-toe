(defproject sdfw-tic-tac-toe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [domina "1.0.0"]]
  :plugins [[lein-cljsbuild "0.2.9"]]
  :source-path "src-clj"
  :cljsbuild {
	:builds [{
		:source-path "src-cljs"
		:compiler {
			:output-to "resources/public/tictactoe.js"
			:optimization :whitespace
			:pretty-print true
			}
                   }]
}
)
