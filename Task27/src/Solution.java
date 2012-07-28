import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;


public class Solution {
	static int n = 0, res = 0;
	static Vector<Integer> xS = new Vector<Integer>();
	static Vector<Integer> xP = new Vector<Integer>();
	
	static ArrayList<Integer> R = new ArrayList<Integer>();
	static ArrayList<Integer> A = new ArrayList<Integer>();
	
	/**static Integer[] A = new Integer[10];
	static Integer[] R = new Integer[10];*/

	//Vector<Boolean> use;
	static Boolean[] use = new Boolean[15];

	public static void gen(int pos, int P1,int P2, int S) {
		int cur_res = Math.max(S,Math.max(P1,P2));
		if (cur_res>=res) return;
		if (pos==n) {
			res = cur_res;
			R = A;
			for(int i = 0; i < n; i++){
				System.out.print(R.get(i) +  1 + " ");
			}
			System.out.println();
			return;
		}
		if (P1>P2) {
			int tmp = P1;
			P1 = P2;
			P2 = tmp;
			//swap(P1,P2);
		}

		int t1=0,t2=0,t4=Integer.MAX_VALUE;
		for (int i = 0; i<n; i++)
			if (!use[i]) {
				//System.out.println(xS.toString());
				int s1 = xS.get(i);
				int p1 = xP.get(i);
				t1 = Math.max (t1, s1+p1);
				t2 = t2 + s1;
				t4 = Math.min(t4,p1);
			}
		if (S+t2+t4>=res) return; 
		if (P1+t1>=res) return;

		int RP1=P1, RP2=P2, RS=S;
		for (int i=0; i<n; i++) 
		if (!use[i]){
			int pp = i;
			use[pp] = true;
			
			A[pos] = pp;
			int start = Math.max(RP1,RS);
			S = start + xS.get(pp);
			P1 = S + xP.get(pp);
			gen(pos+1,P1,P2,S);
			use[pp] = false;
		}
	}

	public static int solve(String fin, String fout) throws Exception {
		xS.clear();
		xP.clear();
		res = 0;
		//ifstream in(fin.c_str());
		//in>>n;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fin));
			n = Integer.parseInt(reader.readLine());
			
			
			for (int i=0; i<n; i++) {
				StringTokenizer st = new StringTokenizer(reader.readLine(), " ");
				Integer s,p;
				while(st.hasMoreTokens()){
					s = Integer.parseInt(st.nextToken());
					p = Integer.parseInt(st.nextToken());
					xS.add(s);
					xP.add(p);
					res+=s+p;
				}
			}
			reader.close();
		}
			catch(Exception e){}
			
		for(int i = 0; i < n; i++)
			use[i] = false;
		
		gen(0,0,0,0);

		//ofstream out(fout.c_str());
		//out<<res<<"\n";
		//try {
		//System.out.println(R.toString());
			PrintWriter writer = new PrintWriter("output.txt");
			System.out.println(res);
			writer.write(res+"\r\n");
			for (int i = 0; i < n; i++){
				writer.write(R[i] + 1);
				System.out.print(R[i] + 1);
			}
			
			writer.write("\r\n");
			writer.close();
			//}
			
		/**} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return 0;
	}

	public static void main(String[] args) throws Exception {
		//try {
			solve("input.txt","output.txt");
	/**	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
