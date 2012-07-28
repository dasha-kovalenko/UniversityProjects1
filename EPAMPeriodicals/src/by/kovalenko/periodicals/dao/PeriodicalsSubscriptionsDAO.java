package by.kovalenko.periodicals.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.entities.Edition;
import by.kovalenko.periodicals.entities.Subscription;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.ColumnNamesManager;
import by.kovalenko.periodicals.managers.SubscriptionSQLManager;

public class PeriodicalsSubscriptionsDAO extends PeriodicalsDAO implements
		IPeriodicalsSubscriptionsDAO {
	private static Logger log = Logger
			.getLogger(PeriodicalsSubscriptionsDAO.class);

	@Override
	public Subscription getSubscription(long subscription_id)
			throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		Subscription subscription = new Subscription();
		ResultSet rs = null;
		try {
			query = SubscriptionSQLManager.getInstance().getValue(
					SubscriptionSQLManager.GET_SUBSCRIPTION);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, subscription_id);
			rs = p.executeQuery();
			if (rs.next()) {
				subscription = new Subscription(
						rs.getDate(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.START_DATE)),
						rs.getDate(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.FINISH_DATE)),
						rs.getLong(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.ID)),
						rs.getBoolean(ColumnNamesManager.getInstance()
								.getValue(ColumnNamesManager.PAID)),
						rs.getLong(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.USER_ID)),
						rs.getDouble(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.PRICE)));
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
		return subscription;
	}

	@Override
	public List<Subscription> listSubscriptions(long user_id)
			throws PeriodicalsDAOException {

		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		List<Subscription> subscriptions = new ArrayList<Subscription>();
		ResultSet rs = null;
		try {
			query = SubscriptionSQLManager.getInstance().getValue(
					SubscriptionSQLManager.LIST_SUBSCRIPTIONS);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, user_id);
			rs = p.executeQuery();
			while (rs.next()) {
				Subscription subscription = new Subscription(
						rs.getDate(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.START_DATE)),
						rs.getDate(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.FINISH_DATE)),
						rs.getLong(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.ID)),
						rs.getBoolean(ColumnNamesManager.getInstance()
								.getValue(ColumnNamesManager.PAID)),
						rs.getLong(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.USER_ID)),
						rs.getDouble(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.PRICE)));
				subscriptions.add(subscription);
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
		return subscriptions;

	}

	private void deleteEditionsFromSubscription(long subscriptionId)
			throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = SubscriptionSQLManager.getInstance().getValue(
					SubscriptionSQLManager.DELETE_EDITIONS_FROM_SUBSCRIPTION);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, subscriptionId);
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
	public void deleteSubscription(long subscription_id)
			throws PeriodicalsDAOException {
		deleteEditionsFromSubscription(subscription_id);
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = SubscriptionSQLManager.getInstance().getValue(
					SubscriptionSQLManager.DELETE_SUBSCRIPTION);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, subscription_id);
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
	public void saveSubscription(Subscription subscription)
			throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = SubscriptionSQLManager.getInstance().getValue(
					SubscriptionSQLManager.SAVE_SUBSCRIPTION);
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
	public void updateSubscription(Subscription subscription)
			throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = SubscriptionSQLManager.getInstance().getValue(
					SubscriptionSQLManager.UPDATE_SUBSCRIPTION);
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

	/*
	 * This method is used in UserSubscriptionShowCommand during pushing the
	 * button ShowEditions.
	 */
	@Override
	public List<Edition> getEditionsFromSubscription(Long subscriptionId)
			throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		List<Edition> editions = new ArrayList<Edition>();
		ResultSet rs = null;
		try {
			query = SubscriptionSQLManager.getInstance().getValue(
					SubscriptionSQLManager.GET_EDITIONS_FROM_SUBSCRIPTION);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, subscriptionId);
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

}