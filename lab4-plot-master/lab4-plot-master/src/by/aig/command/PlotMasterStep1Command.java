package by.aig.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PlotMasterStep1Command implements ICommand {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(!request.getParameterMap().containsKey("name"))
			return getStep1PagePath();
		if (request.getParameterMap().containsKey("name")
				&& request.getParameter("name").matches(
						"[0-9a-zA-Zа-яА-Я ]{3,25}"))
			session.setAttribute("name", request.getParameter("name"));
		else{
			request.setAttribute("incorrectData", "Проверьте название графика");
			return getStep1PagePath();
		}
		
		if (request.getParameterMap().containsKey("description")
				&& request.getParameter("description").matches(
						"[0-9a-zA-Zа-яА-Я ]{3,50}"))
			session.setAttribute("description",
					request.getParameter("description"));
		else{
			request.setAttribute("incorrectData", "Проверьте краткое описание графика");
			return getStep1PagePath();
		}
		
		if (request.getParameterMap().containsKey("signature_x")
				&& request.getParameter("signature_x").matches(
						"[0-9a-zA-Zа-яА-Я ]{3,25}"))
			session.setAttribute("signature_x",
					request.getParameter("signature_x"));
		else{
			request.setAttribute("incorrectData", "Проверьте подпись оси Х");
			return getStep1PagePath();
		}
		
		if (request.getParameterMap().containsKey("signature_y")
				&& request.getParameter("signature_y").matches(
						"[0-9a-zA-Zа-яА-Я ]{3,25}"))
			session.setAttribute("signature_y",
					request.getParameter("signature_y"));
		else{
			request.setAttribute("incorrectData", "Проверьте подпись оси Y");
			return getStep1PagePath();
		}
		
		if (request.getParameterMap().containsKey("smoothing")
				&& ("true".equals(request.getParameter("smoothing"))
						|| "false".equals(request.getParameter("smoothing"))))
			session.setAttribute("smoothing", request.getParameter("smoothing"));
		else{
			request.setAttribute("incorrectData", "Проверьте значение сглаживания");
			return getStep1PagePath();
		}
		
		if (request.getParameterMap().containsKey("signature_points"))
			session.setAttribute("signature_points", "true");
		else
			session.setAttribute("signature_points", "false");
		session.setAttribute("step", request.getParameter("command"));
		return getStep2PagePath();
	}

	private String getStep1PagePath() {
		return "/jsp/plot-master/step1.jsp";
	}

	private String getStep2PagePath() {
		return "/jsp/plot-master/step2.jsp";
	}

}
