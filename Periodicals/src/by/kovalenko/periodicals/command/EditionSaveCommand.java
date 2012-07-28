package by.kovalenko.periodicals.command;

import org.apache.log4j.Logger;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.kovalenko.periodicals.dao.IPeriodicalsEditionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsEditionsDAO;
import by.kovalenko.periodicals.domain.Edition;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

public class EditionSaveCommand implements ICommand {
	private static Logger log = Logger.getLogger(EditionSaveCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			Double price = Double.valueOf(request.getParameter("price"));
			IPeriodicalsEditionsDAO pDao = new PeriodicalsEditionsDAO();
			pDao.saveEdition(new Edition(title, description, price));
			response.sendRedirect(request.getContextPath()
					+ "/periodicals?command=editionList");
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;
	}
}
