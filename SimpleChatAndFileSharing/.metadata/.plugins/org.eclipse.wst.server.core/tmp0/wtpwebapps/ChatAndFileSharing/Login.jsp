<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login and Registration</title>
<link rel="stylesheet" type="text/css" href="assets/styles.css" />
</head>
<body>
	
	<form method="POST" action='Controller' name="login">
		<table border="3" cellpadding="3">
			<tr>
				<td class="labels">Nickname:</td>
				<td><input type="text" name="nickname" maxlength="13" style="width:120px;"></td>
			</tr>
			<tr>
				<td class="labels">Password:</td>
				<td><input type="password" name="password" maxlength="13" style="width:120px;"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="btnLogin" value="Login"> 
					<input type="submit" name="btnRegister" value="Register"> 
				</td>
			</tr>
		</table>
	</form>

	<form class="errors" >
	<% if (request.getAttribute("IsNoAllowed") != null ) {%>
		<%= request.getAttribute("IsNoAllowed") %>
	<%}%> 
	</form>
	
</body>
</html>