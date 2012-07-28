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



public class TemplateBirthdayServlet extends HttpServlet {
	
	int stepFlag;
	InvitationParams params;

	public void init() throws ServletException {
		super.init();
		stepFlag=0;
		params = new InvitationParams();
	}


		public void service(HttpServletRequest request, 
	       HttpServletResponse response)
	               throws ServletException, IOException {
	          PrintWriter writer = response.getWriter();
	          HttpSession session = request.getSession();
	          writer.println("<html> <head> <title>Simple birthday party invite maker</title> " + 
	        		  "<link href=\"calendar.css\" rel=\"stylesheet\" type=\"text/css\"> " +
	        		  "<script src=\"calendar.js\" language=\"javascript\"></script></head> ");
	          writer.println("<body> <center> <img src=head.jpg height=100 width=800 align=top /><br><br>");
	          
	          
	          if (request.getParameter("makeInvite") != null) {
	              //makeInvite button was pressed
	        	  stepFlag = 1;
	          }else if (request.getParameter("next1") != null) {
	        	  params.setYourName(request.getParameter("yourName"));
	        	  params.setFriendName(request.getParameter("friendName"));
	        	  params.setColor(request.getParameter("color"));
	        	  session.setAttribute("yourName", request.getParameter("yourName"));
	        	  session.setAttribute("friendName", request.getParameter("friendName"));
	        	  session.setAttribute("color", request.getParameter("color"));
	        	  stepFlag = 2;
	        	  
	          }else if (request.getParameter("next2") != null) {
	        	  params.setDate(request.getParameter("date"));
	        	  params.setTime(request.getParameter("time"));
	        	  params.setPlace(request.getParameter("place"));
	        	  params.setDc(request.getParameter("dc"));
	        	  session.setAttribute("date", request.getParameter("date"));
	        	  session.setAttribute("time", request.getParameter("time"));
	        	  session.setAttribute("place", request.getParameter("place"));
	        	  session.setAttribute("dc", request.getParameter("dc"));
	              stepFlag = 3;
	              processResult ();
	          } else if (request.getParameter("back2") != null) {
	        	  params.setDate(request.getParameter("date"));
	        	  params.setTime(request.getParameter("time"));
	        	  params.setPlace(request.getParameter("place"));
	        	  params.setDc(request.getParameter("dc"));
	        	  session.setAttribute("date", request.getParameter("date"));
	        	  session.setAttribute("time", request.getParameter("time"));
	        	  session.setAttribute("place", request.getParameter("place"));
	        	  session.setAttribute("dc", request.getParameter("dc"));
	              stepFlag = 1;
	          } else if (request.getParameter("back3") != null){
	        	  stepFlag = 2;
	          }
	          
	          switch (stepFlag) {
	          case 0:
	        	  writer.println("<form method=\"POST\"><button type=\"submit\" name=\"makeInvite\" value=\"makeInvite\" " + 
	        			  	"style=\"width: 200px; color: red; font-size: 50; background-color: #FFCCFF; " + 
	        			  	"-moz-border-radius: 30px; -webkit-border-radius: 30px;" + 
	        			  	"border: 5px solid #009900; padding: 5px;\">Make Invite!</button></form>");   	  
	        	  break;
	          case 1:
	        	  Object yourName = session.getAttribute("yourName");
	        	  Object friendName = session.getAttribute("friendName");
	        	  if (yourName == null)
	        		  yourName = (String) "";
	        	  if (friendName == null)
	        		  friendName = (String) "";
	        	  writer.println("<table width=\"800\" border=\"0\" cellspacing=\"0\" cellpadding=\"4\"><tr>" +
	        			  "<td><form method=\"POST\">Your Name: <br><input type=\"text\" name=\"yourName\" value=" + yourName +
	        			  "><br />Friend's Name: <br><input type=\"text\" name=\"friendName\" value=" + friendName +
	        			  "><br /></td><td> Color (default is black):<br><input type=\"radio\" name=\"color\" value=\"red\"><font color=red>Red</font>" +
	        			  "<br><input type=\"radio\" name=\"color\" value=\"blue\"><font color=blue>Blue</font>" +
	        			  "<br><input type=\"radio\" name=\"color\" value=\"green\"><font color=green>Green</font>" +
	        			  "<br></td></tr></table><button type=\"submit\" name=\"next1\" value=\"next1\" " + 
	        			  "style=\"width: 100px; color: red; font-size: 20; background-color: #FFCCFF;" +
	        			  "-moz-border-radius: 15px; -webkit-border-radius: 15px; border: 5px solid #009900;" +
	        			  "padding: 5px;\">Next -></button></form>");
	        	  break;
	          case 2:
	        	  Object date = session.getAttribute("date");
	        	  Object time = session.getAttribute("time");
	        	  Object place = session.getAttribute("place");
	        	  Object dc = session.getAttribute("dc");
	        	  if (date == null)
	        		  date = (String) "";
	        	  if (time == null)
	        		  time = (String) "";
	        	  if (place == null)
	        		  place = (String) "";
	        	  if (dc == null)
	        		  dc = (String) "";
	        	  writer.println("<form method=\"POST\">Date: <br><input type=\"text\" name=\"date\" onfocus=\"showCalendarControl(this);\" value=" + date +
	        			  "><br />Time: <br><input type=\"text\" name=\"time\" value=" + time +
	        			  "><br />Place: <br><input type=\"text\" name=\"place\" value=" + place +
	        			  "><br />DC:<br><input type=\"text\" name=\"dc\" value=" + dc +
	        			  "><br /><br><table width=\"800\" border=\"0\" cellspacing=\"0\" cellpadding=\"4\"><tr><td><button type=\"submit\" name=\"back2\" value=\"back2\" " +  
	        			  "style=\"width: 100px; color: red; font-size: 20; background-color: #FFCCFF;" + 
	        			  "-moz-border-radius: 15px; -webkit-border-radius: 15px;" +
	        			  "border: 5px solid #009900; padding: 5px;\"><- Back</button></td> " + 
	        			  "<td><button type=\"submit\" name=\"next2\" value=\"next2\" "+
	        			  "style=\"color: red; font-size: 20; background-color: #FFCCFF;" +
	        			  "-moz-border-radius: 15px; -webkit-border-radius: 15px; border: 5px solid #009900;" +
	        			  "padding: 5px;\">Finish -></button></td></tr></table></form>");
	        	  
	        	  break;
	          case 3:
	        	  if (session.getAttribute("yourName")!= null && !session.getAttribute("yourName").equals(""))
	        		  writer.println("Dear " + session.getAttribute("yourName") + "! ");
	        	  writer.println ("Your invitation is:<br><br><img src=result.png /><br><br>" +
	        			  "<form method=\"POST\"><button type=\"submit\" name=\"back3\" value=\"back3\" "+
	        			  "style=\"width: 100px; color: red; font-size: 20; background-color: #FFCCFF;" +
	        			  "-moz-border-radius: 15px; -webkit-border-radius: 15px; border: 5px solid #009900;" +
	        			  "padding: 5px;\"><- Back</button></form>");
	        	  break;
	          }
	          
	          writer.println("</center> </body> </html>");
	          writer.close();
	     }

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
        	  
		}
		
		public void processResult () throws IOException {
			BufferedImage img;
			img = ImageIO.read(new File("C:/Documents and Settings/Admin/workspace/4_templateBd/4_templateBd/source.jpg"));
	
				Graphics2D g2 = img.createGraphics();
			        
				if (params.getColor() == null)
					g2.setColor(Color.black);
				else if (params.getColor().contains("red")){
						g2.setColor(Color.red);
				}
				else if (params.getColor().contains("blue")){
						g2.setColor(Color.blue);
				}
				else if (params.getColor().contains("green")){
						g2.setColor(Color.green);
					}
				Font font = new Font("Serif", Font.PLAIN, 18);
				g2.setFont(font);
				//g2.drawString("Dear " + params.getFriendName() + "!", 30, 30);
				g2.drawString("For:   " + params.getFriendName(), 40, 320);
				g2.drawString("Date:  " + params.getDate(), 40, 340);
				g2.drawString("Time:  " + params.getTime(), 40, 360);
				g2.drawString("Place: " + params.getPlace(), 40, 380);
				g2.drawString("DC:    " + params.getDc(), 40, 400);
		
				ImageIO.write(img, "png", new File("C:/Documents and Settings/Admin/workspace/4_templateBd/4_templateBd/result.png"));
			
		}
}