package edu.ku.it.si.login.service;

import java.rmi.RemoteException;





/**
 * Defines behaviors a class must implement to enable
 * logging into Blackboard Learn 9.1 web services
 * using a proxy tool.
 * @author bphillips
 *
 */
public interface LoginService {
	
	
	/**
	 * Login to the Blackboard system using the proxy tool.  Note that the
	 * proxy tool is setup by a separate application. 
	 * @param modulePath path on computer to location of modules directory 
	 *     that contains the rampart.mar file
	 * @param blackboardServerURL URL of the Blackboard server
	 * @param sharedSecret - shared secret for the proxy tool
	 * @param vendorId Organzation's vendor ID
	 * @param clientProgramId name of the proxy tool to use
	 * @return true if login successful to the Blackboard system otherwise false
	 * @throws RemoteException
	 */
	public boolean loginUsingProxyTool(String modulePath, String blackboardServerURL, 
			String sharedSecret, String vendorId, 
			String clientProgramId) throws RemoteException;
	 

}
