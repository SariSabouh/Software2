package edu.ku.it.si.blackboardcoursesforuser.service;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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

import blackboard.data.content.Content;
import blackboard.data.course.CourseMembership;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import edu.ku.it.si.bbcontextws.generated.ContextWSStub;
import edu.ku.it.si.bbcontextws.generated.ContextWSStub.CourseIdVO;
import edu.ku.it.si.bbcontextws.generated.ContextWSStub.GetMemberships;
import edu.ku.it.si.bbcontextws.generated.ContextWSStub.GetMembershipsResponse;
import edu.ku.it.si.bbcontextws.generated.ContextWSStub.LoginTool;
import edu.ku.it.si.bbcoursews.generated.CourseWSStub;
import edu.ku.it.si.bbcoursews.generated.CourseWSStub.CourseFilter;
import edu.ku.it.si.bbcoursews.generated.CourseWSStub.CourseVO;
import edu.ku.it.si.bbcoursews.generated.CourseWSStub.GetCourse;
import edu.ku.it.si.bbcoursews.generated.CourseWSStub.GetCourseResponse;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.AttemptFilter;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.ColumnFilter;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.ColumnVO;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.DeleteColumns;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetAttempts;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetGradebookColumns;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetGradebookColumnsResponse;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetGrades;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.GetGradesResponse;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.SaveColumns;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.SaveGrades;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.ScoreFilter;
import edu.ku.it.si.bbgradebookws.generated.GradebookWSStub.ScoreVO;

/**
 * Implements methods to enable getting the courses
 * a Blackboard user is enrolled in by using
 * the Blackboard Learn 9.1 web services.
 * 
 * @author bphillips
 *
 */
@SuppressWarnings("deprecation")
public class BlackboardCoursesForUserServiceImpl implements BlackboardCoursesForUserService {
	

	private static final Logger logger = Logger.getLogger(BlackboardCoursesForUserServiceImpl.class.getName() );
	GradebookWSStub gradebookWSStub;

