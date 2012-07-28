package by.kovalenko.periodicals.entities;

import java.util.List;

public class User {

	private long id;
	private String username;
	private String password;
	private Boolean admin;
	private Cart cart;
	private String salt;
	private List<Subscription> subscriptions;

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

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubsciptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
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

	@Override
	public int hashCode() {
		return (int) (31 * id + ((password == null) ? 0 : password.hashCode()) + +((username == null) ? 0
				: username.hashCode()));
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass()) {
			User temp = (User) obj;
			return (this.id == temp.id && this.username.equals(temp.username) && this.admin
					.equals(temp.admin));
		} else
			return false;

	}

}
