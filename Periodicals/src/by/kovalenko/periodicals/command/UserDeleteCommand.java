package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import by.kovalenko.periodicals.dao.IPeriodicalsUsersDAO;
import by.kovalenko.periodicals.dao.PeriodicalsUsersDAO;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

public class UserDeleteCommand implements ICommand {
	private static Logger log = Logger.getLogger(EditionDeleteCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			IPeriodicalsUsersDAO pDao = new PeriodicalsUsersDAO();
			pDao.deleteUser(id);
			response.sendRedirect(request.getContextPath()
					+ "/periodicals?command=userList");
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
		} catch (PeriodicalsDAOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
