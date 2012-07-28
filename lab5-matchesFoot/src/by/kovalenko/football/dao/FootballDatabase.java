package by.kovalenko.football.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import by.kovalenko.football.domain.Match;
import by.kovalenko.football.manager.ConfigurationManager;

public class FootballDatabase implements IFootballDatabase {

	private String url;
	private String username;
	private String password;

	public FootballDatabase() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			UnsupportedEncodingException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		url = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.DATABASE_URL);
		username = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.DATABASE_USERNAME);
		password = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.DATABASE_PASSWORD);
	}

	@Override
	public void deleteMatch(String team1, String team2, String count, Date date) {

		String query = "delete from matches where team_1 = ? and team_2 = ? and count = ? and matchdate = ?"
				+ "or team_1 = ? and team_2 = ? and count = ? and matchdate = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			p.setString(1, team1);
			p.setString(2, team2);
			p.setString(3, count);
			p.setDate(4, date);
			p.setString(5, team2);
			p.setString(6, team1);
			StringTokenizer st = new StringTokenizer(count, ":");
			String str1 = st.nextToken();
			String str2 = st.nextToken();
			String count1 = String.format("%s:%s", str2, str1);
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
	public void deleteMatch(int id) {

		String query = "delete from matches where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			p.setInt(1, id);
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
	public void insertMatch(String team1, String team2, String count, Date date) {
		String query = "insert into matches (team_1, team_2, count, matchdate)"
				+ "values (?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
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
	public void updateMatch(int id, String team1, String team2, String count,
			Date date) {
		String query = "update matches set team_1 =?, team_2 = ?,  count = ?, matchdate = ?"
				+ "where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			p.setString(1, team1);
			p.setString(2, team2);
			p.setString(3, count);
			p.setDate(4, date);
			p.setInt(5, id);
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null)
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
	public List<Match> selectMatches(String team, Date startDate, Date endDate) {
		List<Match> matches = new ArrayList<Match>();
		String query = "select * from matches where team_1 = ? and matchdate >= ? and matchdate <= ? or team_2  = ? and matchdate >= ? and matchdate <= ?";
		Connection connection = null;
		PreparedStatement p = null;

		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			p.setString(1, team);
			p.setDate(2, startDate);
			p.setDate(3, endDate);
			p.setString(4, team);
			p.setDate(5, startDate);
			p.setDate(6, endDate);
			// System.out.println(p.toString());

			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				Match match = new Match(rs.getInt("id"),
						rs.getString("team_1"), rs.getString("team_2"),
						rs.getString("count"), rs.getDate("matchdate"));
				matches.add(match);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	public Match getMatch(int id) {
		Match match = null;
		String query = "select * from matches where id = ?";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				match = new Match(rs.getInt("id"), rs.getString("team_1"),
						rs.getString("team_2"), rs.getString("count"),
						rs.getDate("matchdate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return match;
	}

	@Override
	public List<Match> showDataBase() {
		List<Match> matches = new ArrayList<Match>();
		String query = "select * from matches";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);

			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				Match match = new Match(rs.getInt("id"),
						rs.getString("team_1"), rs.getString("team_2"),
						rs.getString("count"), rs.getDate("matchdate"));
				matches.add(match);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return matches;
	}

	@Override
	public List<String> getTeams() {
		List<String> teams = new LinkedList<String>();
		String query = "select team_1 from matches union select team_2 from matches";
		Connection connection = null;
		PreparedStatement p = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			p = connection.prepareStatement(query);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				teams.add(rs.getString("team_1"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		return teams;
	}

}
