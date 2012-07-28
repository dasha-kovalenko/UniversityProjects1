package by.kovalenko.cvmaster.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CVMasterStep3Command implements ICommand{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameterMap().containsKey("step_back"))
			return getStep2PagePath();
		HttpSession session = request.getSession();
		if(!"".equals(request.getParameter("textAreaEducation")))
			session.setAttribute("textAreaEducation",
					request.getParameter("textAreaEducation"));
		return getStep4PagePath();
	}
	
	private String getStep4PagePath() {
		return "/jsp/cv-master/step4.jsp";
	}
	
	private String getStep2PagePath() {
		return "/jsp/cv-master/step2.jsp";
	}

	
}
