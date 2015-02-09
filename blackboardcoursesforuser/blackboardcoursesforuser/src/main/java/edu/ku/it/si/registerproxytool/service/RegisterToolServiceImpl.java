/**
 * 
 */
package edu.ku.it.si.registerproxytool.service;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.rampart.handler.WSSHandlerConstants;
import org.apache.rampart.handler.config.OutflowConfiguration;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.handler.WSHandlerConstants;

import edu.ku.it.si.bbcontextws.generated.ContextWSStub;
import edu.ku.it.si.bbcontextws.generated.ContextWSStub.RegisterTool;
import edu.ku.it.si.bbcontextws.generated.ContextWSStub.RegisterToolResponse;
import edu.ku.it.si.bbcontextws.generated.ContextWSStub.RegisterToolResultVO;

/**
 * @author bphillips
 *
 */
@SuppressWarnings("deprecation")
public class RegisterToolServiceImpl implements RegisterToolService {

	/* (non-Javadoc)
	 * @see edu.ku.it.si.registerproxytool.service.RegisterToolService#registerTool(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public RegisterToolResultVO registerTool(String modulePath, 
			String blackboardServerURL,
			String clientProgramId, String clientVendorId,
			String toolDescription, String sharedSecret,
			String toolRegistrationPassword, String[] toolMethods) throws RemoteException {

		
		/*This specifies where the modules directory is located
		In the modules directory must be the rampart.mar file
		which is available from the Rampart download at 
		http://ws.apache.org/rampart/download.html
		*/
		ConfigurationContext context = ConfigurationContextFactory.createConfigurationContextFromFileSystem(modulePath);

		ContextWSStub contextWSStub = new ContextWSStub(context,
				"http://" + blackboardServerURL + "/webapps/ws/services/Context.WS");
				
				ServiceClient client = contextWSStub._getServiceClient();

				Options options = client.getOptions();

				options.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION,
						HTTPConstants.HEADER_PROTOCOL_10);

				// Next, setup ws-security settings
				RegisterToolServiceImpl.PasswordCallbackClass pwcb = new RegisterToolServiceImpl.PasswordCallbackClass();

				options.setProperty(WSHandlerConstants.PW_CALLBACK_REF, pwcb);
				
				/*
				 * Must use deprecated class of setting up security because
				 * the SOAP response doesn't include a security header.  Using
				 * the deprecated OutflowConfiguration class we can specify
				 * that the security  header is only for the outgoing SOAP
				 * message.
				 */
				OutflowConfiguration ofc = new OutflowConfiguration();
				ofc.setActionItems("UsernameToken Timestamp");
				ofc.setUser("session");

				ofc.setPasswordType("PasswordText");
				options.setProperty(WSSHandlerConstants.OUTFLOW_SECURITY, ofc.getProperty());
				client.engageModule("rampart");
				
				// call initialize method of the Context web service to get the session id
				String sessionValue = contextWSStub.initialize().get_return();
				
				// set the session id on the callback handler so it is used by all subsequent web service calls.
				pwcb.setSessionId(sessionValue);
				
				RegisterTool registerTool = new RegisterTool();
				
				registerTool.setClientProgramId(clientProgramId);
				registerTool.setClientVendorId(clientVendorId);
				registerTool.setDescription(toolDescription);
				registerTool.setInitialSharedSecret(sharedSecret);
				registerTool.setRegistrationPassword(toolRegistrationPassword);
				registerTool.setRequiredToolMethods(toolMethods);
				
				//register the proxy tool
				
				RegisterToolResponse registerToolResponse = contextWSStub.registerTool(registerTool);
				
				RegisterToolResultVO registerToolResultVO = registerToolResponse.get_return() ;
				
				return registerToolResultVO;
		
		

	}
	
	/**
	 * Store the session id value associated with the logged 
	 * in Blackboard web service.
	 * 
	 */
	private static class PasswordCallbackClass implements CallbackHandler {

		String sessionId = null;


		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}


		public void handle(Callback[] callbacks) throws IOException,
				UnsupportedCallbackException {
			for (int i = 0; i < callbacks.length; i++) {
				WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
				String pw = "nosession";

				if (sessionId != null) {
					pw = sessionId;
				}
				pwcb.setPassword(pw);
			}
		}
	}

}