	@Override
	public List<String> getBlackboardCoursesForUser(String modulePath, String blackboardServerURL,
			String sharedSecret, String vendorId, String clientProgramId, String username)
			throws RemoteException, KeyNotFoundException, PersistenceException {
		
		List<String> courseTitles = new ArrayList<String>();
		List<String> scoreNum = new ArrayList<String>();
		
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
		ServiceClient client2 = contextWSStub._getServiceClient();

		Options options = client.getOptions();
		Options options2 = client.getOptions();

		options.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION,
				HTTPConstants.HEADER_PROTOCOL_10);
		options2.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION,
				HTTPConstants.HEADER_PROTOCOL_10);
		
		/*
		 * STEP 2:  Setup the Web Service - Security settings
		 */

		// Next, setup ws-security settings and specify the 
		// CallBackHandler class
		BlackboardCoursesForUserServiceImpl.PasswordCallbackClass pwcb = new BlackboardCoursesForUserServiceImpl.PasswordCallbackClass();

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
		
		if (loginResult == true) {
			
			/*
			 * STEP 6 - Use a specific method of the Web Service to perform the operation
			 * we need for this application
			 */
			
			
			/* use the Context web service to get the
			 * Blackboard course memberships for the provided
			 * Blackboard username
			 */
			
			/* Create a GetMemberships object and specify
			 * the username to get the course memberships for
			 * 
			 */
			GetMemberships getMemberships = new GetMemberships();
			getMemberships.setUserid(username);
			
			
			GetMembershipsResponse getMembershipsResponse = contextWSStub.getMemberships(getMemberships);
			
			
			/*
			 * STEP 7 - Process the web service response from our Blackboard installation
			 */
		
			/*
			 * Get the return from calling the web service method
			 * which is an Array of CourseIDVO objects
			 */
			CourseIdVO [] courseIdVOs = getMembershipsResponse.get_return() ;
            /*
             *Create an Array of String objects where each String
             *is a Course id value
             */
			
			String [] courseIds = new String[courseIdVOs.length];
			
			int i = 0;
			
			for (CourseIdVO courseIdVO : courseIdVOs) {

				courseIds[i] = courseIdVO.getExternalId();
				
				i++;
				
			}
//			Id courseID = 
//			System.out.println(courseID.toString());
			/*List <CourseMembership> cmlist = CourseMembershipDbLoader.Default.getInstance().loadByCourseIdAndRole(courseID, CourseMembership.Role.STUDENT, null, true);
			Iterator<CourseMembership> students = cmlist.iterator();
			students = cmlist.iterator();
			String currentUserID = "";
			while(students.hasNext()){
				CourseMembership cm = (CourseMembership) students.next();
				currentUserID = cm.getUserId().toString();	
				String lastName = cm.getUser().getFamilyName();
				String external = cm.getUserId().toExternalString();
				String[] parts = external.split("_");
				String externalParsed = parts[1];
				System.out.println("<option value='" + externalParsed + "'>" + lastName + "</option>");
			}*/
			
			/*
			 * Create a GetCourse object that is used
			 * to specify how we want the Course web
			 * service to filter the Blackboard courses
			 * when we call the Course web service's getCourse
			 * method.
			 */
			GetCourse getCourse = new GetCourse();
			GetGrades getGrades = new GetGrades();
			GetGradebookColumns getColumns = new GetGradebookColumns();
			
			CourseFilter courseFilter = new CourseFilter();
			ScoreFilter scoreFilter = new ScoreFilter();
			ColumnFilter columnFilter = new ColumnFilter(); 
			//Filter type 3 is for the id value of the 
			//course which is the PK1 column value in 
			//the course_main table
			courseFilter.setFilterType(3);
			scoreFilter.setFilterType(1);
			columnFilter.setFilterType(1); 
			
			courseFilter.setIds(courseIds);
			scoreFilter.setId(courseIds[0]);
			columnFilter.setIds(courseIds); 
			
			getCourse.setFilter(courseFilter);
			getGrades.setFilter(scoreFilter);
			getColumns.setFilter(columnFilter);
			
			
			/*
			 * STEP 8 - Create an object of another Blackboard web service to perform the next operation
			 * we need for this application
			 */
			
			 /*
			 * Use the Course web service classes to get the
			 * Course value object for each course ID 
			 * stored in the courseIds array.
			 */
			CourseWSStub courseWSStub = new CourseWSStub(ctx,
					"http://" + blackboardServerURL + "/webapps/ws/services/Course.WS");
			gradebookWSStub = new GradebookWSStub(ctx,
					"http://" + blackboardServerURL + "/webapps/ws/services/Gradebook.WS");

			client = courseWSStub._getServiceClient();
			client2 = gradebookWSStub._getServiceClient();

			options = client.getOptions();
			options2 = client2.getOptions();

			options.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION,
					HTTPConstants.HEADER_PROTOCOL_10);
			options2.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION,
					HTTPConstants.HEADER_PROTOCOL_10);
			
			/*
			 * STEP 9 - Setup the WS-Security for the request to this web service
			 * NOTE that we will re-use the same callback handler (with its session ID)
			 * as the previous web service request used.
			 */

			// Next, setup ws-security settings
			// Reuse the same callback handler
			options.setProperty(WSHandlerConstants.PW_CALLBACK_REF, pwcb);
			options2.setProperty(WSHandlerConstants.PW_CALLBACK_REF, pwcb);
			ofc = new OutflowConfiguration();
			ofc.setActionItems("UsernameToken Timestamp");
			ofc.setUser("session");

			ofc.setPasswordType("PasswordText");
			options.setProperty(WSSHandlerConstants.OUTFLOW_SECURITY, ofc
					.getProperty());
			options2.setProperty(WSSHandlerConstants.OUTFLOW_SECURITY, ofc
					.getProperty());
			client.engageModule("rampart");
			client2.engageModule("rampart");
			
			/*
			 * STEP 10 - make the request to this web service
			 */
			
			GetCourseResponse getCourseResponse = courseWSStub.getCourse(getCourse);
			getGrades.setCourseId(courseIds[0]);
			GetGradesResponse getGradeResponse = gradebookWSStub.getGrades(getGrades);
			getGrades.setCourseId(courseIds[0]);
			
			getColumns.setCourseId(courseIds[0]);
			GetGradebookColumnsResponse getGradebookColumnsResponse = gradebookWSStub.getGradebookColumns(getColumns);
			
			/*
			 * STEP 11 - process the response from this web service
			 */
			
			GetAttempts getAttempts = new GetAttempts();
			getAttempts.setCourseId(courseIds[0]);
			
			AttemptFilter atFilter = new AttemptFilter();
			atFilter.setFilterType(0);
			
			getAttempts.setFilter(atFilter);
			
			CourseVO [] courseVOs = getCourseResponse.get_return() ;
			ScoreVO [] scoreVOs = getGradeResponse.get_return();
			ColumnVO [] columnVOs = getGradebookColumnsResponse.get_return();
			
			
			
			for (CourseVO courseVO : courseVOs) {
				
				courseTitles.add( courseVO.getName() );
			}
			
			logger.debug("Course names found for classes " + username + " is enrolled in are " + courseTitles.toString());
			
			//columnVOs = createColumn(columnVOs, "Add1"); // LINE THAT ADDS A COOLUMN WITH DESIRED NAME, after adding comment it to change grade to 99!
			scoreVOs = checkArray(scoreVOs, columnVOs);
			
			//deleteColumn(columnVOs, 3); // LINE THAT DELETS A COLUMN TAKES THE ARRAY OF COLUMNS AND THE LOCATION OF THE COLUMN
			
			for(int j = 0; j<columnVOs.length; j++){
				scoreNum.add(columnVOs[j].getColumnName());
				for(int k = 0; k<scoreVOs.length; k++){
					if(scoreVOs[k].getColumnId().equals(columnVOs[j].getId()))
						scoreNum.add(scoreVOs[k].getGrade());
					if(scoreVOs[k].getGrade() == null)
						changeGrade(scoreVOs[k], "53");
				}
			}
			updateGrades(scoreVOs, courseIds[0]);
			
		}
		
		
		System.out.println(gradeDisplay(scoreNum));
		return scoreNum;

		
	}
	
	public void deleteColumn(ColumnVO[] col, int i) throws RemoteException{
		System.out.println(col[i].getColumnDisplayName());
		DeleteColumns del = new DeleteColumns();
		del.setCourseId(col[i].getCourseId());
		String [] ids = {col[i].getId()};
		del.setIdsToDelete(ids);
		del.setOnlyIfEmpty(false);
		gradebookWSStub.deleteColumns(del);
		SaveColumns save = new SaveColumns();
		save.setColumns(col);
		save.setCourseId(col[i].getCourseId());
		updateColumns(save);
	}
	
	public ScoreVO[] checkArray(ScoreVO[] scr, ColumnVO[] col){
		ArrayList<String> ids = new ArrayList<String>();
		ArrayList<ScoreVO> output = new ArrayList<ScoreVO>();
		for(int i = 0; i<scr.length; i++)
			ids.add(scr[i].getColumnId());
		for(int i = 0; i<col.length; i++){
			if(!ids.contains(col[i].getId())){
				ScoreVO grade = new ScoreVO();
				grade.setCourseId(scr[0].getCourseId());
				grade.setGrade(null);
				grade.setColumnId(col[i].getId());
				grade.setId("_58_1");
				grade.setMemberId(scr[0].getMemberId());
				grade.setExempt(false);
				grade.setExpansionData(scr[0].getExpansionData());
				grade.setInstructorComments(" ");
				grade.setManualGrade(null);
				grade.setManualScore(0.1);
				grade.setSchemaGradeValue(null);
				grade.setStatus(1);
				grade.setStudentComments(" ");
				
				output.add(grade);
			}
		}
		ScoreVO [] newScores = new ScoreVO[scr.length + output.size()];
		for(int i = 0; i<newScores.length; i++){
			if(i<scr.length){
				if(newScores[i] == null)
					newScores[i] = scr[i];
				else
					newScores[i+1] = scr[i];
			}
			if(i<output.size())
				newScores[i+1] = output.get(i);
		}
		return newScores;
	}
	
	public String gradeDisplay(List<String> list){
		String output = "";
		for(int i = 0; i < list.size(); i++)
		{
			if(i % 2 == 0)
				output += list.get(i) + " grade is: ";
			else
				output += list.get(i) + "\n";
		}
		return output;
	}
	
	public void updateGrades(ScoreVO[] scores, String id) throws RemoteException{
		SaveGrades save = new SaveGrades();
		save.setCourseId(id);
		save.setGrades(scores);
		save.setOverrideIfManual(true);
		for(ScoreVO score: scores){
			changeGrade(score, score.getGrade());
		}
		gradebookWSStub.saveGrades(save);	
	}
	
	public void changeGrade(ScoreVO score, String grade){
		score.setGrade(grade);
		score.setManualGrade(grade);
		score.setManualScore(Double.parseDouble(grade));
	}
	
	public void updateColumns(SaveColumns save) throws RemoteException{
		gradebookWSStub.saveColumns(save);
	}
	
	public ColumnVO[] createColumn(ColumnVO[] col, String name) throws RemoteException{
		ArrayList<ColumnVO> columns = new ArrayList<ColumnVO>();
		for(int i = 0; i<col.length; i++){
			if(i != 4)
				columns.add(col[i]);
		}
		ColumnVO newCol = new ColumnVO();
		newCol.setAggregationModel("Last");
		newCol.setCalculationType("NON_CALCULATED");
		newCol.setColumnDisplayName(name);
		newCol.setColumnName(name);
		newCol.setId("_200_1");
		newCol.setCourseId(col[0].getCourseId());
		newCol.setDateCreated(col[3].getDateCreated());
		newCol.setDateModified(col[3].getDateModified());
		newCol.setDescription("");
		newCol.setDescriptionType("HTML");
		newCol.setDueDate(0);
		newCol.setExpansionData(col[3].getExpansionData());
		newCol.setMultipleAttempts(0);
		newCol.setHideAttempt(false);
		newCol.setPosition(4);
		newCol.setPossible(101);
		newCol.setScaleId(col[3].getScaleId());
		newCol.setScorable(true);
		newCol.setShowStatsToStudent(false);
		newCol.setVisible(true);
		newCol.setVisibleInBook(true);
		newCol.setWeight(0);
		newCol.setDeleted(false);
		newCol.setExternalGrade(false);
		columns.add(newCol);
		ColumnVO[] newColumns = columns.toArray(col);
		SaveColumns save = new SaveColumns();
		save.setColumns(newColumns);
		save.setCourseId(col[0].getCourseId());
		updateColumns(save);
		return newColumns;
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
