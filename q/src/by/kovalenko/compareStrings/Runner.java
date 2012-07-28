package by.kovalenko.compareStrings;

class Course {
	private int id;
	private String name;

	public Course(int i, String n) {
		id = i;
		name = n;
	}

	public String toString() {
		return "Название: " + name + "(" + id + ")";
	}
}

class BaseCourse extends Course {
	private int idTeacher;

	public BaseCourse(int i, String n, int it) {
		super(i, n);
		idTeacher = it;
	}

	public String toString() {
		/*
		 * просто toString() нельзя!!! метод будет вызывать сам себя, что
		 * приведет к ошибке во время выполнения
		 */
		return super.toString() + " препод.(" + idTeacher + ")";
	}
}

class OptionalCourse extends BaseCourse {
	private boolean required;

	public OptionalCourse(int i, String n, int it, boolean r) {
		super(i, n, it);
		required = r;
	}

	public String toString() {
		return super.toString() + " required->" + required;
	}
}

class DynDispatcher {
	public void infoCourse(Course c) {
		System.out.println(c.toString());
		// System.out.println(c);//идентично
	}
}

public class Runner {
	public static void main(String[] args) {
		DynDispatcher d = new DynDispatcher();
		Course cc = new Course(7, "MA");
		d.infoCourse(cc);
		BaseCourse bc = new BaseCourse(71, "МП", 2531);
		d.infoCourse(bc);
		OptionalCourse oc = new OptionalCourse(35, "ФА", 4128, true);
		d.infoCourse(oc);
	}
}
