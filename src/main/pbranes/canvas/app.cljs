(ns pbranes.canvas.app
  (:require [reagent.dom :as rdom]))

(defonce ctx
  (.getContext (.getElementById js/document "canvas") "2d"))

(defn app []
  [:p "Hello World 6"])

(defn clear-canvas []
  (.clearRect ctx 0 0 600 600))

(defn simple-example []
  (clear-canvas)
  (set! (.-fillStyle ctx) "rgb(200, 0, 0)")
  (.fillRect ctx "10" "10" "50" "50")
  (set! (.-fillStyle ctx) "rgba(0, 0, 200, 0.5)")
  (.fillRect ctx "30" "30" "50" "50"))

(defn rectangle-shape-example []
  (clear-canvas)
  (.fillRect ctx "25" "25" "100" "100")
  (.clearRect ctx "45" "45" "60" "60")
  (.strokeRect ctx "50" "50" "50" "50"))

(defn triangle-path-example []
  (clear-canvas)
  (.beginPath ctx)
  (.moveTo ctx "75" "50")
  (.lineTo ctx "100" "75")
  (.lineTo ctx "100" "25")
  (.fill ctx))

(defn smiley-example []
  (clear-canvas)
  (.beginPath ctx)
  (.arc ctx 75 75 50 0 (* 2 Math/PI) true) ;; outer circle
  (.moveTo ctx 110 75)
  (.arc ctx 75 75 35 0 Math/PI false) ;; mouth
  (.moveTo ctx 65 65)
  (.arc ctx 60 65 5 0 (* 2 Math/PI) true) ;; left eye
  (.moveTo ctx 95 65)
  (.arc ctx 90 65 5 0 (* 2 Math/PI) true) ;; right eye
  (.stroke ctx))

(defn two-triangles-example []
  (clear-canvas)
    ;; Fill triangle
  (.beginPath ctx)
  (.moveTo ctx 25 25)
  (.lineTo ctx 105 25)
  (.lineTo ctx 25 105)
  (.fill ctx)
    ;; Stroked triangle
  (.beginPath ctx)
  (.moveTo ctx 125 125)
  (.lineTo ctx 125 45)
  (.lineTo ctx 45 125)
  (.closePath ctx)
  (.stroke ctx))

(defn arc-example []
  (clear-canvas)
  (for [i (range 4)]
    (for [j (range 3)]
      (do
        (.beginPath ctx)
        (let [x (-> j (* 50) (+ 25))
              y (-> i (* 50) (+ 25))
              radius 20
              start-angle 0
              end-angle (-> j (* Math/PI) (/ 2) (+ Math/PI))
              counterclockwise (not (= 0 (mod i 2)))]
          (.arc ctx x y radius start-angle end-angle counterclockwise)
          (if (> i 1)
            (.fill ctx)
            (.stroke ctx)))))))

(defn quadradic-bezier-example []
  (clear-canvas)
  (.beginPath ctx)
  (.moveTo ctx 75 25)
  (.quadraticCurveTo ctx 25 25 25 62.5)
  (.quadraticCurveTo ctx 25 100 50 100)
  (.quadraticCurveTo ctx 50 120 30 125)
  (.quadraticCurveTo ctx 60 120 65 100)
  (.quadraticCurveTo ctx 125 100 125 62.5)
  (.quadraticCurveTo ctx 125 25 75 25)
  (.stroke ctx))

(defn cubic-bezier-example []
  (clear-canvas)
  (.beginPath ctx)
  (.moveTo ctx 75 40)
  (.bezierCurveTo ctx 75 37 70 25 50 25)
  (.bezierCurveTo ctx 20 25 20 62.5 20 62.5)
  (.bezierCurveTo ctx 20 80 40 102 75 120)
  (.bezierCurveTo ctx 110 102 130 80 130 62.5)
  (.bezierCurveTo ctx 130 62.5 130 25 100 25)
  (.bezierCurveTo ctx 85 25 75 37 75 40)
  (.fill ctx))

(defn path-2d-example []
  (clear-canvas)
  (let [rectangle  (js/Path2D.)
        circle (js/Path2D.)]
    (.rect rectangle 10 10 50 50)
    (.arc circle 100 35 25 0 (* 2 Math/PI))
    (.stroke ctx rectangle)
    (.fill ctx circle)))

(defn fill-style-example []
  (clear-canvas)
  (for [i (range 6)]
    (for [j (range 6)]
      (let [r (Math/floor (+ 255 (* i -42.5)))
            g (Math/floor (+ 255 (* j -42.5)))
            b 0]
        (set! (.-fillStyle ctx) (str "rgb(" r "," g "," b ")"))
        (.fillRect ctx (* j 25) (* i 25) 25 25)))))

(defn stroke-style-example []
  (clear-canvas)
  (for [i (range 6)]
    (for [j (range 6)]
      (let [r 0
            g (Math/floor (+ 255 (* i -42.5)))
            b (Math/floor (+ 255 (* j -42.5)))]
        (set! (.-strokeStyle ctx) (str "rgb(" r "," g "," b ")"))
        (.beginPath ctx)
        (.arc ctx (+ 12.5 (* j 25)) (+ 12.5 (* i 25)) 10 0 (* Math/PI 2) true)
        (.stroke ctx)))))

(defn ^:dev/after-load start []
  (println "start")
  (rdom/render [app] (.getElementById js/document "app")))

(defn ^:export init []
  (start))

(defn ^:dev/before-load stop []
  (js/console.log "stop"))

(comment
  (simple-example)
  (rectangle-shape-example)
  (triangle-path-example)
  (smiley-example)
  (two-triangles-example)
  (arc-example)
  (quadradic-bezier-example)
  (cubic-bezier-example)
  (path-2d-example)
  (fill-style-example)
  (stroke-style-example)
  )