(ns apps.core
  (:gen-class)
  (:use [clojure.java.io :only [file]]
        [apps.kormadb])
  (:require [clojure.tools.logging :as log]
            [common-cli.core :as ccli]
            [me.raynes.fs :as fs]
            [apps.tasks :as tasks]
            [apps.util.config :as config]
            [ring.adapter.jetty :as jetty]
            [service-logging.thread-context :as tc]))

(defn- init-service
  "Initializes the service."
  []
  (define-database))

(defn- iplant-conf-dir-file
  [filename]
  (when-let [conf-dir (System/getenv "IPLANT_CONF_DIR")]
    (let [f (file conf-dir filename)]
      (when (.isFile f) (.getPath f)))))

(defn- cwd-file
  [filename]
  (let [f (file filename)]
    (when (.isFile f) (.getPath f))))

(defn- classpath-file
  [filename]
  (-> (Thread/currentThread)
      (.getContextClassLoader)
      (.findResource filename)
      (.toURI)
      (file)))

(defn- no-configuration-found
  [filename]
  (throw (RuntimeException. (str "configuration file " filename " not found"))))

(defn- find-config-file
  []
  (let [conf-file "apps.properties"]
    (or (iplant-conf-dir-file conf-file)
        (cwd-file conf-file)
        (classpath-file conf-file)
        (no-configuration-found conf-file))))

(defn load-config-from-file
  "Loads the configuration settings from a properties file."
  ([]
     (load-config-from-file (find-config-file)))
  ([cfg-path]
     (config/load-config-from-file cfg-path)
     (init-service)))

(defn cli-options
  []
  [["-c" "--config PATH" "Path to the config file"
    :default "/etc/iplant/de/apps.properties"]
   ["-v" "--version" "Print out the version number."]
   ["-h" "--help"]])

(defn -main
  [& args]
  (tc/with-logging-context config/svc-info
    (require 'apps.routes.api)
    (let [app (eval 'apps.routes.api/app)
          {:keys [options arguments errors summary]} (ccli/handle-args config/svc-info args cli-options)]
      (when-not (fs/exists? (:config options))
        (ccli/exit 1 (str "The config file does not exist.")))
      (when-not (fs/readable? (:config options))
        (ccli/exit 1 "The config file is not readable."))
      (load-config-from-file (:config options))
      (tasks/set-logging-context! config/svc-info)
      (tasks/schedule-tasks)
      (log/warn "Listening on" (config/listen-port))
      (jetty/run-jetty app {:port (config/listen-port)}))))
