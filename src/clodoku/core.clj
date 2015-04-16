(ns clodoku.core
  (:require [clojure.string :as str]
            [clojure.set :as set]))

;; test data structures

(def example-board-string
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

(def all-coords (for [x (range 9) y (range 9)] [x y]))

(defn pb [b]
  (str/join
   (for [x (range 9) y (range 10)]
     (let [v (b [x y])]
       (if (= y 9)
         "\n"
         (if (number? v)
           (str v)
           "."))))))


(defn parse-board [b]
  (vec (for [row (partition 9 (re-seq #"[\d\.]" b))]
         (vec (map
               #(if (= %1 '.) nil %1)
               (map #(read-string %1) row))))))

(defn get-affected-cells[b x y]
  (filter (fn [coord]
            (or (= (coord 0) x) ; On the same row
                (= (coord 1) y) ; On the same col
                (and (= (mod (coord 0) 3) (mod x 3)) ; same sector
                     (= (mod (coord 1) 3) (mod y 3)))))
          (keys b)))


(defn get-possible [b x y]
  (if (nil? (b [x y]))
    (set/difference
     (set (range 1 10))
     (set
      (filter number?
              (get-in b (get-affected-cells b x y)))))
      nil))

(defn make-board [s]
  (let [b (parse-board s)
        m (zipmap all-coords (map (fn [[x y]] (get (get b x) y)) all-coords))
        free-cells (filter #(nil? (m %)) (keys m))]
    (apply assoc m (apply concat (map list free-cells
                                      (map (fn [[x y]] (get-possible m x y))
                                           free-cells)))))) ; Replace nils with sets of legal values for that cell

(defn board-legal? [b]
  (not-any? #(and (set? % ) (empty? %)) (vals b)))

(defn board-solved? [b]
  (not-any? set? (vals b)))

(defn get-move[board]
  (first
   (sort-by (fn [[x y]] (if (set? (board [x y])) (count (board [x y])) 10))
            (keys board))))

(defn b-assoc [b [x y] v]
  (do
    (assert (contains? (b [x y]) v))
    (let [new-board (assoc b [x y] v)]
      (reduce (fn [altered-map [coord values]]
                (do
                  ; (println altered-map coord values)
                  (if (and (set? values)
                           (contains? (set (get-affected-cells b x y)) coord))
                    (assoc altered-map coord (disj values v))
                    (assoc altered-map coord values))))
              {}
              new-board))))


(defn brute-solve[board]
  (if (not (board-legal? board))
    nil
    (if (board-solved? board)
      board
      ; pick cell
      (let [move (get-move board)
            values (sort (board move))]
        (defn try-solve[b move [v & values]]
          (if (nil? v)
            nil
            (let [new-board (b-assoc b move v)
                  solution (brute-solve new-board)]
              (if (nil? solution)
                (try-solve b move values)
                solution))))
        (try-solve board move values)))))

(defn solve [board]
  (do
    (println "============== INPUT =====================")
    (print   (pb board))
    (println "============= /INPUT =====================")
    (println )
    (println "============== SOLUTION ==================")
    (print   (pb (brute-solve board)))
    (println "============= /SOLUTION ==================")
    board))
