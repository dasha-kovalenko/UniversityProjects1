import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CurriculumVitaeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int stepFlag;
	private CurriculumVitaeParams params;
	
	public void init() throws ServletException {
		super.init();
		stepFlag = 0;
		params = new CurriculumVitaeParams();
	}

	public void service(HttpServletRequest request, 
		       HttpServletResponse response) throws ServletException, IOException {
		          PrintWriter writer = response.getWriter();
		          HttpSession session = request.getSession();
		          writer.println("<html> <head> <title>Simple curriculum vitae maker</title> " + 
		        		  "<link href=\"calendar.css\" rel=\"stylesheet\" type=\"text/css\"> " +
		        		  "<script src=\"calendar.js\" language=\"javascript\"></script></head>");
		         
		          if (request.getParameter("startMakingCV") != null) {
		              //startMakingCV button was pressed
		        	  stepFlag = 1;
		          }else if (request.getParameter("next1") != null) {
		        	  params.setFullName(request.getParameter("fullName"));
		        	  params.setDateOfBirth(request.getParameter("dateOfBirth"));
		        	  params.setCity(request.getParameter("city"));
		        	  params.setAddress(request.getParameter("address"));
		        	  params.seteMail(request.getParameter("eMail"));
		        	  session.setAttribute("fullName", request.getParameter("fullName"));
		        	  session.setAttribute("dateOfBirth", request.getParameter("dateOfBirth"));
		        	  session.setAttribute("city", request.getParameter("city"));
		        	  session.setAttribute("address", request.getParameter("address"));
		        	  session.setAttribute("eMail", request.getParameter("eMail"));
		        	  stepFlag = 2;
		        	  
		          }else if (request.getParameter("next2") != null) {
		        	  params.setEducation(request.getParameter("education"));
		        	  session.setAttribute("education", request.getParameter("education"));
		              stepFlag = 3;
		              processResult ();
		          } else if (request.getParameter("back2") != null) {
		        	  stepFlag = 1;
		          } else if (request.getParameter("back3") != null){
		        	  //params.setEducation(request.getParameter("education"));
		        	  //session.setAttribute("education", request.getParameter("education"));
		        	  stepFlag = 2;
		          }else if (request.getParameter("finishMakingCV") != null) {
		        	  params.setWorkExperience(request.getParameter("workExperience"));
		        	  session.setAttribute("workExperience", request.getParameter("workExperience"));
		              stepFlag = 4;
		              processResult ();
		          } else if (request.getParameter("back3") != null) {
		        	  //params.setEducation(request.getParameter("education"));
		        	  //session.setAttribute("education", request.getParameter("education"));
		              stepFlag = 2;
		          } else if (request.getParameter("back4") != null){
		           	  //params.setWorkExperience(request.getParameter("workExperience"));
		        	  //session.setAttribute("workExperience", request.getParameter("workExperience"));
		 		      stepFlag = 3;
		          }	          
		          switch (stepFlag) {
		          case 0:
		        	  writer.println("<body> <table> <tr> <td bgcolor=#ffffff width=1300 align=center> <font face=\"comic sans ms\" color=#0000ff> <h2>"+
		      				"Welcome to the wizard of creating a curriculum vitae! </h2></font> </table>"+
		      		"<center> <img src=CurriculumVitae.jpg height=450 width=650 align=top /><br><br>");
		        	  writer.println("<form method=\"POST\"><button type=\"submit\" name=\"startMakingCV\" value=\"startMakingCV\" " + 
	        			  	"style=\"height: 50px; color: blue; font-size: 30; background-color: #8DB6CD; " + 
	        			  	"-moz-border-radius: 10px; -webkit-border-radius: 5px;" + 
	        			  	"border: 5px solid #001100; padding: 5px;\">Start Making CV!</button></form>");
		        	  ///writer.println("<form method=\"POST\"><button type = \"submit\" name=startMakingCV src=\"key_start2.jpg\" alt=\"Start\">");
		        	  break;
		          case 1:
		        	  Object fullName = session.getAttribute("fullName");
		        	  Object date = session.getAttribute("dateOfBirth");
		        	  Object  city= session.getAttribute("city");
		        	  Object address = session.getAttribute("address");
		        	  Object  e_mail = session.getAttribute("eMail");

		        	  if (fullName == null)
		        		  fullName = (String) "";
		        	  if (date == null)
		        		  date = (String) "";
		        	  if (city == null)
		        		  city = (String) "";
		        	  if (address == null)
		        		  address = (String) "";
		        	  if (e_mail == null)
		        		  e_mail = (String) "";
		        	  fullName = "";
		        	  writer.println("<body> <table> <tr> <td bgcolor=#ffffff width=1300 align=center> <font face=\"comic sans ms\" color=#0000ff> <h2>"+
			      				"Please, enter your personal data! </h2></font> </table>"+
			      		"<center> <img src = personalData.jpg height=300 width=650 align=top /><br><br>");
		//background = \"CurriculumVitae.jpg\"
		        	  writer.println("<table width=\"600\" border=\"5\" bordercolor = #00CDCD cellspacing=\"5\" cellpadding=\"5\">" +
	        			  "<tr> <td><form method=\"POST\">Full Name:</td> <td><br><input type=\"text\" name=\"fullName\" value=" + fullName +
	        			  "></td></tr> <tr> <td><br />Date:</td> <td> <br><input type=\"text\" name=\"dateOfBirth\" onfocus=\"showCalendarControl(this);\" value=" + date +
	        			  "></td> </tr> <tr><br> <td>City: </td> <td><br><input type=\"text\" name=\"city\" value=" + city +
	        			  "></td></tr> <tr> <br>  <td>Address: </td> <td><br><input type=\"text\" name=\"address\" value=" + address + 
	        			  "><br> <tr> <td>e-mail: </td> <td><br><input type=\"text\" name=\"eMail\" value=" + e_mail + 
	        			  "><br></td></tr><tr></tr><tr></tr></table><button type=\"submit\" name=\"next1\" value=\"next1\" " + 
	        			  "style=\"height: 50px; color: blue; font-size: 20; background-color: #8DB6CD;" +
	        			  "-moz-border-radius: 5px; -webkit-border-radius: 5px; border: 5px solid #001100;" +
	        			  "padding: 5px;\">Next >></button></form>");
		        	  break;
		          case 2:
		        	  Object ed = session.getAttribute("education");
		        	  //System.out.println(ed);
		        	  if (ed == null)
		        		  ed = (String) "";
		        	  writer.println("<body> <table> <tr> <td bgcolor=#ffffff width=1300 align=center> <font face=\"comic sans ms\" color=#0000ff> <h2>"+
			      				"Please, describe your education! </h2></font> </table>"+
			      		"<center> <img src = Education.png height=279 width=400 align=top /><br><br>");
		//background = \"CurriculumVitae.jpg\"
		        	  writer.println("<table width=\"600\" border=\"5\" bordercolor = #00CDCD cellspacing=\"5\" cellpadding=\"5\"><tr>" +
	        			  "<tr> <td><form method=\"POST\">Education:</td> <td> <br><textarea  name=\"education\" WRAP=\"virtual\" rows=\"10\" cols=\"55\">" + ed +
	        			  "</textarea></td></tr></table><button type=\"submit\" name=\"back2\" value=\"back2\" " + 
	        			  "style=\"height: 50px; color: blue; font-size: 20; background-color: #8DB6CD;" +
	        			  "-moz-border-radius: 5px; -webkit-border-radius: 5px; border: 5px solid #001100;" +
	        			  "padding: 5px;\"><< Back</button><button type=\"submit\" name=\"next2\" value=\"next2\" " + 
	        			  "style=\"height: 50px; color: blue; font-size: 20; background-color: #8DB6CD;" +
	        			  "-moz-border-radius: 5px; -webkit-border-radius: 5px; border: 5px solid #001100;" +
	        			  "padding: 5px;\">Next >></button></form>");
	
		        	  
		        	  break;
		          case 3:
		        	  Object work = session.getAttribute("workExperience");
		        	  if (work == null)
		        		  work = (String) "";
		        	  writer.println("<body> <table> <tr> <td bgcolor=#ffffff width=1300 align=center> <font face=\"comic sans ms\" color=#0000ff> <h2>"+
			      				"Please, describe your work experience! </h2></font> </table>"+
			      		"<center> <img src = work-experience.jpg height=279 width=400 align=top /><br><br>");
		        	  writer.println("<table width=\"600\" border=\"5\" bordercolor = #00CDCD cellspacing=\"5\" cellpadding=\"5\"><tr>" +
	        			  "<tr> <td><form method=\"POST\">Work:</td> <td> <br><textarea  name=\"workExperience\" WRAP=\"virtual\" rows=\"10\" cols=\"55\">" + work +
	        			  "</textarea></td></tr></table><button type=\"submit\" name=\"back3\" value=\"back3\" " + 
	        			  "style=\"height: 50px; color: blue; font-size: 20; background-color: #8DB6CD;" +
	        			  "-moz-border-radius: 5px; -webkit-border-radius: 5px; border: 5px solid #001100;" +
	        			  "padding: 5px;\"><< Back</button><button type=\"submit\" name=\"finishMakingCV\" value=\"finishMakingCV\" " + 
	        			  "style=\"height: 50px; color: blue; font-size: 20; background-color: #8DB6CD;" +
	        			  "-moz-border-radius: 5px; -webkit-border-radius: 5px; border: 5px solid #001100;" +
	        			  "padding: 5px;\">Finish</button></form>");
		        	  
		        	  break;
		          case 4:

		        	  if (session.getAttribute("fullName")!= null && !session.getAttribute("fullName").equals(" "))
		        		  writer.println("Dear " + session.getAttribute("fullName") + "! ");
		        	  writer.println ("Your curriculum vitae is:<br><br><img src=result1.png /><br><br>" +
	        			  "<form method=\"POST\"><button type=\"submit\" name=\"back4\" value=\"back3\" " + 
	        			  "style=\"height: 50px; color: blue; font-size: 20; background-color: #8DB6CD;" +
	        			  "-moz-border-radius: 5px; -webkit-border-radius: 5px; border: 5px solid #001100;" +
	        			  "padding: 5px;\"><< Back</button></form>");

		        	  break;
		          }
		          
		          writer.println("</center> </body> </html>");
		          writer.close();
		          
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	  
	}
	
	public void processResult() throws IOException {
		BufferedImage img;
		img = ImageIO.read(new File("C:/Documents and Settings/Admin/workspace/CurriculumVitaeLab4/source2.png"));

			Graphics2D g2 = (Graphics2D)img.getGraphics();
		
			Font font = new Font("Comic Sans MS", Font.PLAIN, 18);
			g2.setColor(Color.BLACK);
			g2.setFont(font);
			g2.drawString("Name:   " + params.getFullName(), 100, 320);
			g2.drawString("Date of Birth:   " + params.getDateOfBirth(), 100, 350);
			g2.drawString("City:  " + params.getCity(), 100, 380);
			g2.drawString("Address:   " + params.getAddress(), 100, 410);
			g2.drawString("e-mail:    " + params.geteMail(), 100, 440);
			g2.drawString("Education:    " + params.getEducation(), 100, 470);
			g2.drawString("Work Experience:    " + params.getWorkExperience(), 100, 500);
	
			ImageIO.write(img, "png", new File("C:/Documents and Settings/Admin/workspace/CurriculumVitaeLab4/result1.png"));
		
	}

}
