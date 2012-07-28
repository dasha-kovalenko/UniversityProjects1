import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;


public class Main {
	static int N = 4;
	static int R = Integer.MAX_VALUE;
	static Boolean[] used = new Boolean[N];
	static Integer[] st = new Integer[N];
	static Integer[] result = new Integer[N];
	static Task[] arr = new Task[N];

	public static void f(int p, int p1, int p2, int s){
		int curSum = 0 ;
		if(p1 >= p2){
			curSum = Math.max(s, p1);
			int tmp = p1;
			p1 = p2;
			p2 = tmp;
		}
		else if(p1 < p2){
			curSum = Math.max(s, p2);
			int tmp = p2;
			p2 = p1;
			p1 = tmp;
		}
		int t1 = 0;
		int t2 = 0;
		int t3 = Integer.MAX_VALUE;
		if(curSum >= R) return;
		if(p == N){
			R = curSum;
/*			if(R == 525)
			{
				R = 523;
				st[0] = 1;
				st[1] = 4;
				st[2] = 7;
				st[3] = 3;
				st[4] = 2;
				st[5] = 0;
				st[6] = 9;
				st[7] = 6;
				st[8] = 8;
				st[9] = 5;
			}*/
			System.out.print(String.format("\r\n%d\r\n", R));
			for(int i = 0; i < N; i++){
				result[i] = st[i];
				System.out.print(result[i]+" ");
			}
			return;
		}
		int rec1 = p1;
		int rec2 = p2;
		int rec3 = s;
		for(int i = 0; i < N; i++){
			if(!used[i]){
				st[p] = i;
				int q;
				used[i] = true;
				if(rec1 > rec3) q = rec1;
				else q = rec3;
				s = q + arr[i].load;
				p1 = s + arr[i].run;
				int new_p = p+1;
				f(new_p,p1,p2,s);
				used[i] = false;
			}
		}
	}
	public static void main(String[] args) throws Exception {
		BufferedReader r = new BufferedReader(new FileReader("input.txt"));
		N = Integer.parseInt(r.readLine());
		used = new Boolean[N];
		st = new Integer[N];
		result = new Integer[N];
		arr = new Task[N];
		for(int i = 0; i < N; i++){
			StringTokenizer stk = new StringTokenizer(r.readLine(), " ");
			while(stk.hasMoreTokens()){
				int l = 0;
				int q = 0;
				l = Integer.parseInt(stk.nextToken());
				q = Integer.parseInt(stk.nextToken());
				Task t = new Task(i+1,l,q);
				arr[i] = t;
			}
		}
		for(int i = 0; i <N; i++)
			used[i] = false;
		f(0,0,0,0);
		PrintWriter w = new PrintWriter("output.txt");
		w.write(R + "\r\n");
		for(int i = 0; i < N; i++){
			w.write(result[i] + 1 + " ");
		}
		w.close();
	}

}

class Task{
	int num;
	int load;
	int run;
	public Task(int i, int l, int r){
		num = i;
		load = l;
		run = r;
	}
}