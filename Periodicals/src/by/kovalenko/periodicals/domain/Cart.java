package by.kovalenko.periodicals.domain;

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
	public Cart(List<Edition> eds){
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
	
}
