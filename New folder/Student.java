public class Student implements Comparable
	{
		private String firstName;
		private String lastName;
		
		public Student()
		{
			firstName = "";
			lastName = "";
		}
		
		public Student(String fName, String lName)
		{
			firstName = fName;
			lastName = lName;
		}
		
		public String getFirstName()
		{
			return firstName;
		}
		
		public String getLastName()
		{
			return lastName;
		}
		
		public int compareTo(Object other)
		{
			Student otherStudent = (Student) other;
			return lastName.compareToIgnoreCase(otherStudent.getLastName());
		}
	}