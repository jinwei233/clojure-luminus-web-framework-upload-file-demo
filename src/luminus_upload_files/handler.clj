(ns luminus-upload-files.handler
  (:use compojure.core)
  (:require [luminus-upload-files.layout :as layout]
            [noir.io :as io]
            [noir.response :as response]
            [noir.util.middleware :refer [app-handler]]
            [ring.util.response :refer [file-response]]))

;; uploaded file dir
(def resource-path "/tmp/")

(defroutes home-routes
  (GET "/" []
       (layout/render "index.html"))
  (GET "/upload" []
       (layout/render "upload.html"))

  (POST "/upload" [file]
        ;; file with same name will be overwrited, so in production mode , gen a
        ;; random string as filename
       (io/upload-file resource-path file)
       (response/redirect
         (str "/files/" (:filename file))))

  (GET "/files/:filename" [filename]
       (file-response (str resource-path filename))))  

(def app (app-handler
          ;; add your application routes here
          [home-routes]))
