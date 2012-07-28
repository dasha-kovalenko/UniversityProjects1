package by.kovalenko.football.dao;
import java.sql.Date;
import java.util.List;

import by.kovalenko.football.domain.Match;


public interface IMatchDatabase {
	List<Match> selectMatches(String team, Date startDate, Date endDate);

	Match getMatch(int id);
	
	void insertMatch(String team1, String team2, String count, Date date);
	
	void insertMatch(Match match);
	
	void updateMatch(int id, String team1, String team2, String count, Date date);

	void updateMatch(Match match);
	
	void deleteMatch(String team1, String team2, String count, Date date);
	
	void deleteMatch(int id);
	
	List<String> getTeams();
	
	List<Match> showDataBase();

}
