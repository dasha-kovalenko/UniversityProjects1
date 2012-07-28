package by.kovalenko.periodicals.domain;

import java.util.List;

public class User {

	private long id;
	private String username;
	private String password;
	private boolean admin;
	private Cart cart;
	private String salt;
	private List<Subscription> subsciptions;

	public User() {
		super();
	}

	public User(long id, String userName, String password, boolean admin,
			String salt) {
		super();
		this.id = id;
		this.username = userName;
		this.password = password;
		this.admin = admin;
		this.salt = salt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Subscription> getSubsciptions() {
		return subsciptions;
	}

	public void setSubsciptions(List<Subscription> subsciptions) {
		this.subsciptions = subsciptions;
	}

	@Override
	public String toString() {
		return String.format("%s-%s", this.username, this.password);
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
