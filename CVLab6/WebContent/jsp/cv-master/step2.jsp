<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page errorPage="/jsp/errors/error.jsp" %> 	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<jsp:include page="/css/step2.css" flush="true"/>
</style>
<style type="text/css">
<jsp:include page="/css/calendar.css" flush="true"/>
</style>
<script type="text/javascript">
<jsp:include page="/js/calendar.js" flush="true"/>
</script>
<title>Personal data</title>
</head>
<body>
<div id=body>
	<h2>Please, enter your personal data!</h2>
	<img id=image src=images/personalData.jpg alt="Personal Data" />
	<form action = "masterCV" id = formData method="post">
	<input type="hidden" name="command" value="step2">
	<div id = bodyName><label id = fullName>Full Name:</label>
		<input type="text" id = textFieldName name="fieldName" required value="name">
	</div>
	    <div id = bodyDate><label id = date>Date:</label>
	    <input type="text" id=textFieldDate name = "fieldDate" required onfocus="showCalendarControl(this);">
    </div>
    <div id = bodyCity><label id = city >City:</label>
	    <input type="text" id=textFieldCity name = "fieldCity" required value="city">
    </div>
    <div id = bodyAddress><label id = address >Address:</label>
    	<input type="text" id = textFieldAddress name = "fieldAddress" required value="address">
   	</div> 
    <div><label id = e-mail>e-mail:</label>
   		<input type="text" id = textFieldE-Mail name = "fieldEMail" required value="q@gmail.com">
	</div>
	<input id="formButton" type="submit" value="Next>>">
    </form>
</div>
</body>
</html>