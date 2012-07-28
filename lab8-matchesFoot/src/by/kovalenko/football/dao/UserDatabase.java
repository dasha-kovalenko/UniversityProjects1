package by.kovalenko.football.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.kovalenko.football.domain.User;
import by.kovalenko.football.manager.ConfigurationManager;

public class UserDatabase implements IUserDatabase {

	private String url;
	private String username;
	private String password;

	public UserDatabase() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			UnsupportedEncodingException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		url = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.DATABASE_URL);
		username = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.DATABASE_USERNAME);
		password = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.DATABASE_PASSWORD);
	}

	@Override
	public void deleteUser(int id) {

		String query = "delete from users where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			p.setInt(1, id);
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public void insertUser(User user) {
		String query = "insert into users (name, password, admin)"
				+ "values (?, ?, ?)";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			p.setString(1, user.getName());
			p.setString(2, user.getPassword());
			p.setBoolean(3, user.isAdmin());
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public void updateUser(User user) {
		String query = "update users set name =?, password = ?,  admin = ? "
				+ "where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			p.setString(1, user.getName());
			p.setString(2, user.getPassword());
			p.setBoolean(3, user.isAdmin());
			p.setInt(4, user.getId());
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public User getUser(int id) {
		User user = null;
		String query = "select * from users where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("name"),
						rs.getString("password"), rs.getBoolean("admin"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (connection != null)
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return user;
	}

	@Override
	public User getUser(String name, String password) {
		User user = null;
		String query = "select * from users where name = ? and password = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, this.password);
			p = connection.prepareStatement(query);
			p.setString(1, name);
			p.setString(2, password);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("name"),
						rs.getString("password"), rs.getBoolean("admin"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (connection != null)
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return user;
	}
	
	@Override
	public User getUser(String name) {
		User user = null;
		String query = "select * from users where name = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			p.setString(1, name);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("name"),
						rs.getString("password"), rs.getBoolean("admin"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (connection != null)
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return user;
	}

	@Override
	public List<User> listUsers() {
		User user;
		List<User> matches = new ArrayList<User>();
		String query = "select * from users";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("name"),
						rs.getString("password"), rs.getBoolean("admin"));
				matches.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matches;
	}

}
