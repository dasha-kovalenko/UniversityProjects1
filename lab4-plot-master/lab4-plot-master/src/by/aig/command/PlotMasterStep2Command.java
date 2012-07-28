package by.aig.command;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PlotMasterStep2Command implements ICommand {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("step")==null){
			response.sendRedirect(request.getContextPath()
					+ "/plot?command=step1");
			return null;
		}
		if (request.getParameterMap().containsKey("sequence_number")
				&& request.getParameter("sequence_number").matches("[1-5]"))
			session.setAttribute("sequence_number",
					request.getParameter("sequence_number"));
		else {
			request.setAttribute("incorrectData",
					"Проверьте вводимые последовательности");
			return getStep2PagePath();
		}

		if (request.getParameterMap().containsKey("min_x")
				&& request.getParameter("min_x")
						.matches("(-?[1-9]\\d{0,2}|0)"))
			session.setAttribute("min_x", request.getParameter("min_x"));
		else {
			request.setAttribute("incorrectData",
					"Проверьте минимально допустимое значение Х");
			return getStep2PagePath();
		}

		if (request.getParameterMap().containsKey("max_x")
				&& request.getParameter("max_x")
						.matches("(-?[1-9]\\d{0,2}|0)")
				&& Integer.parseInt(request.getParameter("min_x")) < Integer
						.parseInt(request.getParameter("max_x")))
			session.setAttribute("max_x", request.getParameter("max_x"));
		else {
			request.setAttribute("incorrectData",
					"Проверьте максимально допустимое значение Х");
			return getStep2PagePath();
		}

		if (request.getParameterMap().containsKey("min_y")
				&& request.getParameter("min_y")
						.matches("(-?[1-9]\\d{0,2}|0)"))
			session.setAttribute("min_y", request.getParameter("min_y"));
		else {
			request.setAttribute("incorrectData",
					"Проверьте минимально допустимое значение Y");
			return getStep2PagePath();
		}

		if (request.getParameterMap().containsKey("max_y")
				&& request.getParameter("max_y")
						.matches("(-?[1-9]\\d{0,2}|0)")
				&& Integer.parseInt(request.getParameter("min_y")) < Integer
						.parseInt(request.getParameter("max_y")))
			session.setAttribute("max_y", request.getParameter("max_y"));
		else {
			request.setAttribute("incorrectData",
					"Проверьте максимально допустимое значение Y");
			return getStep2PagePath();
		}

		for (int i = 1; i <= Integer.parseInt(request
				.getParameter("sequence_number")); i++) {
			if (request.getParameterMap().containsKey("sequence_x_" + i)
					&& request.getParameter("sequence_x_" + i).matches(
							"(-?[1-9]\\d{0,2} |0 )*(-?[1-9]\\d{0,2}|0)")
					&& request.getParameterMap().containsKey("sequence_y_" + i)
					&& request.getParameter("sequence_y_" + i).matches(
							"(-?[1-9]\\d{0,2} |0 )*(-?[1-9]\\d{0,2}|0)")) {
				session.setAttribute("sequence_name_" + i,
						request.getParameter("sequence_name_" + i));
				session.setAttribute("sequence_x_" + i,
						request.getParameter("sequence_x_" + i));
				session.setAttribute("sequence_y_" + i,
						request.getParameter("sequence_y_" + i));
			} else {
				request.setAttribute("incorrectData",
						"Проверьте последовательность #" + i);
				return getStep2PagePath();
			}
		}
		setRequestParametres(request, session);
		session.setAttribute("step", request.getParameter("command"));
		response.sendRedirect(request.getContextPath()
				+ "/plot?command=step3");
		return null;
	}

	private void setRequestParametres(HttpServletRequest request,
			HttpSession session) {
		for (String s : Collections.list((Enumeration<String>) session
				.getAttributeNames())) {
			request.setAttribute(s, session.getAttribute(s));
		}
	}

	private String getStep1PagePath() {
		return "/jsp/plot-master/step1.jsp";
	}
	
	private String getStep2PagePath() {
		return "/jsp/plot-master/step2.jsp";
	}

	private String getStep3PagePath() {
		return "/jsp/plot-master/step3.jsp";
	}

}
