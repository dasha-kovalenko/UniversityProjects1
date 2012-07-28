package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.kovalenko.periodicals.managers.ConfigurationManager;
import by.kovalenko.periodicals.managers.LocaleManager;

public class ErrorCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("incorrectData", LocaleManager.getInstance()
				.getValue(request.getParameter("incorrectData")));
		
		String page = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.ERROR_PAGE_PATH);
		return page;

	}

}
