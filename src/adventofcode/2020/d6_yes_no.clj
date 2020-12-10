(ns adventofcode.2020.d6-yes-no
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.set :as set]))

(defn read-input [input]
  (slurp (io/resource input)))
(def real-input (read-input "d6_input.txt"))
(def sample-input (read-input "d6_sample.txt"))

(-> sample-input
    (str/split #"\R\R")

    (map frequencies))


(comment
  map (fn [l] (str/split l #"\R")) (str/split sample-input #"\R\R"))

(defn parse-input [input]
  (map (fn [l] (str/split l #"\R")) (str/split input #"\R\R")))

(parse-input sample-input) ; => (["abc"] ["a" "b" "c"] ["ab" "ac"] ["a" "a" "a" "a"] ["b"])


(map (fn [item] (set item ) )(quote (["abc"] ["a" "b" "c"] ["ab" "ac"] ["a" "a" "a" "a"] ["b"]))) (#{"abc"} #{"a" "b" "c"} #{"ab" "ac"} #{"a"} #{"b"})



(->> (parse-input real-input)
     (map (fn [item] (set (reduce str item) ) ))

     (map (fn [item] (count item)))
     (apply +))
;; sample => 11
;; real => 6504


(apply + '(1 3 2 1 1))
(set ["a" "a" "a" "a"])

(str ["a" "a" "a" "a"])
(reduce str ["a" "b" "c"])
(set (reduce str ["a" "a" "a" "a"]))
(set "abc")


 (first (parse-input sample-input))

(map (fn [i] (/
             (count (set (reduce str i)))
             (count i))) '(["abc"] ["a" "b" "c"] ["ab" "ac"] ["a" "a" "a" "a"] ["b"]) )



(defn correct-answers [group] (count (apply set/intersection (map set group))))

(apply + (map ca (parse-input sample-input))); => 6
(apply + (map ca (parse-input real-input))); => 3351
