<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<%
		String redirectURL = request.getContextPath()
				+ "/periodicals?command=editionList";
		response.sendRedirect(redirectURL);
	%>
</body>
</html>