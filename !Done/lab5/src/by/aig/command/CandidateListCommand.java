package by.aig.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.aig.dao.MeetingDAO;
import by.aig.domain.Candidate;
import by.aig.manager.ConfigurationManager;
import by.aig.manager.MessageManager;

public class CandidateListCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.CANDIDATE_LIST_PAGE_PATH);
		int minAge;
		if (request.getParameterMap().containsKey("minAge")
				&& request.getParameter("minAge").matches("[1-9][0-9]"))
			minAge = Integer.parseInt(request.getParameter("minAge"));
		else
			minAge = 18;
		int maxAge;
		if (request.getParameterMap().containsKey("maxAge")
				&& request.getParameter("maxAge").matches("[1-9][0-9]"))
			maxAge = Integer.parseInt(request.getParameter("maxAge"));
		else
			maxAge = 99;
		Boolean male;
		if (request.getParameter("male") != null)
			male = Boolean.parseBoolean(request.getParameter("male"));
		else
			male = null;
		String country = request.getParameter("country");
		if ("Любая".equals(country))
			country = null;
		String city = request.getParameter("city");
		if ("Любой".equals(city))
			city = null;
		//System.out.println(minAge + " " + maxAge + " " + male + " " + city);
		try {
			List<Candidate> candidates = MeetingDAO.getInstance()
					.selectCandidates(male, minAge, maxAge, country, city);
			request.setAttribute("candidates", candidates);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("incorrectData", MessageManager.getInstance()
					.getValue(MessageManager.INCORRECT_DATA));
		}
		request.setAttribute("minAge", minAge);
		request.setAttribute("maxAge", maxAge);
		if (male == null)
			male = false;
		request.setAttribute("male", male);
		if (country == null)
			country = "Любая";
		request.setAttribute("country", country);
		if (city == null)
			city = "Любой";
		request.setAttribute("city", city);
		return page;
	}
}
