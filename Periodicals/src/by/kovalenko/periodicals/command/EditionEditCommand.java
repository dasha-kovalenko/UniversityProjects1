package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsEditionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsEditionsDAO;
import by.kovalenko.periodicals.domain.Edition;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.ConfigurationManager;

public class EditionEditCommand implements ICommand {
	private static Logger log = Logger.getLogger(EditionEditCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			IPeriodicalsEditionsDAO pDao;
			pDao = new PeriodicalsEditionsDAO();
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
