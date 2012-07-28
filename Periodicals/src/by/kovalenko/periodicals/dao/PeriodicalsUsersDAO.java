package by.kovalenko.periodicals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.domain.Cart;
import by.kovalenko.periodicals.domain.Edition;
import by.kovalenko.periodicals.domain.Subscription;
import by.kovalenko.periodicals.domain.User;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

public class PeriodicalsUsersDAO extends PeriodicalsDAO implements
		IPeriodicalsUsersDAO {
	private static Logger log = Logger.getLogger(PeriodicalsUsersDAO.class);

	private List<Edition> getEditions(Long cartId)
			throws PeriodicalsDAOException {
		String query = "select editions.* from editions "
				+ "INNER JOIN carts_editions ON "
				+ "editions.id = carts_editions.edition_id"
				+ " WHERE carts_editions.cart_id=?";
		Connection connection = null;
		PreparedStatement p = null;
		List<Edition> editions = new ArrayList<Edition>();
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			rs = p.executeQuery();
			while (rs.next()) {
				Edition edition = new Edition(rs.getString("title"),
						rs.getString("description"), rs.getDouble("price"),
						rs.getLong("id"));
				editions.add(edition);

			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
		return editions;
	}

	private Cart getCart(long id) throws PeriodicalsDAOException {
		String query = "select * from carts where user_id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		Cart cart = new Cart();
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setLong(1, id);
			rs = p.executeQuery();
			if (rs.next()) {
				cart = new Cart(rs.getLong("id"));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
		return cart;
	}

	@Override
	public User getUser(long id) throws PeriodicalsDAOException {
		String query = "select * from users where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		User user = new User();
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setLong(1, id);
			rs = p.executeQuery();
			IPeriodicalsSubscriptionsDAO subscriptionsDAO;
			if (rs.next()) {
				user = new User(rs.getLong("id"), rs.getString("username"),
						rs.getString("password"), rs.getBoolean("admin"),
						rs.getString("salt"));
				Cart cart = getCart(id);
				cart.setEditions(getEditions(cart.getId()));
				user.setCart(cart);
				subscriptionsDAO = new PeriodicalsSubscriptionsDAO();
				user.setSubsciptions(subscriptionsDAO.listSubscriptions(id));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
		return user;
	}

	@Override
	public List<User> listUsers() throws PeriodicalsDAOException {
		String query = "select * from users";
		Connection connection = null;
		PreparedStatement p = null;
		List<User> users = new ArrayList<User>();
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			rs = p.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getLong("id"),
						rs.getString("username"), rs.getString("password"),
						rs.getBoolean("admin"), rs.getString("salt"));
				users.add(user);
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
		return users;
	}

	private void deleteUserFromCarts(long userId)
			throws PeriodicalsDAOException {
		String query = "delete from carts where user_id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, userId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}

	}

	private void deleteUserFromSubscriptions(long userId)
			throws PeriodicalsDAOException {
		String query = "delete from subscriptions where user_id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, userId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}

	}

	@Override
	public void deleteUser(long userId) throws PeriodicalsDAOException {
		deleteUserFromSubscriptions(userId);
		deleteUserFromCarts(userId);
		String query = "delete from users where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, userId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
	}

	@Override
	public void saveUser(User user) throws PeriodicalsDAOException {
		String query = "insert into users (username, password, admin, salt)"
				+ "values (?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setString(1, user.getUsername());
			p.setString(2, user.getPassword());
			p.setBoolean(3, user.isAdmin());
			p.setString(4, user.getSalt());
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
	}

	@Override
	public void updateUser(User user) throws PeriodicalsDAOException {
		String query = "update users set username = ?,  password = ?, admin = ?, salt = ? "
				+ "where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setString(1, user.getUsername());
			p.setString(2, user.getPassword());
			p.setBoolean(3, user.isAdmin());
			p.setString(4, user.getSalt());
			p.setLong(5, user.getId());
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
	}

	@Override
	public void addCartToSubscriptionAndDelete(long cartId)
			throws PeriodicalsDAOException {
		String query = "insert into subscriptions_editions (subscription_id, edition_id)"
				+ "select cart_id, edition_id from carts_editions where cart_id = ?; "
				+ "delete from carts_editions where cart_id = ?;";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			p.setLong(2, cartId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}

	}

	@Override
	public User getUser(String name, String pass)
			throws PeriodicalsDAOException {
		String query = "select * from users where username=? and password=?";
		Connection connection = null;
		PreparedStatement p = null;
		User user = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setString(1, name);
			p.setString(2, pass);
			rs = p.executeQuery();
			if (rs.next()) {
				user = new User(rs.getLong("id"), rs.getString("username"),
						rs.getString("password"), rs.getBoolean("admin"),
						rs.getString("salt"));
				user.setCart(getCart(user.getId()));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
		return user;
	}

	@Override
	public User getUser(String name) throws PeriodicalsDAOException {
		String query = "select * from users where username=?";
		Connection connection = null;
		PreparedStatement p = null;
		User user = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setString(1, name);
			rs = p.executeQuery();
			if (rs.next()) {
				user = new User(rs.getLong("id"), rs.getString("username"),
						rs.getString("password"), rs.getBoolean("admin"),
						rs.getString("salt"));
				user.setCart(getCart(user.getId()));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
		return user;
	}

	@Override
	public void createSubscription(Long userId, Subscription subscription)
			throws PeriodicalsDAOException {
		String query = "insert into subscriptions (start_date, finish_date, paid, user_id,price) values (?,?,?,?,?)";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setDate(1, subscription.getStartDate());
			p.setDate(2, subscription.getFinishDate());
			p.setBoolean(3, false);
			p.setLong(4, userId);
			p.setDouble(5, subscription.getPrice());
			p.executeUpdate();

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
	}

	@Override
	public void clearCart(Long cartId) throws PeriodicalsDAOException {
		String query = "delete from carts_editions where cart_id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}

	}

	@Override
	public double getCartPrice(Long cartId) throws PeriodicalsDAOException {
		String query = "select sum(editions.price) as TotalPrice from editions "
				+ "INNER JOIN carts_editions ON "
				+ "editions.id = carts_editions.edition_id"
				+ " WHERE carts_editions.cart_id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		Double price = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			rs = p.executeQuery();
			if (rs.next()) {
				price = rs.getDouble("TotalPrice");
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
		return price;
	}

	@Override
	public void addEditionsToSubscriptionFromCart(Long subscriptionId,
			Long cartId) throws PeriodicalsDAOException {
		String query = "INSERT into subscriptions_editions (subscription_id, edition_id) (select ?, edition_id from carts_editions where cart_id = ?)";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, subscriptionId);
			p.setLong(2, cartId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
	}

	@Override
	public Long getLastSubscriptionFromUser(long userId)
			throws PeriodicalsDAOException {
		String query = "select id from subscriptions where user_id = ? order by id desc";
		Connection connection = null;
		PreparedStatement p = null;
		Long id = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setLong(1, userId);
			rs = p.executeQuery();
			if (rs.next()) {
				id = rs.getLong("id");
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
		return id;
	}

}