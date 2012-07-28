package by.kovalenko.football.domain;

public class User {

	private int id;
	private String name;
	private String password;
	private String passwordConfirmation;
	private boolean admin;

	public User() {
		super();
	}

	public User(int id, String name, String password, boolean admin) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.admin = admin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password
				+ ", passwordConfirmation=" + passwordConfirmation + ", admin="
				+ admin + "]";
	}

}
