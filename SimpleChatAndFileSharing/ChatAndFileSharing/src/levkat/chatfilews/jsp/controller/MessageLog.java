/**
 * 
 */
package levkat.chatfilews.jsp.controller;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import levkat.chatfilews.core.*;

/**
 * @author Kate Levshova
 *
 */
public class MessageLog extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8801342575055798322L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();

	    out.println("<ul id=\"chatLog\">");

	    Chat chat = new Chat();
		ChatMessage[] messages = chat.getAllMessages();

		boolean isYou = false;
		String name = "";
	
		for( int i = 0; i < messages.length; i++) 
		{ 
			// attributes are not working in GET method.
			// there is a chanse to add another custom servlet class as this
			// and implement POST method in it to use request.getAttribute("Username") there...
			isYou = false; //request.getAttribute("Username").equals(messages[i].author.nickname);
			
			if(isYou)
			{
				name = "<b>" + messages[i].author.nickname + " (you)" +"</b>";
			}
			else
			{
				name = messages[i].author.nickname;
			}
			
			
			
			out.println("<li>" 
							+ "[" + DateFormat.getDateTimeInstance().format(messages[i].timestamp)
							+ "]" + name + ": "
							+ messages[i].text
						+ "</li>");
		} 

	    
	    out.println("</ul>");
	    out.close();
	}

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		Chat chat = new Chat();
		
		String nickname = request.getParameter("nickname").toLowerCase();
		String message = request.getParameter("outcomingmess");
		
		chat.postMessage(message, nickname);
	}
}
