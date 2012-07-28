package by.kovalenko.football.command;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.kovalenko.football.dao.FootballDatabase;
import by.kovalenko.football.dao.IFootballDatabase;
import by.kovalenko.football.manager.ConfigurationManager;

public class MatchUpdateCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			// валидация на стороне клиента
			int id = Integer.parseInt(request.getParameter("id"));
			String team1 = request.getParameter("team1");
			String team2 = request.getParameter("team2");
			String count1 = request.getParameter("count1");
			String count2 = request.getParameter("count2");
			String count = String.format("%s:%s", count1, count2);
			Date date = Date.valueOf(request.getParameter("date"));
			IFootballDatabase db = new FootballDatabase();
			db.updateMatch(id, team1, team2, count, date);
			response.sendRedirect(request.getContextPath()
					+ "/football?command=matchList");
		} catch (InstantiationException e) {
			page = ConfigurationManager.getInstance().getValue(ConfigurationManager.ERROR_PAGE_PATH);
		} catch (IllegalAccessException e) {
			page = ConfigurationManager.getInstance().getValue(ConfigurationManager.ERROR_PAGE_PATH);
		} catch (ClassNotFoundException e) {
			page = ConfigurationManager.getInstance().getValue(ConfigurationManager.ERROR_PAGE_PATH);
		} catch (SQLException e) {
			page = ConfigurationManager.getInstance().getValue(ConfigurationManager.ERROR_PAGE_PATH);
		}
		return page;
	}
}
