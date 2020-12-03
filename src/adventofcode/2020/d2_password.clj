(ns adventofcode.2020.d2-password
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

;; this solution is copy right of Lambda Island
;; https://www.youtube.com/watch?v=rghUu4z5MdM

(def input-sample "1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc")

(defn parse-long [l]
  (Long/parseLong l))


(defn parse-line [s]
  (let [[_ min max char pwd] (re-find #"(\d+)-(\d+) (.): (.*)" s)] [(parse-long min) (parse-long max) (first char) pwd]))

(def entry
(-> input-sample
    (str/split #"\n")
    (nth 2)
    parse-line))

(entry-role-ok? entry)

(def input (map parse-line
     (line-seq (io/reader (io/resource "d2_input.txt")))))


(defn entry-ok? [[min max char pwd]]
  (<= min (get (frequencies pwd) char 0) max))


(defn entry-role-ok? [[min max char pwd]]
  (let [isA (= char (get pwd (dec min)))
        isB (= char (get pwd (dec max)))]
    (cond
      (and isA (not isB)) true
      (and (not isA) isB) true
      :else false
      )
    ))


(count (filter entry-ok? (take 10 input)))

(count (filter entry-ok? input)) ;; => 519
(count (filter entry-role-ok? input));; => 708
