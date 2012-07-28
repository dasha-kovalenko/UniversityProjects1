import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

class Task{
	Integer num;
	Integer load;
	Integer run;
	public Task(Integer i, Integer l, Integer r){
		num = i;
		load = l;
		run = r;
	}
	@Override
	public String toString() {
		return String.format("%d", num);
	}
	
}

class MyComparator implements Comparator<Task>{

	@Override
	public int compare(Task t1, Task t2) {
		// TODO Auto-generated method stub
		if(t1.run!=t2.run)
			return t2.run.compareTo(t1.run);
		return t2.load.compareTo(t1.load);
	}
	
}
public class Solution {

	static int N = 4;
	static int R = 0;
	static ArrayList<Task> arr = new  ArrayList<Task>(N);

	private static void swap(int p1, int p2) {
		if(p1>p2){
			int tmp = p2;
			p2 = p1;
			p1 = tmp;
		}
	}
	public static int f(ArrayList<Task> arr){
		int P1=0; 
		int P2=0;
		int S=0;
		for (int i = 0; i < N; i++) {
			if (P1>P2){
				int tmp = P2;
				P2 = P1;
				P1 = tmp;
			}
			int start = Math.max(P1,S);
			S = start + arr.get(i).load;
			P1 = S + arr.get(i).run;
		}

		int res = Math.max(P1,P2);
		
		return res;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader r = new BufferedReader(new FileReader("input.txt"));
		N = Integer.parseInt(r.readLine());
		arr = new ArrayList<Task>(N);
		for(int i = 0; i < N; i++){
			StringTokenizer stk = new StringTokenizer(r.readLine(), " ");
			while(stk.hasMoreTokens()){
				int l = 0;
				int q = 0;
				l = Integer.parseInt(stk.nextToken());
				q = Integer.parseInt(stk.nextToken());
				Task t = new Task(i+1,l,q);
				arr.add(t);
			}
		}
		Collections.sort(arr, new MyComparator());
		R = f(arr);
		PrintWriter w = new PrintWriter("output.txt");
		w.write(R + "\r\n");
		for(int i = 0; i < N; i++){
			if(arr.get(i) != null)
				w.write(arr.get(i).num + " ");
		}
		w.close();
	}
}

