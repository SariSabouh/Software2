package edu.ku.it.si.login.service;

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
import org.apache.log4j.Logger;
import org.apache.rampart.handler.WSSHandlerConstants;
import org.apache.rampart.handler.config.OutflowConfiguration;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.handler.WSHandlerConstants;

import edu.ku.it.si.bbcontextws.generated.ContextWSStub;
import edu.ku.it.si.bbcontextws.generated.ContextWSStub.LoginTool;

/**
 * Implements methods to enable logging in
 * to Blackboard Learn 9.1 web services
 * using a proxy tool.
 * @author bphillips
 *
 */
@SuppressWarnings("deprecation")
public class LoginServiceImpl implements LoginService {
	
	private static final Logger logger = Logger.getLogger(LoginServiceImpl.class.getName() );

	/* (non-Javadoc)
	 * @see edu.ku.it.si.login.service.LoginService#loginUsingProxyTool(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean loginUsingProxyTool(String modulePath, String blackboardServerURL,
			String sharedSecret, String vendorId, String clientProgramId)
			throws RemoteException {
		
		/*This specifies where the modules directory is located
		In the modules directory must be the rampart.mar file
		which is available from the Rampart download at 
		http://ws.apache.org/rampart/download.html
		*/
		ConfigurationContext ctx = ConfigurationContextFactory.createConfigurationContextFromFileSystem(modulePath);
		
		/*
		 * STEP 1 - Create object of the Web Service we need to use for the operation
		 */

		/*
		 * Create an object of the ContextWSStub.  This class
		 * was generated by using Axis2 and the Context web service
		 * WSDL file.  After generating the file it was copied into 
		 * this project - see package edu.ku.it.si.bbcontextws.generated.
		 * The Context web service is used to get a session id value
		 * from the Blackboard installation and then login using the
		 * the proxy tool.
		 */
		ContextWSStub contextWSStub = new ContextWSStub(ctx,
		"http://" + blackboardServerURL + "/webapps/ws/services/Context.WS");
		
		ServiceClient client = contextWSStub._getServiceClient();

		Options options = client.getOptions();

		options.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION,
				HTTPConstants.HEADER_PROTOCOL_10);
		
		/*
		 * STEP 2:  Setup the Web Service - Security settings
		 */

		// Next, setup ws-security settings
		LoginServiceImpl.PasswordCallbackClass pwcb = new LoginServiceImpl.PasswordCallbackClass();

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
		
		/*
		 * STEP 3: Get the session ID from Blackboard for using this web service
		 */
		
		// call initialize method of the Context web service to get the sessionid
		String sessionValue = contextWSStub.initialize().get_return();
		
		logger.info("Initialized context with Blackboard installation at " + blackboardServerURL + 
				".  Value returned from calling initialize method is " + sessionValue);
		
		// set the sessionid on the callback handler so it is used by all subsequent webservice calls.
		pwcb.setSessionId(sessionValue);
		
		/*
		 * STEP 4:  Login to our Blackboard installation using the information for a proxy tool
		 * that is authorized.
		 */
		
		/*Create a LoginTool object that has information
		*needed to authorize this session to use the
		*web services.  In this example, we are using the
        *proxy tool that was previously setup by 
        *another program and approved to access the Blackboard
        *web services classes and methods
		*/
		LoginTool loginArgs = new LoginTool();
	    loginArgs.setPassword( sharedSecret );
	    
	    //see Blackboard administrator proxy tools for vendor id 
	    //and program id values for this tool
	    loginArgs.setClientVendorId( vendorId );
	    loginArgs.setClientProgramId( clientProgramId );
	    loginArgs.setLoginExtraInfo( "" );
	    loginArgs.setExpectedLifeSeconds( 60 * 60 );
		
		//Call the Context Web Services login method 
		//so that the session id is authorized to access
		//web services 
		boolean loginResult = contextWSStub.loginTool(loginArgs).get_return();
		
		return loginResult;
		
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
