(ns basic-web-app.handler
  (:require
   [basic-web-app.views :as views]
   [compojure.core :refer [defroutes GET POST]]
   [compojure.route :as route]
   [ring.adapter.jetty :as jetty]
   [aleph.http :as aleph]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]))

(defroutes app-routes
  (GET "/" [] (views/home-page))
  (GET "/add-location"
    []
    (views/add-location-page))
  (POST "/add-location"
    {params :params}
    (views/add-location-results-page params))
  (GET "/location/:loc-id"
    [loc-id]
    (views/location-page loc-id))
  (GET "/all-locations"
    []
    (views/all-locations-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  ;; use #' prefix for REPL-friendly code -- see note below
  (-> #'app-routes
      (wrap-defaults site-defaults)))

(defn -main []
  (aleph/start-server #'app {:port 3000}))

(comment
  ;; evaluate this def form to start the webapp via the REPL:
  ;; :join? false runs the web server in the background!
  (def server (aleph/start-server #'app {:port 3000}))
  ;; evaluate this form to stop the webapp via the the REPL:
  (.close server))
