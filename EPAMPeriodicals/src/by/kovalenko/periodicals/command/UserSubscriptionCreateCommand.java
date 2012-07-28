package by.kovalenko.periodicals.command;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsUsersDAO;
import by.kovalenko.periodicals.dao.PeriodicalsDAOFactory;
import by.kovalenko.periodicals.entities.Subscription;
import by.kovalenko.periodicals.entities.User;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.TablesNamesSQLManager;

/**
 * Class <code>UserSubscriptionCreateCommand</code> is used for creating user's
 * {@link Subscription subscription}. Class
 * <code>UserSubscriptionCreateCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class UserSubscriptionCreateCommand implements ICommand {
	private static Logger log = Logger
			.getLogger(UserSubscriptionCreateCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {

			Long id = Long.parseLong(request.getParameter("id"));
			Date startDate = Date.valueOf((String) request
					.getParameter("startDate"));
			Date finishDate = Date.valueOf((String) request
					.getParameter("finishDate"));
			Double price = Double.parseDouble(request.getParameter("price"));
			IPeriodicalsUsersDAO usersDAO = (IPeriodicalsUsersDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.USERS));
			User user = usersDAO.getUser(id);
			Subscription subscription = new Subscription(startDate, finishDate,
					false, user.getId(), price);
			usersDAO.createSubscription(user.getId(), subscription);
			usersDAO.addEditionsToSubscriptionFromCart(usersDAO
					.getLastSubscriptionFromUser(user.getId()), user.getCart()
					.getId());
			usersDAO.clearCart(user.getCart().getId());
			response.sendRedirect(request.getContextPath()
					+ "/periodicals?command=userGetSubscriptions&id="
					+ user.getId());
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;

	}

}
