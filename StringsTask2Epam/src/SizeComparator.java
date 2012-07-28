import java.util.Comparator;


public class SizeComparator implements Comparator<Sentence> {

	@Override
	public int compare(Sentence s1, Sentence s2) {
		// TODO Auto-generated method stub
		if(s1.size() < s2.size()) return -1;
		else if(s1.size() == s2.size()) return 0;
		return 1;
	}

}
