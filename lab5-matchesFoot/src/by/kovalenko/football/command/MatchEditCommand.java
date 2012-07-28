package by.kovalenko.football.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.kovalenko.football.dao.FootballDatabase;
import by.kovalenko.football.dao.IFootballDatabase;
import by.kovalenko.football.domain.Match;
import by.kovalenko.football.manager.ConfigurationManager;

public class MatchEditCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.MATCH_EDIT_PAGE_PATH);
		IFootballDatabase db;
		try {
			db = new FootballDatabase();
			int id = Integer.parseInt(request.getParameter("id"));
			Match match = db.getMatch(id);
			request.setAttribute("match", match);
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
