(ns apps.routes.schemas.callback
  (:use [common-swagger-api.schema :only [describe]]
        [schema.core :only [defschema optional-key Keyword Any]]))

(defschema DeJobState
  {:status
   (describe String "The current status of the analysis")

   :uuid
   (describe String "The external identifier of the analysis")

   (optional-key :completion_date)
   (describe String "The analysis completion date as milliseconds since the epoch")})

(defschema DeJobStatusUpdate
  {:state (describe DeJobState "The current state of the analysis")})

(defschema AgaveJobStatusUpdateParams
  {:status      (describe String "The status assigned to the job by Agave")
   :external-id (describe String "Agave's identifier for the job")
   :end-time    (describe String "The analysis completion timestamp")})

(defschema AgaveJobStatusUpdate
  {:lastUpdated (describe String "The time the job status was last updated")
   Keyword      Any})
