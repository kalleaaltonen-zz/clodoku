(ns clodoku.core-test
  (:require [expectations :refer :all]
            [clodoku.core :refer :all]
            [clojure.string :as str]))

(def test-board-string
  (str/join '(
   ".6. .1. ..2"
   ".4. 62. 7.."
   "... .4. ..."

   ".2. ... 9.."
   "..1 ... 2.."
   "..3 .8. .5."

   ".9. 8.4 ..."
   "..4 .32 .9."
   "2.. .7. .4.")))

(def test-board (make-board test-board-string))

(expect #{[0 0] [0 1] [0 2] [0 3] [0 4] [0 5] [0 6] [0 7] [0 8]
          [1 0] [1 1] [1 2]
          [2 0] [2 1] [2 2]
          [3 0] [4 0] [5 0]
          [6 0] [7 0] [8 0]} (set (get-affected-cells test-board 0 0)))

(expect 6 (test-board [0 1]))

(expect #{3 5 7 8 9} (get-possible test-board 0 0))

(expect #{3 5 7 8 9} (test-board [0 0]))

(def test-board-hard2 (make-board (str/join '(
     "... ... 68."
     "... .73 ..9"
     "3.9 ... .45"
     "49. ... ..."
     "8.3 .5. 9.2"
     "... ... .36"
     "96. ... 3.8"
     "7.. 68. ..."
     ".28 ... ..."))))

(expect (str/join "\n" '(
"172549683"
"645873219"
"389261745"
"496327851"
"813456972"
"257198436"
"964715328"
"731682594"
"528934167"
)), (pb (solve test-board-hard2)))

