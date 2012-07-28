package command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import transport.Transport;

public class CommandSortByTime extends Command {

	private Comparator<Transport> c;
	private ArrayList<Transport> list;
	
	public CommandSortByTime() {
		super();
		c = new Comparator<Transport>() {

			@Override
			public int compare(Transport t1, Transport t2) {
				// TODO Auto-generated method stub
				if(!t1.getStart().equals(t2.getStart()))
					return (t1.getStart().compareTo(t2.getStart()));
				else return t2.getFinish().compareTo(t1.getFinish());

			}
		};
		list = new ArrayList<Transport>();
	}

	
	public ArrayList<Transport> getList() {
		return list;
	}


	public void setList(ArrayList<Transport> list) {
		this.list = list;
	}


	@Override
	public void execute() {
		Collections.sort(list, c);
	}

}
