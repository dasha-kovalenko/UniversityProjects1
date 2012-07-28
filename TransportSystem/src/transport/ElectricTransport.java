package transport;


public class ElectricTransport extends PassTransport{
	
	public ElectricTransport(int number, String startTime, String endTime, String route,
			int value, int interval) {
		super(number, startTime, endTime, route, value, interval);
	}
	

	@Override
	public String show() {
		String str = super.show();
		return String.format("Electric Transport: %s", str);
	}
	
	
	
}
