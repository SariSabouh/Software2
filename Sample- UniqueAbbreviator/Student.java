public class Student implements Comparable <Student>
	{
		private String firstName;
		private String lastName;
		private String abbreviatedName;
		private final int MAX_NAME_LENGTH = 16;
		
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
			if(lName.length() > MAX_NAME_LENGTH)
				abbreviatedName = fName.charAt(0) + lName.substring(0,MAX_NAME_LENGTH);
			else
				abbreviatedName = fName.charAt(0) + lName;
		}
		
		public void setAbbreviatedName(String abName)
		{
			abbreviatedName = abName;
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
		
		@Override
		public int compareTo(Student other)
		{
			return lastName.compareToIgnoreCase(other.getLastName());
		}
		
		@Override
		public String toString()
		{
			return firstName + " " + lastName + " " + abbreviatedName;
		}
	}