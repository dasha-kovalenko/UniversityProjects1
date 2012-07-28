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
 * Class <code>EditionDeleteCommand</code> is used for deleting
 * {@link by.kovalenko.periodicals.domain.Edition edition}. Class
 * <code>EditionDeleteCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class EditionDeleteCommand implements ICommand {
	private static Logger log = Logger.getLogger(EditionDeleteCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			IPeriodicalsEditionsDAO pDao = new PeriodicalsEditionsDAO();
			pDao.deleteEdition(id);
			response.sendRedirect(request.getContextPath()
					+ "/periodicals?command=editionList");
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

}
