package by.kovalenko.periodicals.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import by.kovalenko.periodicals.managers.LocaleManager;

/**
 * Class <code>MessageFilter</code> checks if message exists in the list of
 * parameters and sets attribute <code>message</code> from properties. Class
 * <code>MessageFilter</code> implements {@link Filter <code>Filter</code>}
 * interface.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class MessageFilter implements Filter {

	private FilterConfig filterConfig;

	@Override
	public void destroy() {
		this.setFilterConfig(null);

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest.getParameter("incorrectData") != null) {
			request.setAttribute("incorrectData", LocaleManager.getInstance()
					.getValue(httpRequest.getParameter("incorrectData")));
		}
		chain.doFilter(request, response);
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
