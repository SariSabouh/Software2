import java.util.Arrays;

public class UniqueAbbreviator
{
	private Student[] roster;
	private Student[] abbreviatedRoster;
	
	public UniqueAbbreviator() {	}
	
	public UniqueAbbreviator(String[] nameList)
	{
		populateRoster(nameList);
	}
	
	private String parseFirstName(String name, int delimiterIndex)
	{
		String fName = name.substring(delimiterIndex+1, name.length()-1);
		return fName.replace(' ', '');
	}
	
	private String parseLastName(String name, int delimiterIndex)
	{
		String lName = name.substring(0, delimiterIndex-1);
		return lName.replace(' ', '');
	}
	
	public void populateRoster(String[] nameList)
	{
		if (roster == null)
		{
			roster = new Student[nameList.length];
		}
		else
		{
			roster = null;
			roster = new Student[nameList.length];
		}
		
		for (int i = 0; i < nameList.length; i++)
		{
			//Assume that all names passed in will have surname and given name separated by a comma
			//Expected format is lastName, firstName
			int dIndex = nameList[i].indexOf(",");
			Student[i] = new Student(parseFirstName(nameList[i], dIndex), parseLastName(nameList[i], dIndex));
		}
		
		Arrays.sort(roster);
	}
	
	private void makeAbbreviatedRoster()
	{
		
	}
	
	private int[] findNonUniqueIndices()
	{
		int[] nonUnqLocs = new int[roster.length];
		
	}
}