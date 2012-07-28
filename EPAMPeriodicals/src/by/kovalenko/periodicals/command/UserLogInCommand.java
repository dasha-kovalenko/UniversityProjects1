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
import by.kovalenko.periodicals.dao.PeriodicalsDAOFactory;
import by.kovalenko.periodicals.entities.User;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.LocaleManager;
import by.kovalenko.periodicals.managers.PathToRedirectManager;
import by.kovalenko.periodicals.managers.TablesNamesSQLManager;
import by.kovalenko.periodicals.security.BCrypt;

/**
 * Class <code>UserLogInCommand</code> is used for processing user's logging in
 * action. Class <code>UserLogInCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class UserLogInCommand implements ICommand {
	private static Logger log = Logger.getLogger(UserLogInCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsUsersDAO usersDAO = (IPeriodicalsUsersDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.USERS));
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
						+ PathToRedirectManager.getInstance().getValue(
								PathToRedirectManager.EDITION_SAVE));

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
