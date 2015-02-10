package edu.ku.it.si.login.controller;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import edu.ku.it.si.login.service.LoginService;

/**
 * Acts as a controller to provide behaviors
 * that enable interaction between a view
 * and login services.
 * @author bphillips
 *
 */
public class LoginController {

	private static final Logger logger = Logger.getLogger(LoginController.class.getName() );
	
	/*
	 * Values for these instance fields are injected using Spring.
	 * See the Spring configuration file applicationContext_BBWS_Login.xml.
	 */
	private LoginService loginService ;
	
    private String blackboardServerURL ;
	
	private String clientVendorId ;
	
	private String clientProgramId;
	
	private String modulePath ;
	
	private String sharedSecret ;
	
	
	/**
	 * Using the values of the instance fields (see
	 * public set methods) and the LoginService 
	 * object login to the Blackboard Learn 9.1
	 * web services using a proxy tool that 
	 * was previously registered and authorized.
	 */
	public void loginUsingProxyTool() {
		
		try {
			
			boolean loginResult = loginService.loginUsingProxyTool(modulePath, blackboardServerURL, sharedSecret, clientVendorId, clientProgramId) ;
		
			if ( loginResult == true ) {
				
				logger.info("Login using proxy tool named " + clientProgramId + 
						" was successful.");
	
			} else {
				
				logger.info("Login using proxy tool named " + clientProgramId + 
				" was NOT successful. Consult the context web services log " + 
				"in your Blackboard installation for details.");
				
			}
		
		
		} catch (RemoteException e) {
			
			logger.error("There was an error when logging into the Blackboard Learn 9.1 web service using the proxy tool: " + e.getMessage() );
			
		}
		
		
		
	}
	
	
	public LoginService getLoginService() {
		return loginService;
	}

	
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public String getBlackboardServerURL() {
		return blackboardServerURL;
	}

    /**
     * Set value of the URL to the Blackboard installation
     * @param blackboardServerURL
     */
	public void setBlackboardServerURL(String blackboardServerURL) {
		this.blackboardServerURL = blackboardServerURL;
	}

	public String getClientVendorId() {
		return clientVendorId;
	}

	/**
	 * Set value of the organization's vendor id for the 
	 * proxy tool being used - see value set in Blackboard
	 * System Admin.
	 * @param clientVendorId
	 */
	public void setClientVendorId(String clientVendorId) {
		this.clientVendorId = clientVendorId;
	}

	public String getClientProgramId() {
		return clientProgramId;
	}

	/**
	 * Set value of the name for the 
	 * proxy tool being used - see Blackboard
	 * System Admin.
	 * @param clientProgramId
	 */
	public void setClientProgramId(String clientProgramId) {
		this.clientProgramId = clientProgramId;
	}

	public String getModulePath() {
		return modulePath;
	}

	/**
	 * Set complete path to location of modules folder that
	 * contains the rampart.mar file.
	 * @param modulePath
	 */
	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}

	public String getSharedSecret() {
		return sharedSecret;
	}

	/**
	 * Set value of the shared secret for the 
	 * proxy tool being used - see value set in Blackboard
	 * System Admin.
	 * @param sharedSecret
	 */
	public void setSharedSecret(String sharedSecret) {
		this.sharedSecret = sharedSecret;
	}

	
	
}
