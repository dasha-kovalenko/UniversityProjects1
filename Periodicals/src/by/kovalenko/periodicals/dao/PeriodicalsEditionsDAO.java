package by.kovalenko.periodicals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.domain.Edition;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

public class PeriodicalsEditionsDAO extends PeriodicalsDAO implements
		IPeriodicalsEditionsDAO {
	private static Logger log = Logger.getLogger(PeriodicalsEditionsDAO.class);

	@Override
	public Edition getEdition(long edition_id) throws PeriodicalsDAOException {
		String query = "select * from editions where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		Edition edition = new Edition();
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setLong(1, edition_id);
			rs = p.executeQuery();
			if (rs.next()) {
				edition = new Edition(rs.getString("title"),
						rs.getString("description"), rs.getDouble("price"),
						rs.getLong("id"));
			}

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
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
		String query = "select * from editions";
		Connection connection = null;
		PreparedStatement p = null;
		List<Edition> editions = new ArrayList<Edition>();
		ResultSet rs = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
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

	private void deleteEditionFromEditionsKinds(long editionId)
			throws PeriodicalsDAOException {
		String query = "delete from editions_kinds where edition_id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, editionId);
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
	public void deleteEdition(long id) throws PeriodicalsDAOException {
		deleteEditionFromEditionsKinds(id);
		String query = "delete from editions where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, id);
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
	public void saveEdition(Edition edition) throws PeriodicalsDAOException {
		String query = "insert into editions (title, description, price)"
				+ "values (?, ?, ?)";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setString(1, edition.getTitle());
			p.setString(2, edition.getDescription());
			p.setDouble(3, edition.getPrice());
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
	public void updateEdition(Edition edition) throws PeriodicalsDAOException {
		String query = "update editions set title = ?,  description = ?, price = ?"
				+ "where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
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
		String query = "insert into carts_editions (cart_id, edition_id)"
				+ "values (?, ?)";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setLong(1, cartId);
			p.setLong(2, editionId);
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
	public void deleteEditionFromCart(long cartId, long editionId)
			throws PeriodicalsDAOException {
		String query = "delete from carts_editions where cart_id = ? and edition_id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = getConnection();
			p = connection.prepareStatement(query);
			p.setMaxRows(1);
			p.setLong(1, cartId);
			p.setLong(2, editionId);
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

}
