package by.kovalenko.periodicals.entities;

import java.sql.Date;

public class Subscription {
	private Date startDate;
	private Date finishDate;
	private long id;
	private Boolean paid;
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

	@Override
	public int hashCode() {
		return (int) (33 * (id + price)
				+ ((startDate == null) ? 0 : startDate.hashCode()) + +((finishDate == null) ? 0
					: finishDate.hashCode()));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass()) {
			Subscription temp = (Subscription) obj;
			return (this.id == temp.id && this.startDate.equals(temp.startDate)
					&& this.finishDate.equals(temp.finishDate)
					&& this.paid.equals(temp.paid) && this.price == temp.price);
		} else
			return false;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", startDate.toString(),
				finishDate.toString());
	}

}
