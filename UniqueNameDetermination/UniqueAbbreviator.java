public class UniqueAbbreviator
{
	private Student[] roster;
	public UniqueAbbreviator(String[] nameList)
	{
		roster = new Student[nameList.length];
		
		//Assume that all names passed in will have surname and given name separated by a comma
		for (int i = 0; i < nameList.length; i++)
		{			
			Student[i] = new Student(parseFirstName(nameList[i]), parseLastName(nameList[i]));
		}
	}
	
	private String parseFirstName(String name)
	{
		
	}
	
	private String parseLastName(String name)
	{
		
	}
}