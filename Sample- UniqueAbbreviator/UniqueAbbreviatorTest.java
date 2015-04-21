import java.util.*;

public class UniqueAbbreviatorTest
{
	public static void main (String [] args)
	{
		Student [] students = new Student[8];
		
		students[0] = new Student("John", "Smith");
		students[1] = new Student("Joh", "Smith");
		students[2] = new Student("Jal", "Smith");
		students[3] = new Student("Sonya", "Blade");
		students[4] = new Student("Jacob", "Smalls");
		students[5] = new Student("Jeff", "Smalls");
		students[6] = new Student("Drew", "Smallssaefawfasdawdaefa");
		students[7] = new Student("Drewstuff", "Smallssaefawfasdawdaefa");
		
		UniqueAbbreviator un = new UniqueAbbreviator();
		System.out.println(Arrays.toString(un.getAbbreviatedNameArray(students)));
		
	}
}