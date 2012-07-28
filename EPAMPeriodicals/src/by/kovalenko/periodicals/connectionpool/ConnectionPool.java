package by.kovalenko.periodicals.connectionpool;

import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;

import by.kovalenko.periodicals.exceptions.ConnectionPoolException;
import by.kovalenko.periodicals.managers.ConfigurationManager;

/**
 * Class <code>ConnectionPool</code> is used for storing,
 * {@link #getConnection() providing} and {@link #releaseConnection(Connection)
 * releasing} database connections. It contains methods for connection pool
 * {@link #initConnections() initialization} and {@link #releasePool()
 * releasing} the pool. Class <code>ConnectionPool</code> implements Singleton
 * pattern and based on collections and semaphore. Log4j is used for logging
 * error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class ConnectionPool {

	private static Logger log = Logger.getLogger(ConnectionPool.class);

	private static ConnectionPool pool;
	private List<Connection> freeConnections;

	private Integer connectionNumber = 10;//conf properties
	private Semaphore semaphore;

	private String driverName;
	private String url;
	private String username;
	private String password;

	/**
	 * @exception <code>ConnectionPoolException</code>
	 */
	private ConnectionPool() throws ConnectionPoolException {
		try {
			readProperties();
			initConnections();
			System.out.println("111");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			throw new ConnectionPoolException(e);
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
			throw new ConnectionPoolException(e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
			throw new ConnectionPoolException(e);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new ConnectionPoolException(e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new ConnectionPoolException(e);
		}

	}

	/**
	 * <code>initConnections</code> method is used for initialization and
	 * storing connections with configuration data properties.
	 * 
	 * @exception <code>ConnectionPoolException</code>,
	 *            <code>InstantiationException</code>,
	 *            <code>IllegalAccessException</code>,
	 *            <code>ClassNotFoundException</code>, <code>SQLException</code>
	 *            , <code>UnsupportedEncodingException</code>
	 * 
	 */
	private void initConnections() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			UnsupportedEncodingException {
		Class.forName(driverName).newInstance();
		semaphore = new Semaphore(connectionNumber);
		freeConnections = new LinkedList<Connection>();
		for (int i = 0; i < connectionNumber; i++) {
			Connection c = (Connection) DriverManager.getConnection(url,
					username, password);
			freeConnections.add(c);
		}
	}

	/**
	 * <code>readProperties</code> method is used for receiving database
	 * properties.
	 * 
	 * @exception <code>UnsupportedEncodingException</code>
	 * 
	 */
	private void readProperties() throws UnsupportedEncodingException {
		driverName = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.DATABASE_DRIVER_NAME);
		url = (ConfigurationManager.getInstance()
				.getValue(ConfigurationManager.DATABASE_URL));
		username = (ConfigurationManager.getInstance()
				.getValue(ConfigurationManager.DATABASE_USERNAME));
		password = (ConfigurationManager.getInstance()
				.getValue(ConfigurationManager.DATABASE_PASSWORD));
	}

	/**
	 * <code>getInstanse</code> method is used for receiving
	 * {@link ConnectionPool <code>ConnectionPool</code>} instance.
	 * 
	 * @exception <code>ConnectionPoolException</code>
	 * 
	 */
	public static ConnectionPool getInstanse() throws ConnectionPoolException {
		if (pool == null)
			pool = new ConnectionPool();
		return pool;
	}

	/**
	 * <code>getInstanse</code> method is used for receiving {@link Connection
	 * <code>Connection</code>} from collection of connections.
	 * 
	 * @exception <code>ConnectionPoolException</code>
	 * 
	 */
	public synchronized Connection getConnection()
			throws ConnectionPoolException {
		try {
			semaphore.acquire();
			Connection c = freeConnections.remove(0);
			return c;
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			throw new ConnectionPoolException(e);
		}
	}

	/**
	 * <code>getInstanse</code> method is used for receiving {@link Connection
	 * <code>Connection</code>} from collection of connections.
	 * 
	 * @param timeOut
	 *            is a waiting connection time in seconds.
	 * @exception <code>ConnectionPoolException</code>
	 * 
	 */
	public synchronized Connection getConnection(int timeOut)
			throws ConnectionPoolException {
		boolean acquired;
		try {
			acquired = semaphore.tryAcquire(timeOut, TimeUnit.SECONDS);
			if (!acquired)
				return null;
			else {
				Connection c = freeConnections.remove(0);
				return c;
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			throw new ConnectionPoolException(e);
		}
	}

	/**
	 * <code>releaseConnection</code> method is used for releasing
	 * {@link Connection <code>Connection</code>} and adding it to the
	 * collection of connections.
	 * 
	 * @param c is a {@link Connection <code>Connection</code>} to release.
	 * 
	 */
	public synchronized void releaseConnection(Connection c) {
		if ((c != null) && (freeConnections.size() <= connectionNumber)) {
			freeConnections.add(c);
			semaphore.release();
		}
	}

	/**
	 * <code>releasePool</code> method is used for releasing connection pool.
	 */
	public synchronized void releasePool() {
		for (Connection c : freeConnections)
			try {
				c.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
	}
}
