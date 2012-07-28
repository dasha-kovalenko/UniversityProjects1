package by.kovalenko.periodicals.domain;

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

}
