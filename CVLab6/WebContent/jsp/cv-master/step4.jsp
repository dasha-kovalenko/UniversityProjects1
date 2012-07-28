<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@ page errorPage="/jsp/errors/error.jsp"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<jsp:include page="/css/step4.css"/>
</style>
<title>Work experience</title>
</head>
<body>
<div id=body>
	<h2>Please, enter your work experience!</h2>
	<img id=image src=images/work-experience.jpg alt="Work experience" />
	<form action = "masterCV" id = formData method="post">
	<input type="hidden" name="command" value="step4">
		<custom:areatag area_name="textAreaWork" area_label="Work experience" area_value="work">
			<jsp:attribute name="comment">Comment</jsp:attribute>
		</custom:areatag>
		
		<input id="formButtonNext" type="submit" value="Finish"/>
    </form>
   	<form action="masterCV" method="post">
   		<input type="hidden" name="command" value="step4">
   		<input type="hidden" name="step_back" value="true">
		<input id="formButtonBack" type="submit" value="<<Back"/>
	</form> 
</div>
</body>
</html>