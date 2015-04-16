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

(def test-board (parse-board test-board-string))

;; (expect (vec '(nil 4 nil 6 2 nil 7 nil nil)) (board-row test-board 1))

;; (expect (vec '(6 4 nil 2 nil nil 9 nil nil)) (board-col test-board 1))

;; (expect #{4 6} (board-sector test-board 1 0))

;; (expect false (cell-empty? test-board 0 1))

;; (expect true (cell-empty? test-board 1 0))

;; (expect '([0 0] [0 2] [0 3] [0 5] [0 6] [0 7] [1 0] [1 2] [1 5] [1 7] [1 8] [2 0] [2 1] [2 2] [2 3] [2 5] [2 6] [2 7] [2 8] [3 0] [3 2] [3 3] [3 4] [3 5] [3 7] [3 8] [4 0] [4 1] [4 3] [4 4] [4 5] [4 7] [4 8] [5 0] [5 1] [5 3] [5 5] [5 6] [5 8] [6 0] [6 2] [6 4] [6 6] [6 7] [6 8] [7 0] [7 1] [7 3] [7 6] [7 8] [8 1] [8 2] [8 3] [8 5] [8 6] [8 8]) (get-empties test-board))

;; (expect #{4 6} (board-sector test-board 2 2))

;; (expect #{2 5 7 8 9} (get-possible test-board 2 2))

;; (expect #{5 6} (get-possible test-board 6 4))

;; (expect true (cell-empty? test-board 3 3))

;; (expect false (cell-empty? (board-assoc test-board 3 3 5) 3 3))

;; ;(expect ".6..1...2\n.4.62.7..\n....4....\n.2..4.9..\n..1...2..\n.73.8..5.\n.9.8.4...\n..4.32.9.\n2...7..4." (print-board (trim-board test-board)))

;; (expect false (cell-empty? (board-assoc test-board 3 3 5) 3 3))

;; (expect [[3 3] [3 4] [3 5] [4 3] [4 4] [4 5] [5 3] [5 5]] (board-sector-empties test-board 3 3))

;; (expect [[6 4] [7 3] [8 3] [8 5]] (board-sector-empties test-board 8 5))

;; (expect (disj (set (range 1 10)) 8) (board-need-to-place-sector test-board 3 3))

;; (expect #{1 5 6 9} (board-need-to-place-sector test-board 8 5))

;; (expect (board-assoc test-board 5 1 7) (trim-board test-board))

(def test-board-simple (make-board (str/join '(
     "1.. 570 .38"
     "7.. ..8 4.."
     ".83 .1. 5.."

     ".1. 8.3 ..2"
     "8.7 ... 6.4"
     "5.. 7.6 .8."

     "..8 .9. 72."
     "..5 3.. ..1"
     "97. .85 ..6"))))

test-board-simple

(expect (* 9 9) (count (b-assoc test-board-simple [8 7] 4)))
;(expect true (not (nil? (brute-solve test-board-simple))))

;; (expect [
;;  [1 9 6 5 7 0 2 3 8]
;;  [7 5 2 6 3 8 4 1 9]
;;  [4 8 3 9 1 2 5 6 7]
;;  [6 1 4 8 5 3 9 7 2]
;;  [8 3 7 1 2 9 6 5 4]
;;  [5 2 9 7 4 6 1 8 3]
;;  [3 6 8 4 9 1 7 2 5]
;;  [2 4 5 3 6 7 8 9 1]
;;  [9 7 1 2 8 5 3 4 6]] (solve-board test-board-simple))



; (expect nil (prune-sector test-board-full 5))

(def test-board-hard (make-board (str/join '(
     "1.. ..7 .9."
     ".3. .2. ..8"
     "..9 6.. 5.."

     "..5 3.. 9.."
     ".1. .8. ..2"
     "6.. ..4 ..."

     "3.. ... .1."
     ".4. ... ..7"
     "..7 ... 3.."))))

(expect [[1 nil nil nil nil 7 nil 9 nil]
         [nil 3 nil nil 2 nil nil nil 8]
         [nil nil 9 6 nil nil 5 nil nil]
         [nil nil 5 3 nil nil 9 nil nil]
         [nil 1 nil nil 8 nil nil nil 2]
         [6 nil nil nil nil 4 nil nil nil]
         [3 nil nil nil nil nil nil 1 nil]
         [nil 4 1 nil nil nil nil nil 7]
         [nil nil 7 nil nil nil 3 nil nil]] (solve test-board-hard))
