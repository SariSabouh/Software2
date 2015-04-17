public class Student implements Comparable <Student>
	{
		private String firstName;
		private String lastName;
		private String abbreviatedName;
		
		public Student()
		{
			firstName = "";
			lastName = "";
			abbreviatedName = "";
		}
		
		public Student(String fName, String lName)
		{
			firstName = fName;
			lastName = lName;
			abbreviatedName = "";
		}
		
		public void setFirstName(String fName)
		{
			firstName = fName;
		}
		
		public void setLastName (String lName)
		{
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
		
		public String getAbbreviatedName()
		{
			return abbreviatedName;
		}
		
		public int compareTo(Student other)
		{
			return lastName.compareToIgnoreCase(other.getLastName());
		}
	}