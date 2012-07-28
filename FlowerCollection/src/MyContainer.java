import java.util.*;

public class MyContainer<T extends Flower> extends ArrayList<T> {
	public MyContainer(ArrayList<T> a) {
		super(a);
	}
	public MyContainer(){}
	MyComparator comp = new MyComparator();
	public ArrayList<T> sort(){
		ArrayList<T> ar = new ArrayList<T>(this);
		Collections.sort(ar,comp);
		return ar;
	}
	/*public void addF(Flower f){
		ArrayList<Object> a=new ArrayList<Object>();
		a.add(f.getPicture());
		a.add(f.getName());
		a.add(f.getTemperature());
		a.add(f.getOrigin());
		
	}*/
	/*public void set(int col, Object value){
		this.set(col, value);
	}
	public T elementAt(int c){
		return this.get(c);
		
	}*/
}
