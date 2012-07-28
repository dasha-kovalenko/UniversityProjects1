package levkat.chatfilews.jsp.controller;

import java.util.Map;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

import levkat.chatfilews.core.Chat;

/**
 * Servlet implementation class Controller
 * @author Kate Levshova
 */
public class Controller extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() 
    {
        super();
        
        //timer.addActionListener(checkUpdates)
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();

	    out.println("Hello, world!");
	    out.close();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String nickname = request.getParameter("nickname").toLowerCase();
		String password = request.getParameter("password");
		
		if(nickname == "" || password == "")
		{
			String errorString =  "You should fill in both values - nickname and password.";
			
			// Transferring parameters from one JSP to another (from Login.jsp to Chat.jsp)
			request.setAttribute("IsNoAllowed", errorString);
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			
		}
		else
		{
			@SuppressWarnings("unchecked")
			Map parameters = request.getParameterMap();
			
			// Variables to define which one of the buttons on the form of JSP has been clicked
			boolean isLoginEvent = parameters.containsKey("btnLogin");
			boolean isRegisterEvent = parameters.containsKey("btnRegister");
			boolean isSendEvent = parameters.containsKey("btnSend");
			boolean isLogoutEvent = parameters.containsKey("btnLogout");
			
			Chat chat = new Chat();
			
			if(isLoginEvent == true)
			{
				int result = chat.login(nickname, password);
				
				if(result == 0)
				{
					// Transferring parameters from one JSP to another (from Login.jsp to Chat.jsp)
					request.setAttribute("Username", nickname);
					
					request.setAttribute("Password", password);
					
					request.setAttribute("UserList", chat.getOnlineUsers());
					
					request.setAttribute("AllMessages", chat.getAllMessages());
					
					request.getRequestDispatcher("/Chat.jsp").forward(request, response);
				}
				else
				{
					String errorString =  "Wrong username or password. Also it is possible that current user has been already logged in. Please try to register a new user.";
					
					// Transferring parameters from one JSP to another (from Login.jsp to Chat.jsp)
					request.setAttribute("IsNoAllowed", errorString);
					request.getRequestDispatcher("/Login.jsp").forward(request, response);
				}
			}
			
			if(isRegisterEvent == true)
			{
				int result = chat.registerNewUser(nickname, password);
				
				if(result == 0)
				{
					//TODO: copy here from login
					// Transferring parameters from one JSP to another (from Login.jsp to Chat.jsp)
					request.setAttribute("Username", nickname);
					
					request.setAttribute("Password", password);
					
					request.setAttribute("UserList", chat.getOnlineUsers());
					
					request.setAttribute("AllMessages", chat.getAllMessages());
					
					request.getRequestDispatcher("/Chat.jsp").forward(request, response);
				}
				else
				{
					String errorString =  "Such user is already existed. Please try another one.";
					
					request.setAttribute("IsNoAllowed", errorString);
					request.getRequestDispatcher("/Login.jsp").forward(request, response);
				}
			}
			
			if(isSendEvent == true)
			{
				String message = request.getParameter("outcomingmess");
				chat.postMessage(message, nickname);
				
				request.setAttribute("Username", nickname);
				
				request.setAttribute("Password", password);
				
				request.setAttribute("UserList", chat.getOnlineUsers());
				
				request.setAttribute("AllMessages", chat.getAllMessages());
				
				request.getRequestDispatcher("/Chat.jsp").forward(request, response);
			}
			
			if(isLogoutEvent == true)
			{
				chat.logout(nickname, password);
			}
		}
	}
}