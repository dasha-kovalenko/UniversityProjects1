package fuelTransport;

import transport.FuelTransport;

public class RouteTaxi extends FuelTransport {
	
	private final static int amounOfFuel = 100;
	
	public RouteTaxi(int number, String startTime, String endTime, String route,
			int value, int interval) {
		super(number, startTime, endTime, route, value, interval);
		RouteTaxi.setCapacity(30);
	}

	@Override
	public String show() {
		String str = super.show();
		return String.format("%s %d. Fuel Consumption is %d", str, Bus.getCapacity(),RouteTaxi.amounOfFuel);
	}
	
	
	
	
}
