package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsSubscriptionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsSubscriptionsDAO;
import by.kovalenko.periodicals.domain.Subscription;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

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
			IPeriodicalsSubscriptionsDAO pDao = new PeriodicalsSubscriptionsDAO();
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
