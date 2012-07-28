package by.kovalenko.periodicals.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface <code>ICommand</code> is base interface for all classes, which can
 * be submitted a request. <code>ICommand</code> declares a method for
 * processing of the request and creating the response.
 * 
 * @author <i>Kovalenko Darya, BSU, 2012</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public interface ICommand {
	/**
	 * Method <code>execute()</code> is used to process the {@link HttpRequest
	 * request}.
	 * 
	 * @param request
	 *            Defines request.
	 * @param response
	 *            Defines response.
	 * @throws ServletException
	 *             IOException
	 * @return Returns <a href=
	 *         "http://docs.oracle.com/javase/1.4.2/docs/api/java/lang/String.html"
	 *         >String object.</a>
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/ServletException.html">ServletException</a>
	 *      <a href=
	 *      "http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/IOException.html"
	 *      >IOException</a>
	 * 
	 * 
	 * @author <i>Kovalenko Darya, BSU, 2012</i>
	 * @version <b>1.0</b>
	 * @since <b>1.0</b>
	 */

	String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
}
