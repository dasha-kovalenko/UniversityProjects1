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
 * Class <code>UserGetCartCommand</code> is used to display
 * {@link by.kovalenko.periodicals.entities.User user's}
 * {@link by.kovalenko.periodicals.entities.Cart cart} . Class
 * <code>UserGetCartCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class UserGetCartCommand implements ICommand {
	private static Logger log = Logger.getLogger(UserGetCartCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsUsersDAO usersDAO = (IPeriodicalsUsersDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.USERS));
			User user;
			user = usersDAO.getUser(Long.parseLong(request.getParameter("id")));
			request.setAttribute("user", user);
			request.setAttribute("price",
					usersDAO.getCartPrice(user.getCart().getId()));
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.USER_GET_CART_PAGE_PATH);
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;

	}

}
