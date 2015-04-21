public class Student implements Comparable <Student>
{
		private String firstName;
		private String lastName;
		private String abbreviatedName;
		private int MAX_NAME_LENGTH = 16; //Initialize the maximum length of a name string to 16
		
		public Student(String fName, String lName)
		{
			firstName = fName;
			lastName = lName;
			if(lName.length() > MAX_NAME_LENGTH)
				abbreviatedName = fName.charAt(0) + lName.substring(0,MAX_NAME_LENGTH +1);
			else
				abbreviatedName = fName.charAt(0) + lName;
		}
		
		//Setter method allows to change the maximum number of characters for first or last name later on
		public void setMaxNameLength(int length)
		{
			MAX_NAME_LENGTH = length;
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
		
		public String getFirstNameWithAtMostSpecifiedLength()
		{
			if ( firstName.length() > MAX_NAME_LENGTH)
				return firstName.substring(0,MAX_NAME_LENGTH +1); 
			else
				return firstName;
		}
		
		public String getLastName()
		{
			return lastName;
		}
		
		public String getLastNameWithAtMostSpecifiedLength()
		{
			if (lastName.length() > MAX_NAME_LENGTH)
				return lastName.substring(0,MAX_NAME_LENGTH +1);
			else
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