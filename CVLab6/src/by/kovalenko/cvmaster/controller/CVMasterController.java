package by.kovalenko.cvmaster.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.kovalenko.cvmaster.command.ICommand;

public class CVMasterController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("get request");
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("post request");
		processRequest(request, response);
	}

	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("put request");
		processRequest(request, response);
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("delete request");
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		ICommand command = RequestHelper.getInstance().getCommand(request);
		page = command.execute(request, response);
		System.out.println(request.getRequestURL());
		System.out.println(request.getRequestURI());
		if (page != null) {
			RequestDispatcher requestDispatcher = getServletContext()
					.getRequestDispatcher(page);
			requestDispatcher.forward(request, response);
		}
	}

}
