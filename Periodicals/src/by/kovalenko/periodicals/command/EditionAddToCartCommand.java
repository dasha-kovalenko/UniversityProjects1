package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsEditionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsEditionsDAO;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

/**
 * Class <code>EditionAddToCartCommand</code> is used for adding {@link by.kovalenko.periodicals.domain.Edition edition} to user
 * {@link by.kovalenko.periodicals.domain.Cart cart}. Class <code>EditionAddToCartCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class EditionAddToCartCommand implements ICommand {

	private static Logger log = Logger.getLogger(EditionAddToCartCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			Long cartId = Long.parseLong(request.getParameter("cartId"));
			System.out.println(cartId);
			Long editionId = Long.parseLong(request.getParameter("editionId"));
			IPeriodicalsEditionsDAO pDao;
			pDao = new PeriodicalsEditionsDAO();
			pDao.addEditionToCart(cartId, editionId);
			response.sendRedirect(request.getContextPath()
					+ "/periodicals?command=editionList");
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;

	}

}
