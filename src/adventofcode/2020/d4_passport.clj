(ns adventofcode.2020.d4-passport
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn read-input [input]
  ;; (line-seq (io/reader (io/resource input)))
  (slurp (io/resource input))
  )

(def real-input (read-input "d4_input.txt"))
(def sample-input (read-input "d4_sample.txt"))

(->
 (str/split sample-input #"\R\R")
 ;; => ["ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm" "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\nhcl:#cfa07d byr:1929" "hcl:#ae17e1 iyr:2013\neyr:2024\necl:brn pid:760753108 byr:1931\nhgt:179cm" "hcl:#cfa07d eyr:2025 pid:166559648\niyr:2011 ecl:brn hgt:59in"]
 first
 (->> (re-seq #"(\w{3}):(\S+)")))


;; => (
;;  ["ecl:gry" "ecl" "gry"]
;;  ["pid:860033327" "pid" "860033327"]
;;  ["eyr:2020" "eyr" "2020"]
;;  ["hcl:#fffffd" "hcl" "#fffffd"]
;;  ["byr:1937" "byr" "1937"]
;;  ["iyr:2017" "iyr" "2017"]
;;  ["cid:147" "cid" "147"]
;;  ["hgt:183cm" "hgt" "183cm"]
;;  )

(defn parse-entry [entry]
  (into {} (map (comp vec next)) (re-seq #"(\w{3}):(\S+)" entry)))
;; Test (parse-entry "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm")

(vector (vec (next ["ecl:gry" "ecl" "gry"]))) ; ["ecl" "gry"]
(into {} [["ecl" "gry"]])
(into {} (vec (next ["ecl:gry" "ecl" "gry"])))
(into {}  (map (comp vec next ["ecl:gry" "ecl" "gry"]) ))
(map (comp vec next ["ecl:gry" "ecl" "gry"]))

(defn valid? [m]
  (= (count (dissoc m "cid" )) 7 ))

(->>
 (str/split real-input #"\R\R")
 (map parse-entry)
 (filter valid?)
 count
 )
;; => 245


(map parse-entry sample-input)


(parse-entry sample-input)


(def fields
  ["byr" ;(Birth Year)
   "iyr" ;(Issue Year)
   "eyr" ;(Expiration Year)
   "hgt" ;(Height)
   "hcl" ;(Hair Color)
   "ecl" ;(Eye Color)
   "pid" ;(Passport ID)
   "cid" ;(Country ID)
   ])

(count fields) 8

(defn parse-long [l]
  (Long/parseLong l))


(defn valid2? [{:strs [byr iyr eyr hgt hcl ecl pid cid]}]
  (and
   ;; byr (Birth Year) - four digits; at least 1920 and at most 2002.
   byr (<= 1920 (parse-long byr) 2002)
   ;; iyr (Issue Year) - four digits; at least 2010 and at most 2020.
   iyr (<= 2010 (parse-long iyr) 2020)
;; eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
   eyr (<= 2020 (parse-long eyr) 2030)
;; hgt (Height) - a number followed by either cm or in:
;;     If cm, the number must be at least 150 and at most 193.
;;     If in, the number must be at least 59 and at most 76.
   hgt (let [[_ num unit] (re-find #"(\d+)(cm|in)" hgt)]
         (case unit
         "cm" (<= 150 (parse-long num) 193)
         "in" (<= 59 (parse-long num) 76)
          false))
;; hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
   hcl (re-matches #"#[0-9a-f]{6}" hcl)
;; ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
   ecl (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl)

;; pid (Passport ID) - a nine-digit number, including leading zeroes.
   pid (re-matches #"[0-9]{9}" pid)
;; cid (Country ID) - ignored, missing or not.
  ))

(valid2? {"byr" "1920" "iyr" "2015" "eyr" "2025" "hgt" "176cm" "hcl" "#123456"})
(re-matches #"#[0-9a-f]{6}" "#123a56")

(= 6 (count (rest (re-find #"#[0-9a-f]+" "#12345"))))

(re-find #"(\d+)(cm|in)" "200in")

(#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} "afd")

(->>
 (str/split real-input #"\R\R")
 (map parse-entry)
 (filter valid2?)
 count
 )
;; => 133
