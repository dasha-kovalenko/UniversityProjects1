package first;

/* ������ # 7 : ��������� ����� � �������� : ComparingStrings.java */
public class Department {
	{
		System.out.println("logic (1) id=" + this.id);
	}
	static {
		System.out.println("static logic");
	}
	private int id = 7;

	public Department(int d) {
	id = d;
	System.out.println("����������� id=" + id);
	}
	int getId() {
		return id;
	}
	{
	id = 10;
	System.out.println("logic (2) id=" + id);
	}

	public static void main (String[] args){
	Department obj = new Department(71);
	System.out.println("�������� id=" + obj.getId());}}
	 // public static void main(String[] args) {
/*		  int i = 3; 
			byte b = 1, b1 = 1 + 2;
	//b = b1 + 1; //������ ���������� �����
			Long l = 2L;
			Float f  = Float.valueOf(l);
			l = Long.valueOf(f);
			b = (byte)(b1 + 1);//0 
			System.out.println(b);
	//b = -b; //������ ���������� �����
			b = (byte)-b;//1 
			System.out.println(b);
	//b = +b1; //������ ���������� �����
			b = (byte)+b1; //2 
			System.out.println(b);*/
/*		  float f1 = 10.01f;

		  Float f = f1; //double � Float 
		  f = f1;
		  String s1 = Float.toString(0f); //float � String 
		  String s2 = String.valueOf(f); //Float � String 
		  Byte b = Byte.valueOf("120"); //String � Byte 
		  double d = b.doubleValue(); // Byte � double 
		  short s = (short) d; // double � short 
		  Character ch = new Character('3');	 
		  int i = Character.digit(ch.charValue(), 4); 
		  System.out.println("s1=" + s1 + ", s2=" + s2); 
		  System.out.print("b=" + b + ", s=" + s + ", d=" + d + ", i=" + i); 
		}*/

/*		  System.out.println("5%1=" + 5%1 + "  5%2=" + 5%2); 
			int b1 = 0xe;//14 ��� 1110 
			int b2 = 0x9;//9  ��� 1001 
			int i = 0; 
		System.out.println(b1 + "|" + b2 + " = " + (b1|b2)); 
		System.out.println(b1 + "&" + b2 + " = " + (b1&b2)); 
		System.out.println(b1 + "^" + b2 + " = " + (b1^b2)); 
		System.out.println(     "~" + b2 + " = " + ~b2); 
		System.out.println(b1 + ">>" + ++i + " = " + (b1>>i)); 
		System.out.println(b1 + "<<" + i + " = " + (b1<<i++)); 
		System.out.println(b1 + ">>>" + i +" = " + (b1>>>i)); */

//	  }
//}
 
