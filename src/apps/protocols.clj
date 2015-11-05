(ns apps.protocols)

(defprotocol Apps
  "A protocol used to provide an abstraction layer for dealing with app metadata."
  (getUser [_])
  (getClientName [_])
  (getJobTypes [_])
  (listAppCategories [_ params])
  (hasCategory [_ category-id])
  (listAppsInCategory [_ category-id params])
  (searchApps [_ search-term params])
  (canEditApps [_])
  (addApp [_ app])
  (previewCommandLine [_ app])
  (listAppIds [_])
  (deleteApps [_ deletion-request])
  (getAppJobView [_ app-id])
  (getAppSubmissionInfo [_ app-id])
  (deleteApp [_ app-id])
  (relabelApp [_ app])
  (updateApp [_ app])
  (copyApp [_ app-id])
  (getAppDetails [_ app-id])
  (removeAppFavorite [_ app-id])
  (addAppFavorite [_ app-id])
  (isAppPublishable [_ app-id])
  (makeAppPublic [_ app])
  (deleteAppRating [_ app-id])
  (rateApp [_ app-id rating])
  (getAppTaskListing [_ app-id])
  (getAppToolListing [_ app-id])
  (getAppUi [_ app-id])
  (addPipeline [_ pipeline])
  (formatPipelineTasks [_ pipeline])
  (updatePipeline [_ pipeline])
  (copyPipeline [_ app-id])
  (editPipeline [_ app-id])
  (listJobs [_ params])
  (loadAppTables [_ app-ids])
  (submitJob [_ submission])
  (submitJobStep [_ job-id submission])
  (translateJobStatus [_ job-type status])
  (updateJobStatus [_ job-step job status end-date])
  (getDefaultOutputName [_ io-map source-step])
  (getJobStepStatus [_ job-step])
  (buildNextStepSubmission [_ job-step job])
  (prepareStepSubmission [_ job-id submission])
  (getParamDefinitions [_ app-id])
  (stopJobStep [_ job-step])
  (categorizeApps [_ body])
  (permanentlyDeleteApps [_ body])
  (adminDeleteApp [_ app-id])
  (adminUpdateApp [_ body])
  (getAdminAppCategories [_ params])
  (adminAddCategory [_ body])
  (adminDeleteCategories [_ body])
  (adminDeleteCategory [_ category-id])
  (adminUpdateCategory [_ body])
  (getAppDocs [_ app-id])
  (ownerEditAppDocs [_ app-id body])
  (ownerAddAppDocs [_ app-id body])
  (adminEditAppDocs [_ app-id body])
  (adminAddAppDocs [_ app-id body]))
