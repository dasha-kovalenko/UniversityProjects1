
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * РљР»Р°СЃСЃ <code>MeetingDAO</code> РёСЃРїРѕР»СЊР·СѓРµС‚СЃСЏ РґР»СЏ РїРѕР»СѓС‡РµРЅРёСЏ РґРѕСЃС‚СѓРїР° Рє Р±Р°Р·Рµ
 * РґР°РЅРЅС‹С… <code>meetingDatabase</code>, РІС‹РїРѕР»РЅРµРЅРёСЋ Р·Р°РїСЂРѕСЃРѕРІ РїРѕ
 * {@link #selectCandidates(boolean, int, int, String, String) РІС‹Р±РѕСЂРєРµ РґР°РЅРЅС‹С…},
 * {@link #insertCandidate(boolean, String, int, String, String, String)
 * РґРѕР±Р°РІР»РµРЅРёСЋ РЅРѕРІС‹С… Р·Р°РїРёСЃРµР№} Рё {@link #deleteCandidate(int) СѓРґР°Р»РµРЅРёСЋ
 * СЃСѓС‰РµСЃС‚РІСѓСЋС‰РёС…}. Р РµР°Р»РёР·СѓРµС‚ РёРЅС‚РµСЂС„РµР№СЃ {@link IMeetingDAO
 * <code>IMeetingDAO</code>}.
 * 
 * @see <a
 *      href="http://docs.oracle.com/javase/6/docs/api/java/rmi/server/UnicastRemoteObject.html">UnicastRemoteObject</a>
 * @author <i>РђРЅРґСЂРµСЋРє РР»СЊСЏ, 3 РєСѓСЂСЃ, 1 РіСЂСѓРїРїР°</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class MeetingDAO extends UnicastRemoteObject implements IMeetingDAO {

	private String url;
	private Connection connection;

	public MeetingDAO() throws RemoteException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		url = "jdbc:mysql://localhost:3306/meetingDatabase";
		connection = DriverManager.getConnection(url, "player", "");
		connection.setAutoCommit(true);
	}

	/**
	 * РџСЂРµРґСЃС‚Р°РІР»СЏРµС‚ СЃРѕР±РѕР№ СЂРµР°Р»РёР·Р°С†РёСЋ РјРµС‚РѕРґР°
	 *      {@link IMeetingDAO#selectCandidates(boolean, int, int, String, String)
	 *      selectCandidates} РёРЅС‚РµСЂС„РµР№СЃР° {@link IMeetingDAO
	 *      <code>IMeetingDAO</code>}.
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
		try {
			prstmt = connection.prepareStatement(query);
			prstmt.setInt(1, minAge);
			prstmt.setInt(2, maxAge);
			prstmt.setBoolean(3, male);
			if (country != null)
				prstmt.setString(4, country);
			if (city != null)
				prstmt.setString(5, city);
			ResultSet rs = prstmt.executeQuery();
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
	 * РџСЂРµРґСЃС‚Р°РІР»СЏРµС‚ СЃРѕР±РѕР№ СЂРµР°Р»РёР·Р°С†РёСЋ РјРµС‚РѕРґР°
	 *      {@link IMeetingDAO#insertCandidate(boolean, String, int, String, String, String)
	 *      insertCandidate} РёРЅС‚РµСЂС„РµР№СЃР° {@link IMeetingDAO
	 *      <code>IMeetingDAO</code>}.
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
			if (connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
	 * РџСЂРµРґСЃС‚Р°РІР»СЏРµС‚ СЃРѕР±РѕР№ СЂРµР°Р»РёР·Р°С†РёСЋ РјРµС‚РѕРґР°
	 *      {@link IMeetingDAO#deleteCandidate(int) deleteCandidate} РёРЅС‚РµСЂС„РµР№СЃР°
	 *      {@link IMeetingDAO <code>IMeetingDAO</code>}.
	 */
	@Override
	public void deleteCandidate(int id) throws RemoteException {
		String query = "delete from partners where id = ?";
		PreparedStatement prstmt = null;
		try {
			connection = DriverManager.getConnection(url, "player", "");
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
	 * РџСЂРµРґСЃС‚Р°РІР»СЏРµС‚ СЃРѕР±РѕР№ СЂРµР°Р»РёР·Р°С†РёСЋ РјРµС‚РѕРґР°
	 *      {@link IMeetingDAO#getCandidate(int) getCandidate} РёРЅС‚РµСЂС„РµР№СЃР°
	 *      {@link IMeetingDAO <code>IMeetingDAO</code>}.
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
	 *РџСЂРµРґСЃС‚Р°РІР»СЏРµС‚ СЃРѕР±РѕР№ СЂРµР°Р»РёР·Р°С†РёСЋ РјРµС‚РѕРґР°
	 *      {@link IMeetingDAO#updateCandidate(Candidate) updateCandidate}
	 *      РёРЅС‚РµСЂС„РµР№СЃР° {@link IMeetingDAO <code>IMeetingDAO</code>}.
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

}
