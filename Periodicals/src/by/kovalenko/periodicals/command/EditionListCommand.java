package by.kovalenko.periodicals.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsEditionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsEditionsDAO;
import by.kovalenko.periodicals.domain.Edition;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;
import by.kovalenko.periodicals.managers.ConfigurationManager;

public class EditionListCommand implements ICommand {
	private static Logger log = Logger.getLogger(EditionListCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			System.out.println("hahaha");
			List<Edition> editions = null;
			IPeriodicalsEditionsDAO pDao = new PeriodicalsEditionsDAO();
			editions = pDao.listEditions();
			request.setAttribute("editions", editions);
			System.out.println("hahaha");
			page = ConfigurationManager.getInstance().getValue(
					ConfigurationManager.EDITION_LIST_PAGE_PATH);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}

		return page;
	}
}
