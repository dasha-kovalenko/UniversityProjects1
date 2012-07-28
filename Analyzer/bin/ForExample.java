import java.io.*;
import java.util.ArrayList;


public class ForExample {
		private static int[]counts = new int[7];
			public static void getStatistics(){
		MyFrame frame = new MyFrame(counts);
	}

	/**
	 * @param args
	 */
	static ArrayList <Double> a = new ArrayList<Double>();
	final static Integer SCALE = 1000;
	final static Integer SIZE = 100;
	public static void main(String[]args){
		// TODO Auto-generated constructor stub
		 for (int i = 0; i < SIZE; i++) {
			 counts[1]++;		
			a.add(Math.random()*SCALE);
		}
		int x = 5;
		 while (x > 0){
			 counts[2]++;		
			System.out.println(x--);
		}
		 for(int i = 0; i < SIZE; i++) {
			 counts[3]++;		
			 for(int j = 0; j < SIZE; j++) {
				 counts[4]++;			
				   while(i<j) {
					   counts[5]++;				  
					a.add(i*1.5);
					i++;
				}
				
			}
		
			 do {
				 counts[6]++;			
				x++;
				System.out.println(x);
			}
			while(x<10);
		}
	}
	
}
