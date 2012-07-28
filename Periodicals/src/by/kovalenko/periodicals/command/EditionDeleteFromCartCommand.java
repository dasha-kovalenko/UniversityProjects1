package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.kovalenko.periodicals.dao.IPeriodicalsEditionsDAO;
import by.kovalenko.periodicals.dao.PeriodicalsEditionsDAO;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

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
			IPeriodicalsEditionsDAO pDao;
			pDao = new PeriodicalsEditionsDAO();
			pDao.deleteEditionFromCart(cartId, editionId);
			HttpSession session = request.getSession();
			response.sendRedirect(request.getContextPath()
					+ "/periodicals?command=userGetCart&id="
					+ session.getAttribute("id"));
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return page;

	}

}
