package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import levkat.chatfilews.core.*;
import java.text.DateFormat;

public final class Chat_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("    \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n");
      out.write("<title>Chat</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"assets/styles.css\" />\r\n");
      out.write("  <script type=\"text/javascript\" src=\"assets/jQuery.js\"></script>\r\n");
      out.write("  <script type=\"text/javascript\" src=\"assets/jquery.timers-1.2.js\"></script>\r\n");
      out.write("  <script type=\"text/javascript\">\r\n");
      out.write("  \tvar isAlreadyDownloading = false;\r\n");
      out.write("\r\n");
      out.write("  \t// document \"onLoaded\" handler\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\r\n");
      out.write("\t\t// attach \"onClick\" handler to \"Send\" button\r\n");
      out.write("\t\t$('#btnSend').click(function(){\r\n");
      out.write("\t\t\tSendMessage($(this).val());  \r\n");
      out.write("\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t// download all chat messages\r\n");
      out.write("\t\tDownloadMessages();\r\n");
      out.write("\r\n");
      out.write("\t\t// attach timer to periodicaly downloading all chat messages\r\n");
      out.write("\t\t$(document).everyTime(\"5s\", function() {\r\n");
      out.write("\t\t\tDownloadMessages();\r\n");
      out.write("\t\t}, 0);\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("  \t// download chat messages from server\r\n");
      out.write("\tfunction DownloadMessages(){\r\n");
      out.write("\t\tif (!isAlreadyDownloading)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tisAlreadyDownloading = true;\r\n");
      out.write("\r\n");
      out.write("\t\t\t// downloading all chat messages\r\n");
      out.write("\t\t\t$.get('MessageLog', function(data) {\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t// replace current chat messages with just downloaded\r\n");
      out.write("\t\t\t\t$('#logContainer').html(data);\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t// scroll to the bottom of the messages\r\n");
      out.write("\t\t\t\t$(\"#logContainer\").attr({ scrollTop: $(\"#logContainer\").attr(\"scrollHeight\") });\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t// scroll with animation of the messages\r\n");
      out.write("\t\t\t\t//$('#logContainer').animate({ scrollTop: $(\"#logContainer\").attr(\"scrollHeight\") }, 1000);\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\r\n");
      out.write("\t\t\tisAlreadyDownloading = false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t// send message to server\r\n");
      out.write("\tfunction SendMessage(str){\r\n");
      out.write("\r\n");
      out.write("\t\t// disable send button\r\n");
      out.write("\t\t$('#btnSend').attr('disabled', 'disabled'); // To disable\r\n");
      out.write("\r\n");
      out.write("\t\t// sending message\r\n");
      out.write("\t\t$.post('MessageLog', $('#chat').serialize(), function(data){\r\n");
      out.write("\r\n");
      out.write("\t\t\t// clearing outcoming message\r\n");
      out.write("\t\t\t$('#outcomingmess').val(\"\");\r\n");
      out.write("\r\n");
      out.write("\t\t\t// load all chat messages\r\n");
      out.write("\t\t\tDownloadMessages();\r\n");
      out.write("\r\n");
      out.write("\t\t\t// enable send button\t\t\r\n");
      out.write("\t\t\t$('#btnSend').removeAttr('disabled'); // To enable\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<form method=\"POST\" action='Controller' name=\"chat\" id=\"chat\">\r\n");
      out.write("\t\t<table cellpadding=\"6\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=\"600px\">\r\n");
      out.write("\t\t\t\t\t<div id=\"logContainer\" style=\"height:300px; overflow-y:scroll;\">\r\n");
      out.write("\t\t\t\t\t<ul id=\"chatLog\">\r\n");
      out.write("\t\t\t\t\t<!--\r\n");
      out.write("\t\t\t\t\t");
 
						ChatMessage[] messages = (ChatMessage[])request.getAttribute("AllMessages");
						boolean isYou = false;
						String name = "";
					
						for( int i = 0; i < messages.length; i++) 
						{ 
							isYou = request.getAttribute("Username").equals(messages[i].author.nickname);
							
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
					
      out.write("\r\n");
      out.write("\t\t\t\t\t-->\r\n");
      out.write("\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td class=\"userlist\" rowspan=\"2\" width=\"150px\">\r\n");
      out.write("\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t");
 
						ChatPerson[] users = (ChatPerson[])request.getAttribute("UserList");
						String sYou = "";	
						String nick = "";
					
						for( int i = 0; i < users.length; i++) 
						{ 
							isYou = request.getAttribute("Username").equals(users[i].nickname);
							
							if(isYou)
							{
								nick = "<b>" + users[i].nickname + " (you)" +"</b>";
							}
							else
							{
								nick = users[i].nickname;
							}
							
							out.println("<li>" + nick + "</li>");
						} 
					
      out.write("\r\n");
      out.write("\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=\"600px\"><textarea id=\"outcomingmess\" name=\"outcomingmess\" style=\"width:100%;height:100px\"></textarea></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td align=\"center\">\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" name=\"btnSend\" id=\"btnSend\" value=\"Send\">Send</button> \r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td align=\"right\" width=\"150px\">\r\n");
      out.write("\t\t\t\t\t<input type=\"submit\" width=\"80px\" name=\"btnLogout\" value=\"Logout\" onclick=\"closeWindow()\"> \r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t<input type=\"hidden\" id=\"nickname\" name=\"nickname\" value=");
      out.print( request.getAttribute("Username") );
      out.write(">\r\n");
      out.write("\t\t<input type=\"hidden\" id=\"password\" name=\"password\" value=");
      out.print( request.getAttribute("Password") );
      out.write(">\r\n");
      out.write("\t</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
