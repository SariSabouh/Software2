
/**
 * GradebookWSCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package edu.ku.it.si.bbgradebookws.generated;

    /**
     *  GradebookWSCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class GradebookWSCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public GradebookWSCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public GradebookWSCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getGradebookTypes method
            * override this method for handling normal response from getGradebookTypes operation
            */
           public void receiveResultgetGradebookTypes(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetGradebookTypesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGradebookTypes operation
           */
            public void receiveErrorgetGradebookTypes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveGradingSchemas method
            * override this method for handling normal response from saveGradingSchemas operation
            */
           public void receiveResultsaveGradingSchemas(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.SaveGradingSchemasResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveGradingSchemas operation
           */
            public void receiveErrorsaveGradingSchemas(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for initializeGradebookWS method
            * override this method for handling normal response from initializeGradebookWS operation
            */
           public void receiveResultinitializeGradebookWS(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.InitializeGradebookWSResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from initializeGradebookWS operation
           */
            public void receiveErrorinitializeGradebookWS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteColumns method
            * override this method for handling normal response from deleteColumns operation
            */
           public void receiveResultdeleteColumns(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.DeleteColumnsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteColumns operation
           */
            public void receiveErrordeleteColumns(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateColumnAttribute method
            * override this method for handling normal response from updateColumnAttribute operation
            */
           public void receiveResultupdateColumnAttribute(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.UpdateColumnAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateColumnAttribute operation
           */
            public void receiveErrorupdateColumnAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGrades method
            * override this method for handling normal response from getGrades operation
            */
           public void receiveResultgetGrades(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetGradesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGrades operation
           */
            public void receiveErrorgetGrades(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAttempts method
            * override this method for handling normal response from getAttempts operation
            */
           public void receiveResultgetAttempts(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetAttemptsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAttempts operation
           */
            public void receiveErrorgetAttempts(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveGradebookTypes method
            * override this method for handling normal response from saveGradebookTypes operation
            */
           public void receiveResultsaveGradebookTypes(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.SaveGradebookTypesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveGradebookTypes operation
           */
            public void receiveErrorsaveGradebookTypes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getRequiredEntitlements method
            * override this method for handling normal response from getRequiredEntitlements operation
            */
           public void receiveResultgetRequiredEntitlements(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetRequiredEntitlementsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getRequiredEntitlements operation
           */
            public void receiveErrorgetRequiredEntitlements(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteGrades method
            * override this method for handling normal response from deleteGrades operation
            */
           public void receiveResultdeleteGrades(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.DeleteGradesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteGrades operation
           */
            public void receiveErrordeleteGrades(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveGrades method
            * override this method for handling normal response from saveGrades operation
            */
           public void receiveResultsaveGrades(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.SaveGradesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveGrades operation
           */
            public void receiveErrorsaveGrades(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteGradingSchemas method
            * override this method for handling normal response from deleteGradingSchemas operation
            */
           public void receiveResultdeleteGradingSchemas(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.DeleteGradingSchemasResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteGradingSchemas operation
           */
            public void receiveErrordeleteGradingSchemas(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteGradebookTypes method
            * override this method for handling normal response from deleteGradebookTypes operation
            */
           public void receiveResultdeleteGradebookTypes(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.DeleteGradebookTypesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteGradebookTypes operation
           */
            public void receiveErrordeleteGradebookTypes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveColumns method
            * override this method for handling normal response from saveColumns operation
            */
           public void receiveResultsaveColumns(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.SaveColumnsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveColumns operation
           */
            public void receiveErrorsaveColumns(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServerVersion method
            * override this method for handling normal response from getServerVersion operation
            */
           public void receiveResultgetServerVersion(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetServerVersionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServerVersion operation
           */
            public void receiveErrorgetServerVersion(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGradebookColumns method
            * override this method for handling normal response from getGradebookColumns operation
            */
           public void receiveResultgetGradebookColumns(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetGradebookColumnsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGradebookColumns operation
           */
            public void receiveErrorgetGradebookColumns(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGradingSchemas method
            * override this method for handling normal response from getGradingSchemas operation
            */
           public void receiveResultgetGradingSchemas(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetGradingSchemasResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGradingSchemas operation
           */
            public void receiveErrorgetGradingSchemas(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteAttempts method
            * override this method for handling normal response from deleteAttempts operation
            */
           public void receiveResultdeleteAttempts(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.DeleteAttemptsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteAttempts operation
           */
            public void receiveErrordeleteAttempts(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveAttempts method
            * override this method for handling normal response from saveAttempts operation
            */
           public void receiveResultsaveAttempts(
                    edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.SaveAttemptsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveAttempts operation
           */
            public void receiveErrorsaveAttempts(java.lang.Exception e) {
            }
                


    }
    