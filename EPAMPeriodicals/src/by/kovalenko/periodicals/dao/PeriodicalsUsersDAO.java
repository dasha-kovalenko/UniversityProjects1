package by.kovalenko.periodicals.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.entities.Cart;
import by.kovalenko.periodicals.entities.Edition;
import by.kovalenko.periodicals.entities.Subscription;
import by.kovalenko.periodicals.entities.User;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.ColumnNamesManager;
import by.kovalenko.periodicals.managers.UserSQLManager;

public class PeriodicalsUsersDAO extends PeriodicalsDAO implements
		IPeriodicalsUsersDAO {
	private static Logger log = Logger.getLogger(PeriodicalsUsersDAO.class);

	private List<Edition> getEditions(Long cartId)
			throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		List<Edition> editions = new ArrayList<Edition>();
		ResultSet rs = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.GET_EDITIONS);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			rs = p.executeQuery();
			while (rs.next()) {
				Edition edition = new Edition(rs.getString(ColumnNamesManager
						.getInstance().getValue(ColumnNamesManager.TITLE)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.DESCRIPTION)),
						rs.getDouble(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.PRICE)),
						rs.getLong(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.ID)));
				editions.add(edition);

			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		Cart cart = new Cart();
		ResultSet rs = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.GET_CART);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, id);
			rs = p.executeQuery();
			if (rs.next()) {
				cart = new Cart(rs.getLong(ColumnNamesManager.getInstance()
						.getValue(ColumnNamesManager.ID)));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		User user = new User();
		ResultSet rs = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.GET_USER_WITH_ID);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, id);
			rs = p.executeQuery();
			IPeriodicalsSubscriptionsDAO subscriptionsDAO;
			if (rs.next()) {
				user = new User(rs.getLong(ColumnNamesManager.getInstance()
						.getValue(ColumnNamesManager.ID)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.USERNAME)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.PASSWORD)),
						rs.getBoolean(ColumnNamesManager.getInstance()
								.getValue(ColumnNamesManager.ADMIN)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.SALT)));
				Cart cart = getCart(id);
				cart.setEditions(getEditions(cart.getId()));
				user.setCart(cart);
				subscriptionsDAO = new PeriodicalsSubscriptionsDAO();
				user.setSubsciptions(subscriptionsDAO.listSubscriptions(id));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		List<User> users = new ArrayList<User>();
		ResultSet rs = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.LIST_USERS);
			connection = getConnection();
			p = connection.prepareStatement(query);
			rs = p.executeQuery();
			while (rs.next()) {
				User user = new User(rs.getLong(ColumnNamesManager.getInstance()
						.getValue(ColumnNamesManager.ID)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.USERNAME)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.PASSWORD)),
						rs.getBoolean(ColumnNamesManager.getInstance()
								.getValue(ColumnNamesManager.ADMIN)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.SALT)));
				users.add(user);
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.DELETE_USER_FROM_CARTS);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, userId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.DELETE_USER_FROM_SUBSCRIPTIONS);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, userId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.DELETE_USER);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, userId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.SAVE_USER);
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
		} catch (UnsupportedEncodingException e) {
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
		String query = "";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.UPDATE_USER);
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
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.ADD_CART_TO_SUBSCRIPTION_AND_DELETE);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			p.setLong(2, cartId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = "";
		Connection connection = null;
		PreparedStatement p = null;
		User user = null;
		ResultSet rs = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.GET_USER_WITH_PASSWORD_AND_NAME);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setString(1, name);
			p.setString(2, pass);
			rs = p.executeQuery();
			if (rs.next()) {
				user = new User(rs.getLong(ColumnNamesManager.getInstance()
						.getValue(ColumnNamesManager.ID)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.USERNAME)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.PASSWORD)),
						rs.getBoolean(ColumnNamesManager.getInstance()
								.getValue(ColumnNamesManager.ADMIN)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.SALT)));
				user.setCart(getCart(user.getId()));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = "";
		Connection connection = null;
		PreparedStatement p = null;
		User user = null;
		ResultSet rs = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.GET_USER_WITH_NAME);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setString(1, name);
			rs = p.executeQuery();
			if (rs.next()) {
				user = new User(rs.getLong(ColumnNamesManager.getInstance()
						.getValue(ColumnNamesManager.ID)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.USERNAME)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.PASSWORD)),
						rs.getBoolean(ColumnNamesManager.getInstance()
								.getValue(ColumnNamesManager.ADMIN)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.SALT)));
				user.setCart(getCart(user.getId()));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.CREATE_SUBSCRIPTION);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setDate(1, subscription.getStartDate());
			p.setDate(2, subscription.getFinishDate());
			p.setBoolean(3, false);
			p.setLong(4, userId);
			p.setDouble(5, subscription.getPrice());
			p.executeUpdate();

		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.CLEAR_CART);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		Double price = null;
		ResultSet rs = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.GET_CART_PRICE);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			rs = p.executeQuery();
			if (rs.next()) {
				price = rs.getDouble(ColumnNamesManager.getInstance().getValue(
						ColumnNamesManager.TOTAL_PRICE));
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.ADD_EDITION_TO_SUBSCRIPTION_FROM_CART);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, subscriptionId);
			p.setLong(2, cartId);
			p.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		Long id = null;
		ResultSet rs = null;
		try {
			query = UserSQLManager.getInstance().getValue(
					UserSQLManager.GET_LAST_SUBSCRIPTION_FROM_USER);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setLong(1, userId);
			rs = p.executeQuery();
			if (rs.next()) {
				id = rs.getLong(ColumnNamesManager.getInstance().getValue(
						ColumnNamesManager.ID));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
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