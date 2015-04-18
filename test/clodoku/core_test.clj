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

(expect true (solve test-board))

;; (def test-board-simple (make-board (str/join '(
;;      "1.. 57. .38"
;;      "7.. ..8 4.."
;;      ".83 .1. 5.."

;;      ".1. 8.3 ..2"
;;      "8.7 ... 6.4"
;;      "5.. 7.6 .8."

;;      "..8 .9. 72."
;;      "..5 3.. ..1"
;;      "97. .85 ..6"))))

;; test-board-simple

;; (expect (* 9 9) (count (b-assoc test-board-simple [8 7] 4)))
;; ;(expect true (not (nil? (brute-solve test-board-simple))))

;; ;; (expect [
;; ;;  [1 9 6 5 7 0 2 3 8]
;; ;;  [7 5 2 6 3 8 4 1 9]
;; ;;  [4 8 3 9 1 2 5 6 7]
;; ;;  [6 1 4 8 5 3 9 7 2]
;; ;;  [8 3 7 1 2 9 6 5 4]
;; ;;  [5 2 9 7 4 6 1 8 3]
;; ;;  [3 6 8 4 9 1 7 2 5]
;; ;;  [2 4 5 3 6 7 8 9 1]
;; ;;  [9 7 1 2 8 5 3 4 6]] (solve-board test-board-simple))



;; ; (expect nil (prune-sector test-board-full 5))

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

(expect nil (solve test-board-hard))
