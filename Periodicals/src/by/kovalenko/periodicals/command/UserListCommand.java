package by.kovalenko.periodicals.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsUsersDAO;
import by.kovalenko.periodicals.dao.PeriodicalsUsersDAO;
import by.kovalenko.periodicals.domain.User;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.ConfigurationManager;

public class UserListCommand implements ICommand {

	private static Logger log = Logger.getLogger(UserListCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			List<User> users = null;
			IPeriodicalsUsersDAO pDao = new PeriodicalsUsersDAO();
			users = pDao.listUsers();
			request.setAttribute("users", users);
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.USER_LIST_PAGE_PATH);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}
}
