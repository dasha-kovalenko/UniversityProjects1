import java.util.Comparator;

public class MyComparator<T extends Flower> implements Comparator<T> {
	public MyComparator() {}

	@Override
	public int compare(T f1, T f2) {
		//return f1.getName().compareTo(f2.getName());
		return 0;
	}

	
}

