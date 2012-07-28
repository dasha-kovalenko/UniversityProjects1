package by.kovalenko.football.command;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.kovalenko.football.dao.FootballDatabase;
import by.kovalenko.football.dao.IFootballDatabase;
import by.kovalenko.football.domain.Match;
import by.kovalenko.football.manager.ConfigurationManager;

public class MatchListCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.MATCH_LIST_PAGE_PATH);

		try {
			List<Match> matches = null;
			IFootballDatabase db = new FootballDatabase();
			if (!request.getParameterMap().isEmpty()
					&& !(request.getParameterMap().size() == 1)) {
				String team = request.getParameter("team");
				Date startDate = Date.valueOf(request.getParameter("date1"));
				Date endDate = Date.valueOf(request.getParameter("date2"));
				matches = db.selectMatches(team, startDate, endDate);
			} else {
				matches = db.showDataBase();
			}
			List<String> teams = db.getTeams();
			Collections.sort(teams);
			request.setAttribute("teams", teams);
			request.setAttribute("matches", matches);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return page;
	}
}
