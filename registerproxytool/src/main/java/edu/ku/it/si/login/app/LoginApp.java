package edu.ku.it.si.login.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.ku.it.si.login.controller.LoginController;


/**
 * Application that will login 
 * to Blackboard Learn 9.1 web services
 * using a Proxy tool.  Before running this
 * application you must run the 
 * RegisterProxyToolApp.  See the ReadMe.txt
 * for further information.
 * @author bphillips
 *
 */
public class LoginApp {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
	   "applicationContext_BBWS_Login.xml");
		
		LoginController loginController;
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {

					
			LoginApp app = new LoginApp();
			
			app.run();
		
		}

		private void run() {
			
			
			loginController = (LoginController) ctx.getBean("loginController");
			
			loginController.loginUsingProxyTool();
			
			
		}


}
