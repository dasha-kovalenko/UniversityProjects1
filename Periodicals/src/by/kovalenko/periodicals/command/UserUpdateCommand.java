package by.kovalenko.periodicals.command;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import by.kovalenko.periodicals.dao.IPeriodicalsUsersDAO;
import by.kovalenko.periodicals.dao.PeriodicalsUsersDAO;
import by.kovalenko.periodicals.domain.User;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.LocaleManager;
import by.kovalenko.periodicals.security.BCrypt;

public class UserUpdateCommand implements ICommand {
	private static Logger log = Logger.getLogger(UserUpdateCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			long id = Long.parseLong(request.getParameter("id"));
			String username = request.getParameter("username");
			Boolean isAdmin = Boolean.getBoolean(request.getParameter("admin"));
			String password = request.getParameter("password");
			String passwordConfirmation = request
					.getParameter("passwordConfirmation");
			if (password.equals(passwordConfirmation)) {
				String salt = BCrypt.gensalt();
				password = BCrypt.hashpw(password, salt);
				IPeriodicalsUsersDAO pDao = new PeriodicalsUsersDAO();
				pDao.updateUser(new User(id, username, password, isAdmin, salt));
				response.sendRedirect(request.getContextPath()
						+ "/periodicals?command=userList");
			} else {
				log.error(GregorianCalendar.getInstance().get(Calendar.DATE)
						+ ": "
						+ username
						+ " - "
						+ LocaleManager.getInstance().getValue(
								LocaleManager.INCORRECT_USER_ATTRIBUTES));
				response.sendRedirect(request.getContextPath()
						+ "/periodicals?command=error&incorrectData="
						+ LocaleManager.INCORRECT_USER_ATTRIBUTES);
			}
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}
}
