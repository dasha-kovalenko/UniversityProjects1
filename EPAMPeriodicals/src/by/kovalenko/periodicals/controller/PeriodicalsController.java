package by.kovalenko.periodicals.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import by.kovalenko.periodicals.command.ICommand;
import by.kovalenko.periodicals.connectionpool.ConnectionPool;
import by.kovalenko.periodicals.exceptions.ConnectionPoolException;
import by.kovalenko.periodicals.managers.ConfigurationManager;

public class PeriodicalsController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PeriodicalsController.class);

	@Override
	public void init() throws ServletException {
		super.init();
		Properties properties = new Properties();

		try {
			String pathToProperties = new String(ConfigurationManager
					.getInstance().getValue(ConfigurationManager.LOG_PAGE_PATH));
			properties.load(new FileInputStream(pathToProperties));
			PropertyConfigurator.configure(properties);
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		// определение команды, пришедшей из JSP
		ICommand command = CommandFactory.getInstance().getCommand(request);

		/*
		 * вызов реализованного метода execute() интерфейса ICommand и передача
		 * параметров классу-обработчику конкретной команды метод возвращает
		 * страницу ответа
		 */
		page = command.execute(request, response);
		if (page != null) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher(page);
			// вызов страницы ответа на запрос
			requestDispatcher.forward(request, response);
		}
	}

	@Override
	public void destroy() {
		ConnectionPool pool;
		try {
			pool = ConnectionPool.getInstanse();
			pool.releasePool();
		} catch (ConnectionPoolException e1) {
			log.error(e1.getMessage(), e1);
		}
		super.destroy();
	}

}
