package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.kovalenko.periodicals.managers.ConfigurationManager;
/**
 * Class <code>UserCreateCommand</code> is used for creating
 * {@link by.kovalenko.periodicals.entities.User user}. Class
 * <code>UserCreateCommand</code> implements {@link ICommand
 * <code>ICommand</code>} interface.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class UserCreateCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = ConfigurationManager.getInstance().getValue(
				ConfigurationManager.USER_CREATE_PAGE_PATH);
		return page;

	}

}
