import java.util.ArrayList;

import java.io.*;
public class Example {

	/**
	 * @param args
	 */
	static ArrayList <Double> a = new ArrayList<Double>();
	final static Integer SCALE = 1000;
	final static Integer SIZE = 100;
	private static int[]counts = new int[7];
	public static void getStatistics(){
		try {
		PrintWriter pw = new PrintWriter(new File("output.txt"));
		for(int i = 1; i < counts.length; i++)
			pw.write((String.format("%d: %d\r\n", i, counts[i])));
		pw.close();

		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		  }
		}
	public static void main(String[]args){
		
		// TODO Auto-generated constructor stub
		 for (int i = 0; i < SIZE; i++) {
			 counts[1]++;		
			a.add(Math.random()*SCALE);
		}
		 System.out.println("1: "+counts[1]);
		int x = 5;
		 while (x > 0){
			 counts[2]++;		
			System.out.println(x--);
		}
		 System.out.println("2: "+counts[2]);
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
			 System.out.println("3: "+counts[3]);
			 System.out.println("4: "+counts[4]);
		
			 System.out.println("5: "+counts[5]);
			 do {
				 counts[6]++;			
				x++;
				System.out.println(x);
			}
			while(x<10);
			 System.out.println("6: "+counts[6]);

		}
		 getStatistics();
	
}

}
