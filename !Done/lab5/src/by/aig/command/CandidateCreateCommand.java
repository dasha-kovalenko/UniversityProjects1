package by.aig.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.aig.manager.ConfigurationManager;

public class CandidateCreateCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.CANDIDATE_CREATE_PAGE_PATH);
		return page;
	}

}
