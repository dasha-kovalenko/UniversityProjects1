package comparator;

import java.util.Comparator;
import transport.Transport;

public class RouteComparator implements Comparator<Transport> {

	@Override
	public int compare(Transport t1, Transport t2) {
		// TODO Auto-generated method stub
		if(t1.getRoute().length() < (t2.getRoute().length()))return -1;
		else if(t1.getRoute().length() == (t2.getRoute().length()))return 0;
		else return 1;
	}
}
