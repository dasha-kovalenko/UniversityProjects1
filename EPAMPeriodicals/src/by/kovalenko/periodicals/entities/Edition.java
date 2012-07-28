package by.kovalenko.periodicals.entities;

public class Edition {
	private String title;
	private String description;
	private double price;
	private long id;

	public Edition() {
		super();
	}

	public Edition(String title, String description, double price, long id) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
		this.id = id;
	}

	public Edition(String title, String description, double price) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return (int) (33 * (id + price)
				+ ((title == null) ? 0 : title.hashCode()) + +((description == null) ? 0
					: description.hashCode()));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass()) {
			Edition temp = (Edition) obj;
			return (this.id == temp.id && this.title.equals(temp.title));
		} else
			return false;
	}

	@Override
	public String toString() {
		return String.format("%s", title);
	}

}
