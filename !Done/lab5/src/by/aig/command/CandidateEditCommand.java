package by.aig.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.aig.dao.MeetingDAO;
import by.aig.domain.Candidate;
import by.aig.manager.ConfigurationManager;
import by.aig.manager.MessageManager;

public class CandidateEditCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean isCorrect = true;
		int id;
		if (request.getParameter("id") != null)
			id = Integer.parseInt(request.getParameter("id"));
		else {
			id = 0;
			isCorrect = false;
		}
		int age;
		if (request.getParameterMap().containsKey("age")
				&& request.getParameter("age").matches("[1-9][0-9]"))
			age = Integer.parseInt(request.getParameter("age"));
		else {
			age = 18;
			isCorrect = false;
		}
		String name = "";
		if (request.getParameterMap().containsKey("name")
				&& request.getParameter("name").matches("[а-яА-Яa-zA-Z]{1,10}"))
			name = request.getParameter("name");
		else {
			isCorrect = false;
		}
		Boolean male = false;
		if (request.getParameter("male") != null)
			male = Boolean.parseBoolean(request.getParameter("male"));
		else {
			isCorrect = false;
		}
		String country = request.getParameter("country");
		if ("Любая".equals(country))
			country = null;
		String city = request.getParameter("city");
		if ("Любой".equals(city))
			city = null;
		String phone = "";
		if (request.getParameterMap().containsKey("phone")
				&& request.getParameter("phone").matches("[0-9]{1,10}"))
			phone = request.getParameter("phone");
		else {
			isCorrect = false;
		}
		System.out.println(age + " " + name + " " + male + " " + country + " "
				+ city + " " + phone + " " + isCorrect);
		String page = null;
		Candidate candidate = new Candidate(id, age, name, male, country, city,
				phone);
		if (isCorrect)
			try {
				MeetingDAO.getInstance().updateCandidate(candidate);
				response.sendRedirect(request.getContextPath()
						+ "/candidate?command=candidateList");
			} catch (SQLException e) {
				e.printStackTrace();
				isCorrect = false;
			}
		if (!isCorrect) {
			request.setAttribute("incorrectData", MessageManager.getInstance()
					.getValue(MessageManager.INCORRECT_DATA));
			request.setAttribute("candidate", candidate);
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.CANDIDATE_EDIT_PAGE_PATH);
		}
		return page;
	}

}
