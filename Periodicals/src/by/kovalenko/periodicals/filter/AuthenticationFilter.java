package by.kovalenko.periodicals.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.kovalenko.periodicals.managers.LocaleManager;

/**
 * Class <code>AuthenticationFilter</code> is used for authenticating user access 
 * to different commands. Class <code>AuthenticationFilter</code> implements {@link Filter
 * <code>Filter</code>} interface.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class AuthenticationFilter implements Filter {
	private FilterConfig filterConfig = null;
	private final ArrayList<String> ALLOW_ALL_COMMANDS = new ArrayList<String>();
	{
		ALLOW_ALL_COMMANDS.add("editionList");
		ALLOW_ALL_COMMANDS.add("error");
		ALLOW_ALL_COMMANDS.add("userLogIn");
		ALLOW_ALL_COMMANDS.add("userLogOut");
	}
	private final ArrayList<String> ALLOW_USER_COMMANDS = new ArrayList<String>();
	{
		ALLOW_USER_COMMANDS.add("editionAddToCart");
		ALLOW_USER_COMMANDS.add("editionDeleteFromCart");
		ALLOW_USER_COMMANDS.add("userAddCartToSubscriptions");
		ALLOW_USER_COMMANDS.add("userGetCart");
		ALLOW_USER_COMMANDS.add("userGetSubscriptions");
		ALLOW_USER_COMMANDS.add("userSubscriptionPay");
		ALLOW_USER_COMMANDS.add("userSubscriptionShow");
		ALLOW_USER_COMMANDS.add("userSubscriptionDelete");
		ALLOW_USER_COMMANDS.add("userSubscriptionCreate");

	}

	private final ArrayList<String> ALLOW_ADMIN_COMMANDS = new ArrayList<String>();
	{
		ALLOW_ADMIN_COMMANDS.add("editionCreate");
		ALLOW_ADMIN_COMMANDS.add("editionEdit");
		ALLOW_ADMIN_COMMANDS.add("editionDelete");
		ALLOW_ADMIN_COMMANDS.add("editionSave");
		ALLOW_ADMIN_COMMANDS.add("editionUpdate");
		ALLOW_ADMIN_COMMANDS.add("userList");
		ALLOW_ADMIN_COMMANDS.add("userDelete");
		ALLOW_ADMIN_COMMANDS.add("userUpdate");
		ALLOW_ADMIN_COMMANDS.add("userEdit");
		ALLOW_ADMIN_COMMANDS.add("userCreate");
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
		String command = httpRequest.getParameter("command");
		if (command == null)
			command = "editionList";
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
