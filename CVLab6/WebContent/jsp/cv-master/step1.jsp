<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page errorPage="/jsp/errors/error.jsp" %> 	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<jsp:include page="/css/step1.css" flush="true"/>
</style>
<title>Мастер создания резюме - внешний вид резюме</title>
</head>
<body>
	<div id=body>
	<h2>Welcome to the wizard of creating a curriculum vitae!</h2>
	<img id=image src=images/CurriculumVitae.jpg alt="Curriculum Vitae" />
	<form action="masterCV" method="post">
		<input type="hidden" name="command" value="step1">
		<input id="formButton" type="submit" value="Start Making CV!" name = "startMakingCV">
	</form>
	</div>
</body>
</html>