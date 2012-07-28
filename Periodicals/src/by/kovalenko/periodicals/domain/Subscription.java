package by.kovalenko.periodicals.domain;

import java.sql.Date;

public class Subscription {
	private Date startDate;
	private Date finishDate;
	private long id;
	private boolean paid;
	private long userId;
	private double price;

	public Subscription(Date startDate, Date finishDate, long id, boolean paid,
			long userId, double price) {
		super();
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.id = id;
		this.paid = paid;
		this.userId = userId;
		this.setPrice(price);
	}

	public Subscription() {
		super();
	}

	public Subscription(Date startDate, Date finishDate, boolean paid,
			long userId, double price) {
		super();
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.userId = userId;
		this.paid = paid;
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
