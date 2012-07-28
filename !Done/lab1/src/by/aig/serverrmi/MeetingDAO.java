package by.aig.serverrmi;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.aig.common.Candidate;
import by.aig.common.IMeetingDAO;

/**
 * Класс <code>MeetingDAO</code> используется для получения доступа к базе
 * данных <code>meetingDatabase</code>, выполнению запросов по
 * {@link #selectCandidates(boolean, int, int, String, String) выборке данных},
 * {@link #insertCandidate(boolean, String, int, String, String, String)
 * добавлению новых записей} и {@link #deleteCandidate(int) удалению
 * существующих}. Реализует интерфейс {@link IMeetingDAO
 * <code>IMeetingDAO</code>}.
 * 
 * @see <a
 *      href="http://docs.oracle.com/javase/6/docs/api/java/rmi/server/UnicastRemoteObject.html">UnicastRemoteObject</a>
 * @author <i>Андреюк Илья, 3 курс, 1 группа</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class MeetingDAO extends UnicastRemoteObject implements IMeetingDAO {

	private String url;
	private Connection connection = null;

	public MeetingDAO() throws RemoteException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		super(Registry.REGISTRY_PORT);
		Class.forName("com.mysql.jdbc.Driver");
		url = "jdbc:mysql://localhost:3306/meetingDatabase";
		connection = DriverManager.getConnection(url, "ilya", "");
		connection.setAutoCommit(true);
	}

	/**
	 * Представляет собой реализацию метода
	 * {@link IMeetingDAO#selectCandidates(boolean, int, int, String, String)
	 * selectCandidates} интерфейса {@link IMeetingDAO <code>IMeetingDAO</code>}
	 * .
	 */
	@Override
	public List<Candidate> selectCandidates(boolean male, int minAge,
			int maxAge, String country, String city) throws RemoteException {
		List<Candidate> list = new ArrayList<Candidate>();
		String query = "select * from partners where age >= ? and age <= ? and "
				+ "male = ? "
				+ (country != null ? "and country = ?" : "")
				+ (city != null ? " and city = ?" : "");
		PreparedStatement prstmt = null;
		ResultSet rs = null;
		System.out.println(query);
		try {
			prstmt = connection.prepareStatement(query);
			prstmt.setInt(1, minAge);
			prstmt.setInt(2, maxAge);
			prstmt.setBoolean(3, male);
			if (country != null)
				prstmt.setString(4, country);
			if (city != null)
				prstmt.setString(5, city);
			rs = prstmt.executeQuery();
			while (rs.next()) {
				Candidate candidate = new Candidate(rs.getInt("id"),
						rs.getInt("age"), rs.getString("name"),
						rs.getBoolean("male"), rs.getString("country"),
						rs.getString("city"), rs.getString("phone"));
				list.add(candidate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (prstmt != null)
				try {
					prstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return list;
	}

	/**
	 * Представляет собой реализацию метода
	 * {@link IMeetingDAO#insertCandidate(boolean, String, int, String, String, String)
	 * insertCandidate} интерфейса {@link IMeetingDAO <code>IMeetingDAO</code>}.
	 */
	@Override
	public void insertCandidate(boolean male, String name, int age,
			String country, String city, String phone) throws RemoteException {
		String query = "insert into partners (age, name, male, country, city, phone)"
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement prstmt = null;
		try {
			prstmt = connection.prepareStatement(query);
			prstmt.setInt(1, age);
			prstmt.setString(2, name);
			prstmt.setBoolean(3, male);
			prstmt.setString(4, country);
			prstmt.setString(5, city);
			prstmt.setString(6, phone);
			prstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException();
		} finally {
			if (prstmt != null)
				try {
					prstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
	}

	/**
	 * Представляет собой реализацию метода
	 * {@link IMeetingDAO#deleteCandidate(int) deleteCandidate} интерфейса
	 * {@link IMeetingDAO <code>IMeetingDAO</code>}.
	 */
	@Override
	public void deleteCandidate(int id) throws RemoteException {
		String query = "delete from partners where id = ?";
		PreparedStatement prstmt = null;
		try {
			prstmt = connection.prepareStatement(query);
			prstmt.setInt(1, id);
			prstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException();
		} finally {
			if (prstmt != null)
				try {
					prstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
	}

	/**
	 * Представляет собой реализацию метода
	 * {@link IMeetingDAO#getCandidate(int) getCandidate} интерфейса
	 * {@link IMeetingDAO <code>IMeetingDAO</code>}.
	 */
	@Override
	public Candidate getCandidate(int id) throws RemoteException {
		Candidate candidate = null;
		String query = "select * from partners where id=?";
		PreparedStatement prstmt = null;
		try {
			prstmt = connection.prepareStatement(query);
			prstmt.setInt(1, id);
			ResultSet rs = prstmt.executeQuery();
			while (rs.next()) {
				candidate = new Candidate(rs.getInt("id"), rs.getInt("age"),
						rs.getString("name"), rs.getBoolean("male"),
						rs.getString("country"), rs.getString("city"),
						rs.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException();
		} finally {
			if (prstmt != null)
				try {
					prstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
		return candidate;
	}

	/**
	 * Представляет собой реализацию метода
	 * {@link IMeetingDAO#updateCandidate(Candidate) updateCandidate} интерфейса
	 * {@link IMeetingDAO <code>IMeetingDAO</code>}.
	 */
	@Override
	public void updateCandidate(Candidate candidate) throws RemoteException {
		String query = "update partners set age=?,name=?,male=?,country=?,city=?,phone=?  where id=?";
		PreparedStatement prstmt = null;
		try {
			prstmt = connection.prepareStatement(query);
			prstmt.setInt(1, candidate.getAge());
			prstmt.setString(2, candidate.getName());
			prstmt.setBoolean(3, candidate.isMale());
			prstmt.setString(4, candidate.getCountry());
			prstmt.setString(5, candidate.getCity());
			prstmt.setString(6, candidate.getPhone());
			prstmt.setInt(7, candidate.getId());
			prstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException();
		} finally {
			if (prstmt != null)
				try {
					prstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}
	}

	public void closeConnection() {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
