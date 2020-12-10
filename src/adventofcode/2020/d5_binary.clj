(ns adventofcode.2020.d5-binary
  (:require [clojure.java.io :as io]))

(def code-map {\B 1 \F 0 \L 0 \R 1 })

(str "2r" )  ; "2r1000110"
(Integer/parseInt (apply str (map code-map (vec (char-array (take 7 "BFFFBBFRRR"))))) 2)
(Integer/p)
(partition-all 7 "BFFFBBFRRR") ; ((\B \F \F \F \B \B \F) (\R \R \R))
(vec (char-array (take 7 "BFFFBBFRRR")))


(defn to-num [code]
  (Long/parseLong (apply str (map code-map code)) 2))

(defn decode-row [code]
  (to-num (first (partition-all 7 code))))

(defn decode-col [code]
  (to-num (last (partition-all 7 code))))

(defn decode-id [code]
  (let [[row col] (partition-all 7 code)
        rno (to-num row)
        cno (to-num col) ]
    (+ cno (* rno 8))))

(decode-row "BFFFBBFRRR")
(decode-col "BFFFBBFRRR")
(decode-id "BFFFBBFRRR")

(defn decode [code]
  (let [[row col] (partition-all 7 code)
        rno (to-num row)
        cno (to-num col) ]
    (str code " row " rno " seat " cno " seat ID " (+ cno (* rno 8)))))
;; BFFFBBFRRR: row 70, column 7, seat ID 567.
(decode "BFFFBBFRRR") ; "BFFFBBFRRR row 70 seat 7 seat ID 567"
;; FFFBBBFRRR: row 14, column 7, seat ID 119.
(decode "FFFBBBFRRR") ; "FFFBBBFRRR row 14 seat 7 seat ID 119"
;; BBFFBBFRLL: row 102, column 4, seat ID 820.
(decode "BBFFBBFRLL") ; "BBFFBBFRLL row 102 seat 4 seat ID 820"

(def real-input (line-seq (io/reader (io/resource "d5_input.txt"))))

(-> decode-id
    (map real-input)
    sort
    last) ; 894

(->> real-input
     (map decode-id)
     (apply max)) ; => 894


(def seat-ids (map decode-id real-input))


(some (fn [[l h ]]
        (when (= h (+ 2 l))
          (inc l)))
      (partition-all 2 1 (sort seat-ids))) ; => 579
