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
	
	private void makeAbbreviatedRoster()
	{
		int[] indices = findNonUniqueIndices();
	}
	
	private int[] findNonUniqueIndices()
	{
		int[] nonUnqLocs = new int[roster.length];
		String prevLName, currentLName, nextLName, prevFirstInitial, currentFirstInitial, nextFirstInitial = "";
		int nonUniqueCounter = 0;
		for (int i = 0; i < roster.length; i++)
		{
			if (i >=1)
			{
				prevLName = roster[i-1].getLastName();
				prevFirstInitial = roster[i-1].getFirstName().substring(0,1);
			}
			else
			{
				prevLName = "";
				prevFirstInitial = "";
			}
			
			currentLName = roster[i].getLastName();
			currentFirstInitial = roster[i].getFirstName().substring(0,1);
			
			if (i < roster.length-1)
			{
				nextLName = roster[i+1].getLastName();
				nextFirstInitial = roster[i+1].getFirstName().substring(0,1)
			}
			else
			{
				nextLName = "";
				nextFirstInitial = "";
			}
			
			if ((String.valueOf(currentLName) == String.valueOf(nextLName)) && (String.valueOf(currentFirstInitial) == String.valueOf(nextFirstInitial)))
			{
				nonUnqLocs[nonUniqueCounter] = i;
				nonUniqueCounter++;
			}
			
			else if (String.valueOf(currentLName) == String.valueOf(prevLName) && (String.valueOf(currentFirstInitial) == String.valueOf(prevFirstInitial)))
			{
				nonUnqLocs[nonUniqueCounter] = i;
				nonUniqueCounter++;
			}
		}
		
		int[] compactIndexArray = new int[nonUniqueCounter];
		for (int i = 0; i < nonUniqueCounter; i++)
			compactIndexArray[i] = nonUnqLocs[i];
		
		return compactIndexArray;		
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
	
	public void getAbbreviatedRoster()
	{
		return abbreviatedRoster;
	}
}