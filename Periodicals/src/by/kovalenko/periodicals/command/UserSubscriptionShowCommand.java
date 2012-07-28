package by.kovalenko.periodicals.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsSubscriptionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsSubscriptionsDAO;
import by.kovalenko.periodicals.domain.Edition;
import by.kovalenko.periodicals.domain.Subscription;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.ConfigurationManager;

public class UserSubscriptionShowCommand implements ICommand {
	private static Logger log = Logger
			.getLogger(UserSubscriptionShowCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsSubscriptionsDAO pDAO = new PeriodicalsSubscriptionsDAO();
			Subscription subscription = pDAO.getSubscription(Long
					.parseLong(request.getParameter("id")));
			List<Edition> editions;
			editions = pDAO.getEditionsFromSubscription(subscription.getId());
			request.setAttribute("editions", editions);
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.USER_SUBSCRIPTION_SHOW_PAGE_PATH);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}

}
