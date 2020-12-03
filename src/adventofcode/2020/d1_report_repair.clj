(ns adventofcode.2020.d1-report-repair
  (:require [clojure.java.io :as io]))

;; this solution is copy right of Lambda Island
;; https://www.youtube.com/watch?v=9ITiZ88sljA

(def input-sample [1721 979 366 299 675 1456])

(def input (map #(Long/parseLong %)
     (line-seq (io/reader (io/resource "d1_input.txt")))))

(set
 (for [x input
       y input
       :when (= 2020 (+ x y))] (* x y)))
;; => #{514579}
;; => #{997899}

(set
 (for [x input
       y input
       z input
       :when (= 2020 (+ x y z))]
   (* x y z)))

;; => #{241861950}
;; => #{131248694}

;; (defn is2020? [n] (= n 2020))

;; (defn adder [a b]
;; (if (and (number? a) (number? b))
;; (+ a b)
;; 0))

;; (defn adder3 [a b c]
;;   (if (and (number? a) (number? b) (number? c))
;;     (+ a b c)
;;     0))

;; (defn er-find-2020 [report]
;;   (reduce
;;    (fn [a b]
;;      (println a " " b " " (adder a b))
;;      (if (is2020? (adder a b))
;;        (reduced (vector a b))
;;        a )) report))

;; (defn er-rec [report]
;;   (let [check (er-find-2020 report)]
;;     (if (= (first report) check)
;;       (er-rec (rest report))
;;       check)))

;; (defn er3-rec [report]
;;   )

;; (defn expense-report [report]
;;   (reduce * (er-rec report))
;;   )
