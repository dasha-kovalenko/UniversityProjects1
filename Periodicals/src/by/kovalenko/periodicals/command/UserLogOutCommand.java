package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserLogOutCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		HttpSession session = request.getSession();
		session.setAttribute("id", null);
		session.setAttribute("username", null);
		session.setAttribute("password", null);
		session.setAttribute("cartId", null);
		session.setAttribute("admin", null);
		response.sendRedirect(request.getContextPath()
				+ "/periodicals?command=editionList");
		return page;

	}

}
