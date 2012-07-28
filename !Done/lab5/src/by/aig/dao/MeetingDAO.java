package by.aig.dao;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.aig.domain.Candidate;
import by.aig.manager.ConfigurationManager;

public class MeetingDAO {

	private Connection connection;

	private static MeetingDAO instance;

	public static MeetingDAO getInstance() {
		if (instance == null)
			try {
				instance = new MeetingDAO();
			} catch (Exception e) {
			}
		return instance;
	}

	public MeetingDAO() throws RemoteException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			UnsupportedEncodingException {
		Class.forName(
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_DRIVER_NAME))
				.newInstance();
	}

	public List<Candidate> selectCandidates(Boolean male, int minAge,
			int maxAge, String country, String city) throws SQLException, UnsupportedEncodingException {
		List<Candidate> list = new ArrayList<Candidate>();
		String query = "select * from partners where age >= ? and age <= ?"
				+ (male != null ? " and male = ? " : "")
				+ (country != null ? "and country = ?" : "")
				+ (city != null ? " and city = ?" : "");
		connection = DriverManager.getConnection(
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_URL),
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_USERNAME),
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_PASSWORD));
		connection.setAutoCommit(true);
		PreparedStatement prstmt = null;
		ResultSet rs = null;
		int counter = 1;
		prstmt = connection.prepareStatement(query);
		prstmt.setInt(counter++, minAge);
		prstmt.setInt(counter++, maxAge);
		if (male != null)
			prstmt.setBoolean(counter++, male);
		if (country != null)
			prstmt.setString(counter++, country);
		if (city != null)
			prstmt.setString(counter++, city);
		rs = prstmt.executeQuery();
		while (rs.next()) {
			Candidate candidate = new Candidate(rs.getInt("id"),
					rs.getInt("age"), rs.getString("name"),
					rs.getBoolean("male"), rs.getString("country"),
					rs.getString("city"), rs.getString("phone"));
			list.add(candidate);
		}
		rs.close();
		prstmt.close();
		connection.close();
		return list;
	}

	public void insertCandidate(boolean male, String name, int age,
			String country, String city, String phone) throws SQLException, UnsupportedEncodingException {
		String query = "insert into partners (age, name, male, country, city, phone)"
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		connection = DriverManager.getConnection(
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_URL),
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_USERNAME),
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_PASSWORD));
		connection.setAutoCommit(true);
		PreparedStatement prstmt = null;
		prstmt = connection.prepareStatement(query);
		prstmt.setInt(1, age);
		prstmt.setString(2, name);
		prstmt.setBoolean(3, male);
		prstmt.setString(4, country);
		prstmt.setString(5, city);
		prstmt.setString(6, phone);
		prstmt.executeUpdate();
		prstmt.close();
		connection.close();
	}

	public void deleteCandidate(int id) throws SQLException, UnsupportedEncodingException {
		String query = "delete from partners where id = ?";
		connection = DriverManager.getConnection(
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_URL),
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_USERNAME),
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_PASSWORD));
		connection.setAutoCommit(true);
		PreparedStatement prstmt = null;
		prstmt = connection.prepareStatement(query);
		prstmt.setInt(1, id);
		prstmt.executeUpdate();
		prstmt.close();
		connection.close();
	}

	public Candidate getCandidate(int id) throws SQLException, UnsupportedEncodingException {
		Candidate candidate = null;
		String query = "select * from partners where id=?";
		connection = DriverManager.getConnection(
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_URL),
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_USERNAME),
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_PASSWORD));
		connection.setAutoCommit(true);
		PreparedStatement prstmt = null;
		prstmt = connection.prepareStatement(query);
		prstmt.setInt(1, id);
		ResultSet rs = prstmt.executeQuery();
		while (rs.next()) {
			candidate = new Candidate(rs.getInt("id"), rs.getInt("age"),
					rs.getString("name"), rs.getBoolean("male"),
					rs.getString("country"), rs.getString("city"),
					rs.getString("phone"));
		}
		rs.close();
		prstmt.close();
		connection.close();
		return candidate;
	}

	public void updateCandidate(Candidate candidate) throws SQLException, UnsupportedEncodingException {
		String query = "update partners set age=?,name=?,male=?,country=?,city=?,phone=?  where id=?";
		connection = DriverManager.getConnection(
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_URL),
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_USERNAME),
				ConfigurationManager.getInstance().getValue(
						ConfigurationManager.DATABASE_PASSWORD));
		connection.setAutoCommit(true);
		PreparedStatement prstmt = null;
		prstmt = connection.prepareStatement(query);
		prstmt.setInt(1, candidate.getAge());
		prstmt.setString(2, candidate.getName());
		prstmt.setBoolean(3, candidate.isMale());
		prstmt.setString(4, candidate.getCountry());
		prstmt.setString(5, candidate.getCity());
		prstmt.setString(6, candidate.getPhone());
		prstmt.setInt(7, candidate.getId());
		prstmt.executeUpdate();
		prstmt.close();
		connection.close();
	}

}
