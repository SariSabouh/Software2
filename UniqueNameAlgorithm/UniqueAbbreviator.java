import java.util.Arrays;
import java.util.LinkedList;

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
		String fName = name.substring(delimiterIndex+1);
		return fName.replace(' ', '\0');
	}
	
	private String parseLastName(String name, int delimiterIndex)
	{
		String lName = name.substring(0, delimiterIndex);
		return lName.replace(' ', '\0');
	}
	
	private void makeAbbreviatedRoster()
	{
		abbreviatedRoster = new Student[roster.length];
		
		for (int i = 0; i < abbreviatedRoster.length; i++)
			abbreviatedRoster[i] = new Student(roster[i].getFirstName().substring(0,1), roster[i].getLastName());
		
		int[] problematicIndices = findNonUniqueIndices();
		int duplicateNameGroupCount = countDuplicateNames(problematicIndices);
		
		Student[] fixedOffenders = new Student[problematicIndices.length];
		
		int currentIndex = 0;
		
		for (int i = 0; i < duplicateNameGroupCount; i++)
		{
			LinkedList<Student> group = new LinkedList<Student>();
			
			while ((String.valueOf(roster[problematicIndices[currentIndex]].getLastName()) == String.valueOf(roster[problematicIndices[currentIndex+1]].getLastName())) && (String.valueOf(roster[problematicIndices[currentIndex]].getFirstName().substring(0,1)) == String.valueOf(roster[problematicIndices[currentIndex+1]].getFirstName().substring(0,1))))
			{
				group.add(roster[problematicIndices[currentIndex]]);
				currentIndex++;
			}
			
			group.add(roster[problematicIndices[currentIndex]]);
			currentIndex++;
			
			LinkedList<Student> fixedGroup = getUniqueNameList(group);
			for (int j = currentIndex-fixedGroup.size()+1; j < currentIndex+1; j++)
			{
				fixedOffenders[j] = new Student(fixedGroup.get(j).getFirstName(), fixedGroup.get(j).getLastName());
			}
		}
		
		for (int i = 0; i < fixedOffenders.length; i++)
		{
			abbreviatedRoster[problematicIndices[i]] = new Student(fixedOffenders[i].getFirstName(), fixedOffenders[i].getLastName());
		}
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
				nextFirstInitial = roster[i+1].getFirstName().substring(0,1);
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
	
	private int countDuplicateNames(int[] indicesToCheck)
	{
		int counter = 0;
		boolean sameGroup = false;
		for (int i = 0; i < indicesToCheck.length; i++)
		{
			if (i < indicesToCheck.length-1)
			{
				if (String.valueOf(roster[indicesToCheck[i]].getLastName()) == String.valueOf(roster[indicesToCheck[i+1]].getLastName()))
				{
					if (String.valueOf(roster[indicesToCheck[i]].getFirstName().substring(0,1)) == String.valueOf(roster[indicesToCheck[i+1]].getFirstName().substring(0,1)))
						sameGroup = true;
				}
			}
			else
				counter++;
			
			if (i > 0)
			{
				if (String.valueOf(roster[indicesToCheck[i]].getLastName()) != String.valueOf(roster[indicesToCheck[i-1]].getLastName()))
					counter++;
				else if (String.valueOf(roster[indicesToCheck[i]].getLastName()) != String.valueOf(roster[indicesToCheck[i-1]].getLastName()))
					counter++;
			}
		}
		
		return counter;
	}
	
	private LinkedList<Student> getUniqueNameList(LinkedList<Student> rawList)
	{
		return null;
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
			roster[i] = new Student(parseFirstName(nameList[i], dIndex), parseLastName(nameList[i], dIndex));
		}
		
		Arrays.sort(roster);
	}
	
	public Student[] getAbbreviatedRoster()
	{
		return abbreviatedRoster;
	}
}