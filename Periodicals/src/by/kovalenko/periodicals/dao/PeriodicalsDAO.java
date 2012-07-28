package by.kovalenko.periodicals.dao;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;

import by.kovalenko.periodicals.connectionpool.ConnectionPool;
import by.kovalenko.periodicals.exceptions.ConnectionPoolException;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;


public class PeriodicalsDAO {
	private Connection connection;
	private static ConnectionPool connectionPool;
	private static Logger log = Logger.getLogger(PeriodicalsDAO.class);

	public PeriodicalsDAO() {
		try {
			connectionPool = ConnectionPool.getInstanse();
		} catch (ConnectionPoolException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void releaseConnection() {
		connectionPool.releaseConnection(connection);
	}

	public Connection getConnection() throws PeriodicalsDAOException{
		this.connection = connectionPool.getConnection();
		return connection;
	}
}
