package fuelTransport;

import transport.FuelTransport;


public class Bus extends FuelTransport {

	private final static int amountOfFuel = 50;
	
	public Bus(int number, String startTime, String endTime,
			String route, int value, int interval){
		super(number, startTime, endTime, route, value, interval);
		Bus.setCapacity(130);
	}

	@Override
	public String show() {
		String str = super.show();
		return String.format("%s %d. Fuel Consumption is %d", str, RouteTaxi.getCapacity(),Bus.amountOfFuel);
	}
	
	
}
