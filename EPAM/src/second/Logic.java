package second;

public class Logic {
public static void main(String[] args) {
	Course objA = new BaseCourse();
	BaseCourse objB = new BaseCourse();
	System.out.println("objA: id=" + objA.id);
	System.out.println("objB: id=" + objB.id);
	System.out.println("      id=" + ((Course)objB).id);
	//Course objC = new Course();
	}
}
