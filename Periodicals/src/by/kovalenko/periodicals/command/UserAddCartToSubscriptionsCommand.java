package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsUsersDAO;
import by.kovalenko.periodicals.dao.PeriodicalsUsersDAO;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

public class UserAddCartToSubscriptionsCommand implements ICommand {
	private static Logger log = Logger
			.getLogger(UserAddCartToSubscriptionsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			Long cartId = Long.parseLong(request.getParameter("cart_id"));
			IPeriodicalsUsersDAO pDao;
			pDao = new PeriodicalsUsersDAO();
			pDao.addCartToSubscriptionAndDelete(cartId);
			response.sendRedirect(request.getContextPath()
					+ "/periodicals?command=editionSubscriptions");
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;

	}

}
