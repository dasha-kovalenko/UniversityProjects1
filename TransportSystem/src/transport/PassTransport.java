package transport;
import java.util.StringTokenizer;


public class PassTransport {
	
	private static int capacity;

	private int number;
	private String startTime;
	private String endTime;
	private String route;
	private int value;
	private int interval;
	
	protected PassTransport(int number, String startTime, String endTime,
			String route, int value, int interval) {
		this.number = number;
		this.startTime = startTime;
		this.endTime = endTime;
		this.route = route;
		this.value = value;
		this.interval = interval;
	}

	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}


	public static int getCapacity() {
		return capacity;
	}

	public static void setCapacity(int capacity) {
		PassTransport.capacity = capacity;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public double getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	
	public String show(){
		String className = this.getClass().toString();
		StringTokenizer st = new StringTokenizer(className,".");
		st.nextToken();
		return String.format("%s #%d begins to move in %s and finishes in %s through the route \"%s\". The " +
				"fare is %d and the driving range is %d minutes. It's capacity is", st.nextToken(), this.number, this.startTime,
				this.endTime, this.route, this.value, this.interval);
		
	}
}
