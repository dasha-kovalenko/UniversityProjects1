package by.kovalenko.periodicals.command;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsEditionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsDAOFactory;
import by.kovalenko.periodicals.entities.Edition;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.PathToRedirectManager;
import by.kovalenko.periodicals.managers.TablesNamesSQLManager;

/**
 * Class <code>EditionUpdateCommand</code> is used to update an edited
 * {@link by.kovalenko.periodicals.entities.Edition existing edition}. Class
 * <code>EditionUpdateCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class EditionUpdateCommand implements ICommand {
	private static Logger log = Logger.getLogger(EditionUpdateCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			long id = Long.parseLong(request.getParameter("id"));
			String title = request.getParameter("title");
			System.out.println(request.getParameter("title"));
			String description = request.getParameter("description");
			Double price = Double.valueOf(request.getParameter("price"));
			IPeriodicalsEditionsDAO pDao = (IPeriodicalsEditionsDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.EDITIONS));

			pDao.updateEdition(new Edition(title, description, price, id));
			response.sendRedirect(request.getContextPath()
					+ PathToRedirectManager.getInstance().getValue(
							PathToRedirectManager.EDITION_SAVE));
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}
}
