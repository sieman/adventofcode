(ns adventofcode.2020.d3-trees
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

;; https://www.youtube.com/watch?v=wXVDfzxeZ-o
;; copy right lambda island

(def input-real (line-seq (io/reader (io/resource "d3_input.txt"))))
(def input-sample (line-seq (io/reader (io/resource "d3_sample.txt"))))

(def my-map (mapv (fn [row]
                    (mapv {\# true \. false} row)) input-real))


(defn tree? [m x y]
  (let [width (count (first m))]
    (get-in m [y (mod x width)])))

(tree? my-map 0 0)
(tree? my-map 2 0)
(tree? my-map 13 0)
(mod 13 11)


(defn input->map [input]
  (mapv (fn [row]
          (mapv {\# true \. false} row)) input))

(def pos [my-map 0 0 0])

(defn sled [[down-x down-y] [my-map x y trees]]
  (let [x (+ x down-x)
        y (+ y down-y)
        tree? (tree? my-map x y)
        ]
    (cond
      (nil? tree?) (reduced trees)

      (true? tree?) [my-map x y (inc trees)]

      :else [my-map x y trees])))

(-> pos
    sled
    sled
    sled
    sled
    sled
    sled
    sled
    sled
    sled
    sled
    (->> (drop 1))
    )


(defn sled-down [slope input]
  @(first
    (drop-while
     (complement reduced?)
     (iterate (partial sled slope) [(input->map input) 0 0 0]))))

(last (input->map input-sample))

(sled-down [3 1] input-sample)
(sled-down [3 1] input-real) ;; => 207

(def slopes
  [[1 1]
   [3 1]
   [5 1]
   [7 1]
   [1 2]])

(apply * (for [s slopes]
           (sled-down s input-real)))
;; => 336 input-sample
;; => 2655892800 input-real
