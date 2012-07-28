package comparator;

import java.util.Comparator;
import transport.Transport;

public class TimeComparator implements Comparator<Transport>{

	@Override
	public int compare(Transport t1, Transport t2) {
		// TODO Auto-generated method stub
		if(!t1.getStart().equals(t2.getStart()))
			return (t1.getStart().compareTo(t2.getStart()));
		else return t2.getFinish().compareTo(t1.getFinish());
/*		if(t1.getStart().before(t2.getStart()))return -1;
		else if(t1.getStart().equals(t2.getStart()))return 0;
		else return 1;
*/	
	}


}
