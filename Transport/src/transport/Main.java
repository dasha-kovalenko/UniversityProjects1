package transport;

import java.util.ArrayList;
import java.util.Map;

import command.Command;
import command.CommandSortByRoute;
import command.CommandSortByTime;
import actions.Actions;
import factory.TransportFactory;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Transport t1 = TransportFactory.getClassFromFactory("bus",50,1,5,0,1,0,"A-C-B",650,10);
		((Bus)t1).setAmountOfFuel(50);
		Transport t2 = TransportFactory.getClassFromFactory("bus",50,38,5,20,1,10,"A-K",600,20);
		((Bus)t2).setAmountOfFuel(60);
		Transport t3 = TransportFactory.getClassFromFactory("trolleybus",60,2,4,57,1,10,"A-K-L-B",400,12);
		((Trolleybus)t3).setEnergy(1000);
		Transport t4 = TransportFactory.getClassFromFactory("trolleybus",80,9,5,15,00,55,"A-B-J-Y-O-P",1000,15);
		((Trolleybus)t4).setEnergy(1500);
		Transport t5 = TransportFactory.getClassFromFactory("trolleybus",90,11,4,57,1,15,"A-Q-K-G",500,12);
		((Trolleybus)t3).setEnergy(1000);

		ArrayList<Transport> transports = new ArrayList<Transport>();
		transports.add(t1);
		transports.add(t2);
		transports.add(t3);
		transports.add(t4);
		transports.add(t5);

		//CommandComparator c1 = new TimeComparator();
		//Collections.sort(transports, c1.execute());
		//���������� � ������� ������� Command
		Command com = new CommandSortByTime();
		((CommandSortByTime)com).setList(transports);
		com.execute();
		System.out.println("After sort according to time: \r\n" + transports.toString()+"\r\n");
		//Collections.sort(transports, new RouteComparator());
		Command com1 = new CommandSortByRoute();
		((CommandSortByRoute)com1).setList(transports);
		com1.execute();

		System.out.println("After sort according to the length of the route: \r\n" + transports.toString()+"\r\n");
		
		Actions actions = new Actions();
		actions.setTransports(transports);
		for(Transport t : transports)
			System.out.println(String.format("Whole value is %d for the %s", actions.countWholeValue(t),t.toString()));
		
		System.out.println();
		Map<String, Integer> resMap = actions.findRoutes("A", "B");
		if(resMap.isEmpty())System.out.println("No routes are found!");
		else {
/*			Set<String> set = resMap.keySet();
			System.out.println("Found Routes are:\r\n" + set.toString()+"\r\n");
			Collection<Integer> values = resMap.values();
			System.out.println("Their values are:\r\n" + values.toString() + "\r\n");
			System.out.println(actions.getTransports().toString());
	
			System.out.println(actions.findValues(400, 500).toString());
*/		}
	}
}
