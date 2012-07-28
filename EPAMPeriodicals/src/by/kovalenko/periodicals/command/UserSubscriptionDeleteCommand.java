package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsSubscriptionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsDAOFactory;
import by.kovalenko.periodicals.entities.Subscription;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.TablesNamesSQLManager;

/**
 * Class <code>UserSubscriptionDeleteCommand</code> is used for deleting user's
 * {@link Subscription subscription}. Class
 * <code>UserSubscriptionDeleteCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class UserSubscriptionDeleteCommand implements ICommand {
	private static Logger log = Logger
			.getLogger(UserSubscriptionDeleteCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			Long subscriptionId = Long.parseLong(request
					.getParameter("subscriptionId"));
			IPeriodicalsSubscriptionsDAO pDao = (IPeriodicalsSubscriptionsDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.SUBSCRIPTIONS));
			pDao.deleteSubscription(subscriptionId);
			response.sendRedirect(request.getContextPath()
					+ "/periodicals?command=userGetSubscriptions&id="
					+ request.getParameter("userId"));
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}

}
