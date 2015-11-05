(ns apps.service.apps.de.jobs.protocol)

(defprotocol JobRequestFormatter
  "A protocol for formatting JEX job requests."
  (buildTreeSelectionArgs [_ param param-value])
  (buildSelectionArgs [_ param param-value])
  (buildFlagArgs [_ param param-value])
  (buildInputArgs [_ param param-value])
  (buildOutputArgs [_ param param-value])
  (buildReferenceGenomeArgs [_ param param-value])
  (buildReferenceSequenceArgs [_ param param-value])
  (buildReferenceAnnotationArgs [_ param param-value])
  (buildGenericArgs [_ param param-value])
  (buildInputs [_ params])
  (buildOutputs [_ params])
  (buildParams [_ params outputs])
  (buildConfig [_ steps step])
  (buildEnvironment [_ step])
  (buildComponent [_ step])
  (buildStep [_ steps step])
  (buildSteps [_])
  (buildSubmission [_]))
