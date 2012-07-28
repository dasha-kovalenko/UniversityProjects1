package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsSubscriptionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsSubscriptionsDAO;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

public class UserSubscriptionDeleteCommand implements ICommand {
	private static Logger log = Logger
			.getLogger(UserSubscriptionDeleteCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsSubscriptionsDAO periodicalsSubscriptionsDAO = null;
			Long subscriptionId = Long.parseLong(request
					.getParameter("subscriptionId"));
			periodicalsSubscriptionsDAO = new PeriodicalsSubscriptionsDAO();
			periodicalsSubscriptionsDAO.deleteSubscription(subscriptionId);
			response.sendRedirect(request.getContextPath()
					+ "/periodicals?command=userGetSubscriptions&id="
					+ request.getParameter("userId"));
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}

}
