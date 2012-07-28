package by.aig.common;
import java.io.Serializable;
import java.sql.Date;

/**
 * Класс <code>Match</code> представляет собой класс для хранения матчей
 * в базе данных.
 * 
 * @author <i>Коваленко Дарья, 3 курс, 1 группа</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */

public class Match implements Serializable{
	
	private int id;
	private String team1;
	private String team2;
	private String count;
	private Date matchdate;
	public Match() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Match(int id, String team1, String team2,String count, Date matchdate) {
		super();
		this.count = count;
		this.id = id;
		this.matchdate = matchdate;
		this.team1 = team1;
		this.team2 = team2;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTeam1() {
		return team1;
	}
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	public String getTeam2() {
		return team2;
	}
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public Date getMatchdate() {
		return matchdate;
	}
	public void setMatchdate(Date matchdate) {
		this.matchdate = matchdate;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "team1: " + team1 + ", team2: " + team2+
		", count - " + count + " , date: " + matchdate.toString() + ", id: " + id;
	}
	
	

}
