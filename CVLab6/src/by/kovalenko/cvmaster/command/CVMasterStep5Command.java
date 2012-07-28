package by.kovalenko.cvmaster.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CVMasterStep5Command implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameterMap().containsKey("step_back"))
			return getStep4PagePath();
		return null;
	}

	private String getStep4PagePath() {
		return "/jsp/cv-master/step4.jsp";
	}
}
