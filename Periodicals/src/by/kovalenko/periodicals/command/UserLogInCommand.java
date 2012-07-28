package by.kovalenko.periodicals.command;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsUsersDAO;
import by.kovalenko.periodicals.dao.PeriodicalsUsersDAO;
import by.kovalenko.periodicals.domain.User;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.LocaleManager;
import by.kovalenko.periodicals.security.BCrypt;

public class UserLogInCommand implements ICommand {
	private static Logger log = Logger.getLogger(UserLogInCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsUsersDAO usersDAO;
			usersDAO = new PeriodicalsUsersDAO();
			String username = request.getParameter("username");
			User user = usersDAO.getUser(username);
			if (user != null
					&& BCrypt.hashpw(request.getParameter("password"),
							user.getSalt()).equals(user.getPassword())) {
				HttpSession session = request.getSession();
				session.setAttribute("id", user.getId());
				session.setAttribute("username", user.getUsername());
				session.setAttribute("admin", user.isAdmin());
				session.setAttribute("cartId", user.getCart().getId());
				response.sendRedirect(request.getContextPath()
						+ "/periodicals?command=editionList");

			} else {
				response.sendRedirect(request.getContextPath()
						+ "/periodicals?command=error&incorrectData="
						+ LocaleManager.INCORRECT_USER_ATTRIBUTES);
				log.error(GregorianCalendar.getInstance().get(Calendar.DATE)
						+ ": "
						+ username
						+ " - "
						+ LocaleManager.getInstance().getValue(
								LocaleManager.INCORRECT_USER_ATTRIBUTES));
			}
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}
}
