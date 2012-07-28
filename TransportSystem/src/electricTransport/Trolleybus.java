package electricTransport;

import transport.ElectricTransport;;

public class Trolleybus extends ElectricTransport {
	
	private static final int energy = 1500;
	
	public Trolleybus(int number, String startTime, String endTime, String route,
			int value, int interval) {
		super(number, startTime, endTime, route, value, interval);
		Trolleybus.setCapacity(190);
	}
	
	@Override
	public String show() {
		String str = super.show();
		return String.format("%s %d. Energy Consumption is %d",str, Trolleybus.getCapacity(), Trolleybus.energy);
	}
	
	
	
}
