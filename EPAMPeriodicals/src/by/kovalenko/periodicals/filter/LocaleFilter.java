package by.kovalenko.periodicals.filter;

import java.io.IOException;
import java.util.Map;

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
 * Class <code>LocaleFilter</code> checks if locale exists in the list
 * of parameters and adds it there. Class <code>LocaleFilter</code> implements
 * {@link Filter <code>Filter</code>} interface.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class LocaleFilter implements Filter {

	private FilterConfig filterConfig;
	private String defaultLocale;

	@Override
	public void destroy() {
		this.setFilterConfig(null);

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		if (session.getAttribute("locale") == null){
			session.setAttribute("locale", defaultLocale);
		}
		if (httpRequest.getParameter("locale") == null) {
			String uri = httpRequest.getRequestURL().append("?")
					.append(getQueryString(httpRequest)).append("locale=")
					.append(session.getAttribute("locale")).toString();
			httpResponse.sendRedirect(uri);
		} else {
			LocaleManager.getInstance().setLocale(
					request.getParameter("locale"));
			session.setAttribute("locale", httpRequest.getParameter("locale"));
			chain.doFilter(request, response);
		}
	}

	@SuppressWarnings("unchecked")
	private String getQueryString(HttpServletRequest request) {
		StringBuilder query = new StringBuilder();
		for (Map.Entry<String, String[]> entry : ((Map<String, String[]>) request
				.getParameterMap()).entrySet()) {
			query.append(entry.getKey()).append("=")
					.append(entry.getValue()[0]).append("&");
		}
		return query.toString();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.setFilterConfig(filterConfig);
		defaultLocale = filterConfig.getInitParameter("defaultLocale");
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

}
