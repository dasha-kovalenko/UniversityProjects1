package by.kovalenko.periodicals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.domain.Edition;
import by.kovalenko.periodicals.domain.Subscription;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

public class PeriodicalsSubscriptionsDAO extends PeriodicalsDAO implements
		IPeriodicalsSubscriptionsDAO {
	private static Logger log = Logger
			.getLogger(PeriodicalsSubscriptionsDAO.class);

	@Override
	public Subscription getSubscription(long subscription_id)
			throws PeriodicalsDAOException {
		String query = "select * from subscriptions where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		Subscription subscription = new Subscription();
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setLong(1, subscription_id);
			rs = p.executeQuery();
			if (rs.next()) {
				subscription = new Subscription(rs.getDate("start_date"),
						rs.getDate("finish_date"), rs.getLong("id"),
						rs.getBoolean("paid"), rs.getLong("user_id"),
						rs.getDouble("price"));
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
		return subscription;
	}

	@Override
	public List<Subscription> listSubscriptions(long user_id)
			throws PeriodicalsDAOException {

		String query = "select * from subscriptions where user_id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		List<Subscription> subscriptions = new ArrayList<Subscription>();
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, user_id);
			rs = p.executeQuery();
			while (rs.next()) {
				Subscription subscription = new Subscription(
						rs.getDate("start_date"), rs.getDate("finish_date"),
						rs.getLong("id"), rs.getBoolean("paid"),
						rs.getLong("user_id"), rs.getDouble("price"));
				subscriptions.add(subscription);
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
		return subscriptions;

	}

	private void deleteEditionsFromSubscription(long subscriptionId)
			throws PeriodicalsDAOException {
		String query = "delete from subscriptions_editions where subscription_id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, subscriptionId);
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
	public void deleteSubscription(long subscription_id)
			throws PeriodicalsDAOException {
		deleteEditionsFromSubscription(subscription_id);
		String query = "delete from subscriptions where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, subscription_id);
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
	public void saveSubscription(Subscription subscription)
			throws PeriodicalsDAOException {
		String query = "insert into subscriptions (start_date, finish_date, paid, user_id, price)"
				+ "values (?, ?, ?, ?,?)";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setDate(1, subscription.getStartDate());
			p.setDate(2, subscription.getFinishDate());
			p.setBoolean(3, subscription.isPaid());
			p.setLong(4, subscription.getUserId());
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
	public void updateSubscription(Subscription subscription)
			throws PeriodicalsDAOException {
		String query = "update subscriptions set start_date = ?,  finish_date = ?, paid = ?,user_id = ?,price=? "
				+ "where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setDate(1, subscription.getStartDate());
			p.setDate(2, subscription.getFinishDate());
			p.setBoolean(3, subscription.isPaid());
			p.setLong(4, subscription.getUserId());
			p.setDouble(5, subscription.getPrice());
			p.setLong(6, subscription.getId());
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
	public List<Edition> getEditionsFromSubscription(Long subscriptionId)
			throws PeriodicalsDAOException {

		String query = "select editions.* from editions "
				+ "INNER JOIN subscriptions_editions ON "
				+ "editions.id = subscriptions_editions.edition_id"
				+ " WHERE subscriptions_editions.subscription_id=?";
		Connection connection = null;
		PreparedStatement p = null;
		List<Edition> editions = new ArrayList<Edition>();
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, subscriptionId);
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

}
