import java.util.ArrayList;


public class ForExample {

	/**
	 * @param args
	 */
	static ArrayList <Double> a = new ArrayList<Double>();
	final static Integer SCALE = 1000;
	final static Integer SIZE = 100;
	public static void main(String[]args){
		// TODO Auto-generated constructor stub
		for (int i = 0; i < SIZE; i++) {
			a.add(Math.random()*SCALE);
		}
		int x = 5;
		while (x > 0){
			System.out.println(x--);
		}
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				while(i<j) {  
					a.add(i*1.5);
					i++;
				}
				
			}
		
			do {
				x++;
				System.out.println(x);
			}
			while(x<10);
		}
	}
	
}
