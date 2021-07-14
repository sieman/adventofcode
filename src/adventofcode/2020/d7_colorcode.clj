(ns adventofcode.2020.d7-colorcode
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.set :as cset :refer [difference]]))

(defn read-input [file] ; (slurp (io/resource (str "2020/" file)))
  (line-seq (io/reader (io/resource (str "2020/" file)))))

(def real-input (read-input "d7_input.txt"))

(def sample-input (read-input "d7_sample.txt"))


(defn parse-entry [colm s-input]
  (let   [[bag & a-deps] (str/split s-input #"\s?bags\scontain\s|\s?bag\w?[,.]")
          deps (keep (comp next (partial re-find #"(\d+)\s(\w+\s+\w+)")) a-deps)
          deps-m (reduce (fn [m bag] (assoc m (nth bag 1) (Integer/parseInt (nth bag 0))) ) {} deps)
          ]
    (assoc colm bag deps-m)
    ))


(def entries (reduce parse-entry {} sample-input))

(-> entries
    (get  "shiny gold")
    (get  "vibrant plum")
    )

entries


(some (fn [[color num]]
        (= color "shiny gold")) {"shiny gold" 2, "faded blue" 9})
({"shiny gold" 2, "faded blue" 9} "shiny gold")
(contains? {"shiny gold" 2, "faded blue" 9} "shiny gold")

(defn sub-contains? [container own-color]
  (->> container
       entries
       (some (fn [[color num]]
               (or (= color own-color)
                   (sub-contains? color own-color)))
             )))

(->> entries
     (filter
      (fn [[color container]]
        (contains? container "shiny gold") ))
     (keys)
     )


(get entries "shiny gold") {"mirrored blue" 2, "muted brown" 1, "dim purple" 3}


(defn contains-deps-color? [deps color]
  (contains? deps color))

(contains-deps-color? '{"shiny gold" "2", "faded blue" "9"} "shiny gold")



(difference (set (keys entries)) #{"shiny gold"})

(defn find-valid-iter [keys result color]
  )


(defn find-valid [start]
  (let [miak (difference (set (keys entries)) (set start))
        result (vector start)]
    (for [key miak
          :when (contains-deps-color? start)]
      (conj result key)
      )
    result
    ))

(find-valid "shiny gold")


;; ---------------------------------------

(defn color-graph [entries]
  (reduce (fn [m [bag deps]]
            (reduce (fn [m [num col]]
                      (update m col conj bag))
                    m deps))
          {}
          entries))

(defn add-valid [result graph color]
  (into result  (get graph color)))

(get entries "bright white") ;{"shiny gold" "1"}
(keys {"shiny gold" "1"} )
(add-valid #{} entries  "bright white" )






(defn valid-outermost [graph start]
(loop [result (add-valid #{} graph start)]
  (let [result2 (reduce (fn [res color]
                          (add-valid res graph color))
                        result result)]
    (if (= result result2)
      result
      (recur result2)))))

(valid-outermost entries "shiny gold")

(count (valid-outermost (color-graph entries) "shiny gold"))
