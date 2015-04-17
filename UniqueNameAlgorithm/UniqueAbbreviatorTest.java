import java.lang.Thread;

public class UniqueAbbreviatorTest
{
	public static void main(String[] args)
	{
		String[] names = {"Hilyer, Codem", "Hilyer, Cody", "Roberts, Jack", "Roberts, Jonathan"};
		UniqueAbbreviator unqAbb = new UniqueAbbreviator(names);
		
		try{
			Thread.sleep(10000);
		}
		catch(Exception e)
		{
			System.out.println("yee");
		}
		
		Student[] abbreviatedStudents = unqAbb.getAbbreviatedRoster();
		
		
		
		for (int i = 0; i < abbreviatedStudents.length; i++)
		{
			System.out.println(abbreviatedStudents[i].getFirstName()+" "+abbreviatedStudents[i].getLastName());
		}
	}
}