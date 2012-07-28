package by.kdv.transport;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Transport {

	private int capacity;
	private int number;
	// private String startTime;
	// private String endTime;
	private Date start;
	private Date finish;
	private String route;
	private int price;
	private int interval;

	public Transport() {
	}

	protected Transport(int capacity, int number, int startHour,
			int startMinute, int finishHour, int finishMinute, String route,
			int value, int interval) {
		super();
		this.capacity = capacity;
		this.number = number;
		this.route = route;
		this.price = value;
		this.interval = interval;
		GregorianCalendar calendar = new GregorianCalendar(2012,
				Calendar.APRIL, 03, startHour, startMinute);
		this.start = calendar.getTime();
		calendar = new GregorianCalendar(2012, Calendar.APRIL, 03, finishHour,
				finishMinute);
		this.finish = calendar.getTime();
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public int getValue() {
		return price;
	}

	public void setValue(int value) {
		this.price = value;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	// стоимость поездки по опред. маршруту
	public int countPrice() {
		return (route.length() / 2) * price;
	}

	@Override
	public String toString() {
		return String
				.format("#%d begins to move in %s and finishes in %s through the route \"%s\". The "
						+ "fare is %d and the driving range is %d minutes. It's capacity is %d",
						this.number,
						(DateFormat.getTimeInstance(DateFormat.MEDIUM)
								.format(this.start)),
						DateFormat.getTimeInstance(DateFormat.MEDIUM).format(
								this.finish), this.route, this.price,
						this.interval, this.capacity);

	}

}
