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
 * Class <code>UserSubscriptionPayCommand</code> is used to pay for
 * {@link by.kovalenko.periodicals.entities.User user's}
 * {@link by.kovalenko.periodicals.entities.Subscription subscription}. Class
 * <code>UserSubscriptionPayCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class UserSubscriptionPayCommand implements ICommand {
	private static Logger log = Logger
			.getLogger(UserSubscriptionPayCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			long subscriptionId = Long.parseLong(request
					.getParameter("subscriptionId"));
			IPeriodicalsSubscriptionsDAO pDao = (IPeriodicalsSubscriptionsDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.SUBSCRIPTIONS));
			Subscription subscription = pDao.getSubscription(subscriptionId);
			subscription.setPaid(true);
			pDao.updateSubscription(subscription);
			response.sendRedirect(request.getContextPath()
					+ "/periodicals?command=userGetSubscriptions&id="
					+ request.getParameter("userId"));
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}

}
