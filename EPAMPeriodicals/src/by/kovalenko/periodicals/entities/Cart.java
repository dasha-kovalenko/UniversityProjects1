package by.kovalenko.periodicals.entities;

import java.util.List;

public class Cart {
	private long id;
	private List<Edition> editions;

	public Cart() {
		super();
	}

	public Cart(long id) {
		super();
		this.id = id;
	}

	public Cart(List<Edition> eds) {
		this.editions = eds;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Edition> getEditions() {
		return editions;
	}

	public void setEditions(List<Edition> editions) {
		this.editions = editions;
	}

	@Override
	public int hashCode() {
		return (int) (37 * id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass()) {
			Cart temp = (Cart) obj;
			return (this.id == temp.id && this.editions.equals(temp.editions));
		} else
			return false;
	}

	@Override
	public String toString() {
		return String.format("Cart: %s", editions.toString());
	}

}
