package by.kovalenko.cvmaster.command;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CVMasterStep4Command implements ICommand{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameterMap().containsKey("step_back"))
			return getStep3PagePath();
		HttpSession session = request.getSession();
		if(!"".equals(request.getParameter("textAreaWork")))
			session.setAttribute("textAreaWork",
					request.getParameter("textAreaWork"));
		request.setAttribute("fieldName", session.getAttribute("fieldName"));
		request.setAttribute("fieldDate", session.getAttribute("fieldDate"));
		request.setAttribute("fieldCity", session.getAttribute("fieldCity"));
		request.setAttribute("fieldAddress", session.getAttribute("fieldAddress"));
		request.setAttribute("fieldEMail", session.getAttribute("fieldEMail"));
		System.out.println( session.getAttribute("fieldEMail"));
		request.setAttribute("textAreaEducation", session.getAttribute("textAreaEducation"));
		request.setAttribute("textAreaWork", session.getAttribute("textAreaWork"));
		//processResult(request);
		return getStep5PagePath();
	}

	public void processResult(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		BufferedImage img;
		img = ImageIO.read(new File("C:/Documents and Settings/Admin/workspace/CVLab4/source2.png"));

		Graphics2D g2 = (Graphics2D)img.getGraphics();
	
		Font font = new Font("Comic Sans MS", Font.PLAIN, 18);
		g2.setColor(Color.BLACK);
		g2.setFont(font);
		g2.drawString("Name:   " + session.getAttribute("fieldName"), 100, 320);
		g2.drawString("Date of Birth:   " + session.getAttribute("fieldDate"), 100, 350);
		g2.drawString("City:  " + session.getAttribute("fieldCity"), 100, 380);
		g2.drawString("Address:   " + session.getAttribute("fieldAddress"), 100, 410);
		g2.drawString("e-mail:    " + session.getAttribute("fieldE-Mail"), 100, 440);
		g2.drawString("Education:    " + session.getAttribute("textAreaEducation"), 100, 470);
		g2.drawString("Work Experience:    " + session.getAttribute("textAreaWork"), 100, 500);

		//ImageIO.write(img, "png", new File("C:/Documents and Settings/Admin/workspace/CVLab4/WebContent/images/result1.png"));
		
	}
	
	private String getStep3PagePath() {
		return "/jsp/cv-master/step3.jsp";
	}
	
	private String getStep5PagePath() {
		return "/jsp/cv-master/step5.jsp";
	}
}
