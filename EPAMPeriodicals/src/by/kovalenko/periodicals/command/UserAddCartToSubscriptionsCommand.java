package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import by.kovalenko.periodicals.dao.IPeriodicalsUsersDAO;
import by.kovalenko.periodicals.dao.PeriodicalsDAOFactory;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.PathToRedirectManager;
import by.kovalenko.periodicals.managers.TablesNamesSQLManager;

/**
 * Class <code>UserAddCartToSubscriptionsCommand</code> is used for adding
 * {@link by.kovalenko.periodicals.entities.Edition editions} of the users's
 * {@link by.kovalenko.periodicals.entities.Cart cart} to the list of
 * {@link by.kovalenko.periodicals.entities.Subscription subscriptions}. Class
 * <code>UserAddCartToSubscriptionsCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class UserAddCartToSubscriptionsCommand implements ICommand {
	private static Logger log = Logger
			.getLogger(UserAddCartToSubscriptionsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			Long cartId = Long.parseLong(request.getParameter("cart_id"));
			IPeriodicalsUsersDAO pDao = (IPeriodicalsUsersDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.USERS));

			pDao.addCartToSubscriptionAndDelete(cartId);
			response.sendRedirect(request.getContextPath()
					+ PathToRedirectManager
							.getInstance()
							.getValue(PathToRedirectManager.USER_ADD_CART_TO_SUBSCRIPTIONS));
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;

	}

}
