package by.aig.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.aig.dao.MeetingDAO;
import by.aig.domain.Candidate;
import by.aig.manager.ConfigurationManager;

public class CandidateGetCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		int id;
		if (request.getParameter("id") != null)
			id = Integer.parseInt(request.getParameter("id"));
		else
			id = 0;
		Candidate candidate;
		try {
			candidate = MeetingDAO.getInstance().getCandidate(id);
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.CANDIDATE_EDIT_PAGE_PATH);
			request.setAttribute("candidate", candidate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return page;
	}

}
