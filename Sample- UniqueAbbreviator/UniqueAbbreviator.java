import java.util.*;

public class UniqueAbbreviator
{
	
	public String [] getAbbreviatedNameArray(Student [] sArray)
	{
		String [] resultArray = new String[sArray.length];
		Arrays.sort(sArray);
		
		attemptToGetUniqueNamesWithFirstThreeLettersOfFirstName(sArray);
		generateAbbreviatedNameWithCompleteFirstName(sArray);
		
		for (int i=0; i< sArray.length; i++)
		{
			resultArray[i] = sArray[i].getAbbreviatedName();
		}
		return resultArray;
	}
	
	public void attemptToGetUniqueNamesWithFirstThreeLettersOfFirstName(Student [] arr)
	{
		for (int i =0; i<arr.length -1; i++)
		{
			while(arr[i].getAbbreviatedName().equals(arr[i+1].getAbbreviatedName()) && i< arr.length -2)
			{
				arr[i].setAbbreviatedName(arr[i].getFirstName().substring(0,3) + arr[i].getLastName());
				arr[i+1].setAbbreviatedName(arr[i+1].getFirstName().substring(0,3) + arr[i+1].getLastName());
				i++;
			}
			
		}
	}
	
	public void generateAbbreviatedNameWithCompleteFirstName(Student [] arr)
	{
		for (int i =0; i<arr.length -1; i++)
		{
			while(arr[i].getAbbreviatedName().equals(arr[i+1].getAbbreviatedName()) && i< arr.length -2)
			{
				arr[i].setAbbreviatedName(arr[i].getFirstName() + arr[i].getLastName());
				arr[i+1].setAbbreviatedName(arr[i+1].getFirstName() + arr[i+1].getLastName());
				i++;
			}
		}
	}
}