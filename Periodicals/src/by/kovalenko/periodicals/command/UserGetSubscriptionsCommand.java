package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsUsersDAO;
import by.kovalenko.periodicals.dao.PeriodicalsUsersDAO;
import by.kovalenko.periodicals.domain.User;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.ConfigurationManager;

public class UserGetSubscriptionsCommand implements ICommand {
	private static Logger log = Logger.getLogger(UserGetSubscriptionsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsUsersDAO usersDAO;
			usersDAO = new PeriodicalsUsersDAO();
			User user;
			user = usersDAO.getUser(Long.parseLong(request.getParameter("id")));
			request.setAttribute("user", user);
			request.setAttribute("subscriptions", user.getSubsciptions());
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.USER_GET_SUBSCRIPTIONS_PAGE_PATH);
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}

		return page;
	}

}
