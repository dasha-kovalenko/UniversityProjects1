
import java.io.*;
import java.util.*;

public class Main {
	static Stack <Task> s1 = new Stack <Task> ();
	static Stack <Task> s2 = new Stack <Task> ();
	static Stack<Integer> tmp = new Stack<Integer>();
	static int minSum = Integer.MAX_VALUE;
	static int R; static int F; static int sumT;
	static Stack<Time> stackT = new Stack<Time>();
	static boolean otsev = false;
	static Stack<Task> stack = new Stack<Task>();
	static Stack<Stack<Integer>> sT1 = new Stack<Stack<Integer>>();
	
	static void addTask(Task t, int k, int n, Stack<Task> stack) {
		Time time;
		if (stackT.isEmpty())
			time = new Time(0, 0, 0, 0, 0);
		else
			time = stackT.peek();
		Time q = new Time(time.time1, time.time2, time.lostTime1, time.lostTime2, time.serverTime);
		if(k == 0) {
			if(q.time1 < q.time2){ 
				k = 1;
				q.isFree1 = true;
			}
			else {
				k = 2;
				q.isFree2 = true;
			}
		}
		if (k == 1) {
			q.isFree1 = false;
			if (q.serverTime > q.time1) {
				q.lostTime1 += (q.serverTime - q.time1);
				q.time1 += (q.serverTime - q.time1);
			}
			else if (q.time1 > q.serverTime)
				q.serverTime = q.time1;
			q.time1 += t.load;
			q.time1 += t.run;
			q.serverTime += t.load;
			if(q.time2 < q.serverTime || q.time2 < q.time1) q.isFree2 = true;
			if(q.lostTime1 > R ) {
				otsev = true;
			}else {
				otsev = false;
			}

			if (stackT.size() == n-1 && q.time1 > q.time2) {
				q.lostTime2 += (q.time1 - q.time2);
				q.time2 = q.time1;
				if(sumT <= minSum && sumT != 0){
					minSum = sumT;
					for(int j = 0; j < stack.size(); j++){
						System.out.println(stack.get(j));
					}
					for(int i = 0; i < n; i++){
						tmp.push(stack.get(i).num);
					}
				}
				sumT = q.time1;
			}
			s1.add(t);
		}
		else if (k == 2) {
			q.isFree2 = false;
			if (q.serverTime > q.time2) {
				q.lostTime2 += (q.serverTime - q.time2);
				q.time2 += (q.serverTime - q.time2);
			}
			else if (q.time2 > q.serverTime)
				q.serverTime = q.time2;
			q.time2 += t.load;
			q.time2 += t.run;
			q.serverTime += t.load;
			if(q.time1 < q.serverTime || q.time1 < q.time2)
				q.isFree1 = true;
			if(q.lostTime2 > R) {
				otsev = true;
				return;
			} else {
				otsev = false;
			}

			if (stackT.size() == n-1 && q.time1 < q.time2) {
				q.lostTime1 += (q.time2 - q.time1);
				q.time1 = q.time2;
				if(sumT <= minSum && sumT != 0){
					minSum = sumT;
					
					for(int i=0; i<stack.size(); i++){
						tmp.add(stack.get(i).num);
					}
				}
				sumT  = q.time2;
			}
			s2.add(t);
		}
		F = Math.max(q.lostTime1, q.lostTime2);
		stackT.add(q);
		if (F > R){
			return;
		}
		
	}
	
	static void findTimetable(ArrayList<Task> a,
								int n,
								Stack<Task> stack,
								int visit,
								Stack<Stack<Integer>>s)
	{	
		Time q = new Time(0,0,0,0,0);
		if (!stackT.isEmpty()) {
			q = stackT.peek();
		}
		boolean isFound = false;
		if(stack.size() == n) {
			if (F < R) {
				F = Math.max(stackT.peek().lostTime1, stackT.peek().lostTime2);
				R = F;
			} else
				return;
			if (sumT > Math.max(stackT.peek().time1, stackT.peek().time2) || sumT == 0) {
				sumT = Math.max(stackT.peek().time1, stackT.peek().time2);
				sT1.clear();
				s.clear();
				if(!tmp.isEmpty()){
					sT1.add((Stack<Integer>)tmp.clone());
					s.add((Stack<Integer>) tmp.clone());
				}
			}
			else if(sumT == Math.max(stackT.peek().time1, stackT.peek().time2) || sumT == 0){
				if(!tmp.isEmpty()){
					s.add((Stack<Integer>) tmp.clone());
					sT1.add((Stack<Integer>)tmp.clone());
				}
			}
		}
		for(int i = visit+1; i < n; i++)
		{
			Task t1 = a.get(i);
			if((!(t1.isUsed)) && (visit != i))
			{
				stack.add(t1);
				int k = 0;
				if(s1.isEmpty()){
					k = 1;
				}
				else if(s2.isEmpty()){
					k = 2;
				}else if(q.isFree1){
					k = 1;
				}else if(q.isFree2){
					k = 2;
				}
				addTask(t1, k, n, stack);
				a.get(i).isUsed = true;
				isFound = true;
				visit = -1;
				break;
			}
			
		}
		if(!isFound)
		{
			tmp.clear();
			if(stack.isEmpty()) return;
			else
			{
				stack.peek().isUsed = false;
				Task t = stack.peek();
				visit = a.indexOf(t);
				stack.pop();
				stackT.pop();
				if (!stackT.isEmpty())
				{
					q = stackT.peek();
				}
			}
		}
		findTimetable(a, n, stack, visit, s);
		
	}
	public static void main(String[] args) throws Exception {
		ArrayList<Task> arr = new ArrayList<Task>();
		R = Integer.MAX_VALUE;
		F = 0;
		sumT = 0;
		int n = 0;
		BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
		n = Integer.parseInt(reader.readLine());
		for(int i=1; i<=n; i++){
			StringTokenizer stTok = new StringTokenizer(reader.readLine()," ");
			while(stTok.hasMoreTokens()){
				int a = Integer.parseInt(stTok.nextToken());
				int b = Integer.parseInt(stTok.nextToken());
				Task t = new Task(i,a,b);
				arr.add(t);
			}
		}
		reader.close();
		Collections.sort(arr, new MyComparator());
		Stack<Task> stack = new Stack<Task>();
		Stack<Stack<Integer>> s = new Stack<Stack<Integer>>();
		int visit = -1;
		findTimetable(arr, n, stack, visit,s);
		PrintWriter writer = new PrintWriter("output.txt");
		writer.print(minSum + "\r\n");
		Stack<Integer> stack1 = new Stack<Integer>();
		int size = 0;
		//while(!s.isEmpty()){
			stack1 = s.pop();
			size = stack1.size();
			for(Integer e: stack1)
				writer.print(e + " ");
			writer.print("\r\n");
		//}
		writer.close();
	}
}
class Task{
	int num; Integer load; int run;
	boolean isUsed;
	public Task(int n, int l, int r) {
		num = n; load = l; run = r; isUsed = false;
	}
	public String toString(){return "num = "+this.num+" "+"load = "+this.load+" "+"run = "+this.run;}
}
class Time{
	int time1; int time2; int lostTime1; int lostTime2; int serverTime;
	boolean isFree1 =  true; boolean isFree2 = true;
	Time(int t1, int t2, int lt1, int lt2, int st) {
		time1 = t1; time2 = t2; lostTime1 = lt1; lostTime2 = lt2; serverTime = st;
	}
}
class MyComparator implements Comparator<Task>{
	public MyComparator() {}
	public int compare(Task t1, Task t2) { return (t1.load).compareTo(t2.load);}
}

