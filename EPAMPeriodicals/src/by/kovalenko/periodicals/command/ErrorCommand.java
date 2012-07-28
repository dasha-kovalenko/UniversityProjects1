package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.kovalenko.periodicals.managers.ConfigurationManager;
import by.kovalenko.periodicals.managers.LocaleManager;

/**
 * Class <code>ErrorCommand</code> is used to generate error JSP-page. Class
 * <code>EditionUpdateCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class ErrorCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("incorrectData", LocaleManager.getInstance()
				.getValue(request.getParameter("incorrectData")));

		String page = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.ERROR_PAGE_PATH);
		return page;

	}

}
