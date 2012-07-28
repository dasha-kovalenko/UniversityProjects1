package by.kovalenko.cvmaster.command;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CVMasterStep2Command implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println("111");
		if (!"".equals(request.getParameter("fieldName")))
			session.setAttribute("fieldName", request.getParameter("fieldName"));
		else {
			request.setAttribute("incorrectData", "Проверьте вводимые данные");
			return getStep2PagePath();
		}

		if (!"".equals(request.getParameter("fieldDate")))
			session.setAttribute("fieldDate", request.getParameter("fieldDate"));
		else {
			request.setAttribute("incorrectData", "Проверьте вводимые данные");
			return getStep2PagePath();
		}
		if (!"".equals(request.getParameter("fieldCity")))
			session.setAttribute("fieldCity", request.getParameter("fieldCity"));
		else {
			request.setAttribute("incorrectData", "Проверьте вводимые данные");
			return getStep2PagePath();
		}

		if (!"".equals(request.getParameter("fieldAddress")))
			session.setAttribute("fieldAddress",
					request.getParameter("fieldAddress"));
		else {
			request.setAttribute("incorrectData", "Проверьте вводимые данные");
			return getStep2PagePath();
		}
		System.out.println(request.getParameter("fieldEMail"));
		if (!"".equals(request.getParameter("fieldEMail"))) {
			session.setAttribute("fieldEMail",
					request.getParameter("fieldEMail"));
		} else {
			request.setAttribute("incorrectData", "Проверьте вводимые данные");
			return getStep2PagePath();
		}
		setRequestParametres(request, session);
		session.setAttribute("step", request.getParameter("command"));
		return getStep3PagePath();
	}

	@SuppressWarnings("unchecked")
	private void setRequestParametres(HttpServletRequest request,
			HttpSession session) {
		for (String s : Collections.list((Enumeration<String>) session
				.getAttributeNames())) {
			request.setAttribute(s, session.getAttribute(s));
		}
	}

	private String getStep2PagePath() {
		return "/jsp/cv-master/step2.jsp";
	}

	private String getStep3PagePath() {
		return "/jsp/cv-master/step3.jsp";
	}

}
