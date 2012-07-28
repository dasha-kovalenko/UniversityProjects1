package transport;

public class FuelTransport extends PassTransport {

	public FuelTransport(int number, String startTime, String endTime,
			String route, int value, int interval) {
		super(number, startTime, endTime, route, value, interval);
	}
		
	@Override
	public String show() {
		String str = super.show();
		return String.format("Fuel Transport: %s", str, null);
	}
	

}
