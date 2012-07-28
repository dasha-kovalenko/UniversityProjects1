package by.kovalenko.periodicals.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import by.kovalenko.periodicals.managers.CommandManager;
import by.kovalenko.periodicals.managers.LocaleManager;

/**
 * Class <code>AuthenticationFilter</code> is used for authenticating user
 * access to different commands. Class <code>AuthenticationFilter</code>
 * implements {@link Filter <code>Filter</code>} interface.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class AuthenticationFilter implements Filter {
	private FilterConfig filterConfig = null;
	private static Logger log = Logger.getLogger(AuthenticationFilter.class);
	private final static List<String> ALLOW_ALL_COMMANDS = new ArrayList<String>();
	static {
		try {
			ALLOW_ALL_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.EDITION_LIST));
			ALLOW_ALL_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.ERROR));
			ALLOW_ALL_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_LOGIN));
			ALLOW_ALL_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_LOGOUT));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
	}
	private final static List<String> ALLOW_USER_COMMANDS = new ArrayList<String>();
	static {
		try {
			ALLOW_USER_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.EDITION_ADD_TO_CART));
			ALLOW_USER_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.EDITION_DELETE_FROM_CART));
			ALLOW_USER_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_ADD_CART_TO_SUBSCRIPTIONS));
			ALLOW_USER_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_GET_CART));
			ALLOW_USER_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_GET_SUBSCRUPTIONS));
			ALLOW_USER_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_SUBSCRIPTION_PAY));
			ALLOW_USER_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_SUBSCRIPTION_SHOW));
			ALLOW_USER_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_SUBSCRIPTION_DELETE));
			ALLOW_USER_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_SUBSCRIPTION_CREATE));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}

	}

	private final static List<String> ALLOW_ADMIN_COMMANDS = new ArrayList<String>();
	static {
		try {
			ALLOW_ADMIN_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.EDITION_CREATE));

			ALLOW_ADMIN_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.EDITION_EDIT));
			ALLOW_ADMIN_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.EDITION_DELETE));
			ALLOW_ADMIN_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.EDITION_SAVE));
			ALLOW_ADMIN_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.EDITION_UPDATE));
			ALLOW_ADMIN_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_LIST));
			ALLOW_ADMIN_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_DELETE));
			ALLOW_ADMIN_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_UPDATE));
			ALLOW_ADMIN_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_EDIT));
			ALLOW_ADMIN_COMMANDS.add(CommandManager.getInstance().getValue(
					CommandManager.USER_CREATE));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
	}

	@Override
	public void destroy() {
		this.setFilterConfig(null);

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String command = httpRequest.getParameter(CommandManager.getInstance()
				.getValue(CommandManager.COMMAND));
		if (command == null)
			command = CommandManager.getInstance().getValue(
					CommandManager.EDITION_LIST);
		boolean isAuthorized = false;
		boolean isAdmin = false;
		boolean isAccessed = false;
		HttpSession session = httpRequest.getSession();
		isAuthorized = session.getAttribute("id") != null;
		if (session.getAttribute("admin") != null)
			isAdmin = Boolean.parseBoolean(session.getAttribute("admin")
					.toString());
		if (ALLOW_ALL_COMMANDS.contains(command)
				|| (isAuthorized && ALLOW_USER_COMMANDS.contains(command))
				|| (isAdmin && ALLOW_ADMIN_COMMANDS.contains(command)))
			isAccessed = true;
		if (isAccessed)
			filterChain.doFilter(request, response);
		else
			httpResponse.sendRedirect(httpRequest.getContextPath()
					+ "/periodicals?command=error&incorrectData="
					+ LocaleManager.ACCESS_IS_DENIED);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.setFilterConfig(filterConfig);
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

}
