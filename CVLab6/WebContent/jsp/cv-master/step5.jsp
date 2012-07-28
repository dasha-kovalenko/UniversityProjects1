<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<jsp:include page="/css/step5.css" flush="true"/>
</style>
<title>Result CV</title>
</head>
<body>
<div id=body>
	<h2>Dear ${fieldName}! Your CV is:</h2>
	<img id=image src=images/resume.jpg alt="Results" />
	<p>Name: ${fieldName}</p>
	<p>Date: ${fieldDate}</p>
		<p>City: ${fieldCity}</p>
		<p>Address: ${fieldAddress}</p>
		<p>E-Mail: ${fieldEMail}</p>
		<p>Education: ${textAreaEducation}</p>
		<p>Work Experience: ${textAreaWork}</p>
	
	<form action = "masterCV" id = formData method="post">
		<input type="hidden" name="command" value="step5">
   		<input type="hidden" name="step_back" value="true">
		<input id="formButtonBack" type="submit" value="<<Back"/>
	</form> 
</div>
</body>
</html>