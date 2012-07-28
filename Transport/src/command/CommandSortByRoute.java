package command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import transport.Transport;

public class CommandSortByRoute extends Command {

	private Comparator<Transport> c;
	private ArrayList<Transport> list;
	
	public CommandSortByRoute() {
		super();
		// TODO Auto-generated constructor stub
		c = new Comparator<Transport>() {

			@Override
			public int compare(Transport t1, Transport t2) {
				// TODO Auto-generated method stub
				if(t1.getRoute().length() < (t2.getRoute().length()))return -1;
				else if(t1.getRoute().length() == (t2.getRoute().length()))return 0;
				else return 1;
			}
		};
		list = new ArrayList<Transport>();
	}

	
	public void setList(ArrayList<Transport> list) {
		this.list = list;
	}

	@Override
	public void execute() {
		Collections.sort(list, c);
	}

}
