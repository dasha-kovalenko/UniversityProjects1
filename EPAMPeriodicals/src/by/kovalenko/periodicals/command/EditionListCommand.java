package by.kovalenko.periodicals.command;

import java.io.IOException;
import java.util.List;

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
 * Class <code>EditionListCommand</code> is used to display a list of
 * {@link by.kovalenko.periodicals.entities.Edition existing editions}. Class
 * <code>EditionListCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface. Log4j is used for logging error messages.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class EditionListCommand implements ICommand {
	private static Logger log = Logger.getLogger(EditionListCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			List<Edition> editions = null;
			IPeriodicalsEditionsDAO pDao = (IPeriodicalsEditionsDAO) PeriodicalsDAOFactory
					.getInstance().getDAO(
							TablesNamesSQLManager.getInstance().getValue(
									TablesNamesSQLManager.EDITIONS));
			editions = pDao.listEditions();
			request.setAttribute("editions", editions);
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.EDITION_LIST_PAGE_PATH);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}

		return page;
	}
}
