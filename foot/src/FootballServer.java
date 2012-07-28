import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FootballServer  extends UnicastRemoteObject implements FootballDataBase{
	
	private String url;

	public FootballServer() throws RemoteException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		url = "jdbc:mysql://localhost:3306/footballdb";
	}

	@Override
	public void deleteMatch(String team1, String team2, String count, Date date) throws RemoteException {
		
		String query = "delete from matches where team_1 = ? and team_2 = ? and count = ? and matchdate = ?" +
				"or team_1 = ? and team_2 = ? and count = ? and matchdate = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, "player", "");
			p = connection.prepareStatement(query);
			p.setString(1, team1);
			p.setString(2, team2);
			p.setString(3, count);
			p.setDate(4, date);
			p.setString(5, team2);
			p.setString(6, team1);
			StringTokenizer st = new StringTokenizer(count,":");
			String str1 = st.nextToken();
			String str2 = st.nextToken();
			String count1 = String.format("%s:%s", str2,str1);
			p.setString(7, count1);
			p.setDate(8, date);
			p.executeUpdate();
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
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public void insertMatch(String team1, String team2, String count, Date date) throws RemoteException {
		String query = "insert into matches (team_1, team_2, count, matchdate)"
			+ "values (?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, "player", "");
			p = connection.prepareStatement(query);
			p.setString(1, team1);
			p.setString(2, team2);
			p.setString(3, count);
			p.setDate(4, date);
			p.executeUpdate();
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
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public List<Match> selectMatches(String team, Date startDate, Date endDate) throws RemoteException {
		List<Match> matches = new ArrayList<Match>();
		String query = "select * from matches where team_1 = ? and matchdate >= ? and matchdate <= ? or team_2  = ? and matchdate >= ? and matchdate <= ?";
		Connection connection = null;
		PreparedStatement p = null;
	
		try {
			connection = DriverManager.getConnection(url, "player", "");
			p = connection.prepareStatement(query);
			p.setString(1, team);
			p.setDate(2, startDate);
			p.setDate(3, endDate);
			p.setString(4,team);
			p.setDate(5, startDate);
			p.setDate(6, endDate);
			//System.out.println(p.toString());

			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				Match match = new Match(rs.getInt("id"),
						rs.getString("team_1"),
						rs.getString("team_2"),
						rs.getString("count"), rs.getDate("matchdate"));
				matches.add(match);
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException();
		}finally {
			if (p != null)
				try {
					p.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			if (connection != null)
				try {
					connection.setAutoCommit(true);
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
			return matches;
	}

	@Override
	public List<Match> showDataBase() throws RemoteException {
		// TODO Auto-generated method stub
		List<Match> matches = new ArrayList<Match>();
		String query = "select * from matches";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, "player", "");
			p = connection.prepareStatement(query);

			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				Match match = new Match(rs.getInt("id"),
						rs.getString("team_1"),
						rs.getString("team_2"),
						rs.getString("count"), rs.getDate("matchdate"));
				matches.add(match);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return matches;
	}

}
