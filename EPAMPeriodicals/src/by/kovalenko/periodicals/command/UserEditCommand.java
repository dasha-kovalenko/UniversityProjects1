package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsUsersDAO;
import by.kovalenko.periodicals.dao.PeriodicalsDAOFactory;
import by.kovalenko.periodicals.entities.User;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.ConfigurationManager;
import by.kovalenko.periodicals.managers.TablesNamesSQLManager;

/**
 * Class <code>UserEditCommand</code> is used for editing
 * {@link by.kovalenko.periodicals.entities.User users}. Class
 * <code>UserEditCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class UserEditCommand implements ICommand {

	private static Logger log = Logger.getLogger(UserEditCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsUsersDAO pDao = (IPeriodicalsUsersDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.USERS));
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
