package by.kovalenko.compareStrings;

class A {
	A(int i) {
		System.out.print("Конструктор A");
	}
}

public class Quest extends A {
	public static void main(String[] args) {
		Quest s = new Quest();
		// 1
	}

	public Quest() {
		super(1);
	}

	public void show() {
		// 3
	}
}
