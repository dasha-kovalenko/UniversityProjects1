package by.kdv.transport;

public class Trolleybus extends Transport{
	
	private int energy;

	public Trolleybus(){}
	public Trolleybus(int capacity, int number, int startHour, int startMinute,
			int finishHour, int finishMinute, String route, int value,
			int interval){
		super(capacity, number, startHour, startMinute, finishHour, finishMinute, route, value, interval);

}


	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s %s. Energy Consumption is %d","Trolleybus ", super.toString(), this.energy);

	}

	
	
}
