<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="/jsp/errors/error.jsp" %> 	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<jsp:include page="/css/step3.css"/>
</style>
<title>Education</title>
</head> 
<body>
<div id=body>
	<h2>Please, enter your education!</h2>
	<img id=image src=images/Education.png alt="Education" />
	<form action = "masterCV" id = formData  method="post">
	<input type="hidden" name="command" value="step3">
		<custom:areatag area_name="textAreaEducation" area_label="Education" area_value="education">
			<jsp:attribute name="comment">
				<input id="formButtonNext" type="submit" value="Next>>"/>
			</jsp:attribute>
		</custom:areatag>
    </form>
   	<form action="masterCV" method="post">
   		<input type="hidden" name="command" value="step3">
   		<input type="hidden" name="step_back" value="true">
		<input id="formButtonBack" type=submit value="<<Back"/>
	</form> 
</div>
</body>
</html>