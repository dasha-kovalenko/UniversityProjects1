package by.kovalenko.periodicals.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsSubscriptionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsDAOFactory;
import by.kovalenko.periodicals.entities.Edition;
import by.kovalenko.periodicals.entities.Subscription;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.ConfigurationManager;
import by.kovalenko.periodicals.managers.TablesNamesSQLManager;

/**
 * Class <code>UserSubscriptionPayCommand</code> is used to display
 * {@link Edition editions}editions of the
 * {@link by.kovalenko.periodicals.entities.Subscription subscription}. Class
 * <code>UserSubscriptionPayCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class UserSubscriptionShowCommand implements ICommand {
	private static Logger log = Logger
			.getLogger(UserSubscriptionShowCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsSubscriptionsDAO pDao = (IPeriodicalsSubscriptionsDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.SUBSCRIPTIONS));
			Subscription subscription = pDao.getSubscription(Long
					.parseLong(request.getParameter("id")));
			List<Edition> editions;
			editions = pDao.getEditionsFromSubscription(subscription.getId());
			request.setAttribute("editions", editions);
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.USER_SUBSCRIPTION_SHOW_PAGE_PATH);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}

}
