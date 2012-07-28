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
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.ColumnNamesManager;
import by.kovalenko.periodicals.managers.EditionSQLManager;

public class PeriodicalsEditionsDAO extends PeriodicalsDAO implements
		IPeriodicalsEditionsDAO {
	private static Logger log = Logger.getLogger(PeriodicalsEditionsDAO.class);

	@Override
	public Edition getEdition(long edition_id) throws PeriodicalsDAOException {
		Connection connection = null;
		PreparedStatement p = null;
		Edition edition = new Edition();
		ResultSet rs = null;
		String query;

		try {
			query = EditionSQLManager.getInstance().getValue(
					EditionSQLManager.GET_EDITION);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, edition_id);
			rs = p.executeQuery();
			if (rs.next()) {
				edition = new Edition(rs.getString(ColumnNamesManager
						.getInstance().getValue(ColumnNamesManager.TITLE)),
						rs.getString(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.DESCRIPTION)),
						rs.getDouble(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.PRICE)),
						rs.getLong(ColumnNamesManager.getInstance().getValue(
								ColumnNamesManager.ID)));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new PeriodicalsDAOException(e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					log.error(e1.getMessage(), e1);
				}
			releaseConnection();
		}
		return edition;
	}

	@Override
	public List<Edition> listEditions() throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		List<Edition> editions = new ArrayList<Edition>();
		ResultSet rs = null;
		try {
			query = EditionSQLManager.getInstance().getValue(
					EditionSQLManager.LIST_EDITIONS);
			connection = getConnection();
			p = connection.prepareStatement(query);
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
			log.error(e);
			throw new PeriodicalsDAOException(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
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

	@Override
	public void deleteEdition(long id) throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = EditionSQLManager.getInstance().getValue(
					EditionSQLManager.DELETE_EDITION);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, id);
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
	public void saveEdition(Edition edition) throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = EditionSQLManager.getInstance().getValue(
					EditionSQLManager.SAVE_EDITION);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setString(1, edition.getTitle());
			p.setString(2, edition.getDescription());
			p.setDouble(3, edition.getPrice());
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
	public void updateEdition(Edition edition) throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = EditionSQLManager.getInstance().getValue(
					EditionSQLManager.UPDATE_EDITION);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setString(1, edition.getTitle());
			p.setString(2, edition.getDescription());
			p.setDouble(3, edition.getPrice());
			p.setLong(4, edition.getId());
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
	public void addEditionToCart(long cartId, long editionId)
			throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = EditionSQLManager.getInstance().getValue(
					EditionSQLManager.ADD_EDITION_TO_CART);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			p.setLong(2, editionId);
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
	public void deleteEditionFromCart(long cartId, long editionId)
			throws PeriodicalsDAOException {
		String query = null;
		Connection connection = null;
		PreparedStatement p = null;
		try {
			query = EditionSQLManager.getInstance().getValue(
					EditionSQLManager.DELETE_EDITION_FROM_CART);
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			p.setLong(2, editionId);
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

}
