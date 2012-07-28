package by.kovalenko.compareStrings;

class Course1 {
	public int id = 71;

	public Course1() {
		System.out.println("конструктор класса Course");
		id = getId();// !!!
		System.out.println(" id=" + id);
	}

	public int getId() {
		System.out.println("getId() класса Course");
		return id;
	}
}

class BaseCourse1 extends Course1 {
	public int id = 90;// так делать не следует!

	public BaseCourse1() {
		System.out.println("конструктор класса BaseCourse");
		System.out.println(" id=" + getId());
	}

	public int getId() {
		System.out.println("getId() класса BaseCourse");
		return id;
	}
}

public class Logic {
	public static void main(String[] args) {
		// при создании объекта objA ссылка типа Course инициализируется
		// объектом типа BaseCourse
		Course1 objA = new BaseCourse1();
		BaseCourse1 objB = new BaseCourse1();
		System.out.println("objA: id=" + objA.id);
		System.out.println("objB: id=" + objB.id);
		@SuppressWarnings("unused")
		Course1 objC = new Course1();
	}
}
