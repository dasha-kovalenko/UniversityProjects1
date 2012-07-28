<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="levkat.chatfilews.core.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.text.DateFormat" %><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chat</title>
<link rel="stylesheet" type="text/css" href="assets/styles.css" />
  <script type="text/javascript" src="assets/jQuery.js"></script>
  <script type="text/javascript" src="assets/jquery.timers-1.2.js"></script>
  <script type="text/javascript">
  	var isAlreadyDownloading = false;

  	// document "onLoaded" handler
	$(document).ready(function(){

		// attach "onClick" handler to "Send" button
		$('#btnSend').click(function(){
			SendMessage($(this).val());  
		});

		// download all chat messages
		DownloadMessages();

		// attach timer to periodicaly downloading all chat messages
		$(document).everyTime("5s", function() {
			DownloadMessages();
		}, 0);
	});

  	// download chat messages from server
	function DownloadMessages(){
		if (!isAlreadyDownloading)
		{
			isAlreadyDownloading = true;

			// downloading all chat messages
			$.get('MessageLog', function(data) {

				// replace current chat messages with just downloaded
				$('#logContainer').html(data);

				// scroll to the bottom of the messages
				$("#logContainer").attr({ scrollTop: $("#logContainer").attr("scrollHeight") });

				// scroll with animation of the messages
				//$('#logContainer').animate({ scrollTop: $("#logContainer").attr("scrollHeight") }, 1000);
			});

			isAlreadyDownloading = false;
		}
	}

	// send message to server
	function SendMessage(str){

		// disable send button
		$('#btnSend').attr('disabled', 'disabled'); // To disable

		// sending message
		$.post('MessageLog', $('#chat').serialize(), function(data){

			// clearing outcoming message
			$('#outcomingmess').val("");

			// load all chat messages
			DownloadMessages();

			// enable send button		
			$('#btnSend').removeAttr('disabled'); // To enable
		});
	}
	</script>
</head>
<body>
	<form method="POST" action='Controller' name="chat" id="chat">
		<table cellpadding="6">
			<tr>
				<td width="600px">
					<div id="logContainer" style="height:300px; overflow-y:scroll;">
					<ul id="chatLog">
					<!--
					<% 
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
					%>
					-->
					</ul>
					</div>
				</td>
				<td class="userlist" rowspan="2" width="150px">
					<ul>
					<% 
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
					%>
					</ul>
				</td>
			</tr>
			<tr>
				<td width="600px"><textarea id="outcomingmess" name="outcomingmess" style="width:100%;height:100px"></textarea></td>
			</tr>
			<tr>
				<td align="center">
					<button type="button" name="btnSend" id="btnSend" value="Send">Send</button> 
				</td>
				<td align="right" width="150px">
					<input type="submit" width="80px" name="btnLogout" value="Logout" onclick="closeWindow()"> 
				</td>
			</tr>
		</table>
		<input type="hidden" id="nickname" name="nickname" value=<%= request.getAttribute("Username") %>>
		<input type="hidden" id="password" name="password" value=<%= request.getAttribute("Password") %>>
	</form>
</body>
</html>
