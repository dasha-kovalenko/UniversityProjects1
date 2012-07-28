package by.kovalenko.cvmaster.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CVMasterStep1Command implements ICommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		 return "/jsp/cv-master/step2.jsp";
	}
}
