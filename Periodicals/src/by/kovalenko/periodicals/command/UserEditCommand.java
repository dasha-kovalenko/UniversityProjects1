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

public class UserEditCommand implements ICommand {

	private static Logger log = Logger.getLogger(UserEditCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsUsersDAO pDao;
			pDao = new PeriodicalsUsersDAO();
			Long id = Long.parseLong(request.getParameter("id"));
			User user;
			user = pDao.getUser(id);
			request.setAttribute("user", user);
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.USER_EDIT_PAGE_PATH);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}

}
