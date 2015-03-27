package edu.ku.it.si.blackboardcoursesforuser.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;

import edu.ku.it.si.blackboardcoursesforuser.controller.BlackboardCoursesForUserController;


/**
 * Application that will get Blackboard courses 
 * for a specific user.
 * Before running this
 * application you must run the 
 * RegisterProxyToolApp.  See the ReadMe.txt
 * for further information.
 * @author bphillips
 *
 */
public class BlackboardCoursesForUserApp {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
	   "applicationContext_BlackboardCoursesForUser.xml");
		
		BlackboardCoursesForUserController blackboardCoursesForUserController;
		
		/**
		 * @param args
		 * @throws PersistenceException 
		 * @throws KeyNotFoundException 
		 */
		public static void main(String[] args) throws KeyNotFoundException, PersistenceException {

					
			BlackboardCoursesForUserApp app = new BlackboardCoursesForUserApp();
			
			app.run();
		
		}

		private void run() throws KeyNotFoundException, PersistenceException {
			
			
			blackboardCoursesForUserController = (BlackboardCoursesForUserController) ctx.getBean("blackboardCoursesForUserController");
			
			blackboardCoursesForUserController.getBlackboardCoursesForUser() ;
			
			
		}


}
