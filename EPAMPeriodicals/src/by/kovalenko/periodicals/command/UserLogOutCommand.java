package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.kovalenko.periodicals.managers.PathToRedirectManager;

/**
 * Class <code>UserLogOutCommand</code> is used for processing user's logging
 * out action. Class <code>UserLogOutCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class UserLogOutCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		HttpSession session = request.getSession();
		session.setAttribute("id", null);
		session.setAttribute("username", null);
		session.setAttribute("password", null);
		session.setAttribute("cartId", null);
		session.setAttribute("admin", null);
		response.sendRedirect(request.getContextPath()
				+ PathToRedirectManager.getInstance().getValue(
						PathToRedirectManager.EDITION_SAVE));
		return page;

	}
}
