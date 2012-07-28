package electricTransport;

import transport.ElectricTransport;

public class Tram extends ElectricTransport {
	
	private static final int energy = 1000;

	public Tram(int number, String startTime, String endTime, String route,
			int value, int interval) {
		super(number, startTime, endTime, route, value, interval);
		Tram.setCapacity(200);
	}

	@Override
	public String show() {
		String str = super.show();
		return String.format("%s %d. Energy Consumption is %d",str, Tram.getCapacity(), Tram.energy);
		
	}
	
	
	
}
