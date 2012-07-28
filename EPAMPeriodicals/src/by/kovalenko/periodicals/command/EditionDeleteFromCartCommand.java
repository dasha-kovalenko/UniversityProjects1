package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsEditionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsDAOFactory;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.PathToRedirectManager;
import by.kovalenko.periodicals.managers.TablesNamesSQLManager;

/**
 * Class <code>EditionDeleteFromCartCommand</code> is used for deleting
 * {@link by.kovalenko.periodicals.entities.Edition edition} from user's
 * {@link by.kovalenko.periodicals.entities.Cart cart}. Class
 * <code>EditionDeleteFromCartCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class EditionDeleteFromCartCommand implements ICommand {

	private static Logger log = Logger
			.getLogger(EditionDeleteFromCartCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			Long cartId = Long.parseLong(request.getParameter("cartId"));
			Long editionId = Long.parseLong(request.getParameter("editionId"));
			IPeriodicalsEditionsDAO pDao = (IPeriodicalsEditionsDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.EDITIONS));
			pDao.deleteEditionFromCart(cartId, editionId);
			HttpSession session = request.getSession();
			response.sendRedirect(request.getContextPath()
					+ PathToRedirectManager.getInstance().getValue(
							PathToRedirectManager.EDITION_DELETE_FROM_CART)
					+ session.getAttribute("id"));
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;

	}

}
