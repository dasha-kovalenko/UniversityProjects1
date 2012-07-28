package by.aig.command;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PlotMasterStep3Command implements ICommand {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("step") == null
				|| !haveSessionStep1Parametres(session))
			return getStep1PagePath();
		if (session.getAttribute("step").equals("step1")
				|| !haveSessionStep2Parametres(session)) {
			session.removeAttribute("step");
			response.sendRedirect(request.getContextPath() + "/plot");
			return null;
		}
		session.setAttribute("step", request.getParameter("command"));
		if (!haveRequestStep1Parametres(request)
				|| !haveRequestStep2Parametres(request)) {
			setRequestParametres(request, session);
			response.sendRedirect(request.getContextPath()
					+ "/plot?command=step3"
					+ getSessionAttributesEncoded(session));
			return null;
		}
		return getStep3PagePath();
	}

	private String getSessionAttributesEncoded(HttpSession session)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		for (String s : Collections.list((Enumeration<String>) session
				.getAttributeNames())) {
			sb.append("&"
					+ s
					+ "="
					+ URLEncoder.encode((session.getAttribute(s).toString()),
							"utf-8"));
		}
		return sb.toString();
	}

	private boolean haveSessionStep1Parametres(HttpSession session) {
		String params[] = { "name", "description", "signature_x",
				"signature_y", "smoothing" };
		for (String s : params) {
			if (session.getAttribute(s) == null)
				return false;
		}
		return true;
	}

	private boolean haveSessionStep2Parametres(HttpSession session) {
		String params[] = { "sequence_number", "min_x", "max_x", "min_y",
				"max_y", };
		for (String s : params) {
			if (session.getAttribute(s) == null)
				return false;
		}
		for (int i = 1; i <= Integer.parseInt((String) session
				.getAttribute("sequence_number")); i++) {
			if (session.getAttribute("sequence_name_" + i) == null
					|| session.getAttribute("sequence_x_" + i) == null
					|| session.getAttribute("sequence_y_" + i) == null)
				return false;
		}
		return true;
	}

	private boolean haveRequestStep1Parametres(HttpServletRequest request) {
		String params[] = { "name", "description", "signature_x",
				"signature_y", "smoothing" };
		for (String s : params) {
			if (request.getParameter(s) == null)
				return false;
		}
		return true;
	}

	private boolean haveRequestStep2Parametres(HttpServletRequest request)
			throws NumberFormatException {
		String params[] = { "sequence_number", "min_x", "max_x", "min_y",
				"max_y", };
		for (String s : params) {
			if (request.getParameter(s) == null)
				return false;
		}
		for (int i = 1; i <= Integer.parseInt(request
				.getParameter("sequence_number")); i++) {
			if (request.getParameter("sequence_name_" + i) == null
					|| request.getParameter("sequence_x_" + i) == null
					|| request.getParameter("sequence_y_" + i) == null)
				return false;
		}
		return true;
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
