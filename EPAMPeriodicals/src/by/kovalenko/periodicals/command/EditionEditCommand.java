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
import by.kovalenko.periodicals.managers.ConfigurationManager;
import by.kovalenko.periodicals.managers.TablesNamesSQLManager;

/**
 * Class <code>EditionEditCommand</code> is used for editing
 * {@link by.kovalenko.periodicals.entities.Edition editions}. Class
 * <code>EditionEditCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class EditionEditCommand implements ICommand {
	private static Logger log = Logger.getLogger(EditionEditCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsEditionsDAO pDao = (IPeriodicalsEditionsDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.EDITIONS));
			int id = Integer.parseInt(request.getParameter("id"));
			Edition edition;
			edition = pDao.getEdition(id);
			request.setAttribute("edition", edition);
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.EDITION_EDIT_PAGE_PATH);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}

		return page;
	}

}
