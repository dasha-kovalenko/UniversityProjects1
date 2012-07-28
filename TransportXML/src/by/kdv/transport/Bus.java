package by.kdv.transport;


public class Bus extends Transport{

	private int amountOfFuel;

	public Bus(){}
	
	public Bus(int capacity, int number, int startHour, int startMinute,
						int finishHour, int finishMinute, String route, int value,
						int interval){
		super(capacity, number, startHour, startMinute, finishHour, finishMinute, route, value, interval);

	}

	public int getAmountOfFuel() {
		return amountOfFuel;
	}

	public void setAmountOfFuel(int amountOfFuel) {
		this.amountOfFuel = amountOfFuel;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s %s. Fuel Consumption is %d","Bus ", super.toString(), this.amountOfFuel);
	}
	

}
