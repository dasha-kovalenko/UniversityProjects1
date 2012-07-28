package second;

public class GenericMethod {
	public static <T extends Number> byte asByte(T num) {
		long n = num.longValue();
		if (n >= -128 && n <= 127) return (byte)n;
		else return 0;
	}	
	public static void main(String [] args) {
		System.out.println(asByte(7));
		System.out.println(asByte(new Float("7.f")));
		// System.out.println(asByte(new Character('7'))); 
		// ошибка компиляции
	}
}

