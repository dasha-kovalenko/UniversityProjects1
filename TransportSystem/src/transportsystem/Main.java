package transportsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import actions.*;
import electricTransport.*;
import fuelTransport.*;
import transport.PassTransport;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<PassTransport> trans = new ArrayList<PassTransport>();
		
		trans.add(new RouteTaxi(30,"05:41","01:00","A-C-D-E-G-B",1000,20));
		trans.add(new RouteTaxi(31,"05:43","01:10","A-D-B",2250,10));
		trans.add(new Tram(20,"06:01","23:50","A-B",1000,12));
		trans.add(new Tram(21,"06:12","23:45","A-M",1040,14));
		trans.add(new Trolleybus(10,"04:57","01:10","A-K-L-B",400,12));
		trans.add(new Trolleybus(11,"04:59","01:21","A-P-C",1000,14));
		trans.add(new Bus(1,"05:00","01:00","A-C-B",650,10));
		trans.add(new Bus(2,"05:05","01:10","A-D-B",630,15));
		Actions actions = new Actions();
		actions.setAutoPark(trans);
		List<String> routes;
		routes = actions.findRoutes("A", "B");
		ArrayList<Integer> nums = actions.getIndexes();
		actions.showResults();
		
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter startPoint: ");
        String startPoint = sc.nextLine();
        System.out.println("Enter endPoint:");
        String endPoint = sc.nextLine();

        routes.clear();
        nums.clear();
		routes = actions.findRoutes(startPoint, endPoint);
		nums = actions.getIndexes();
		actions.showResults();
	}
}
