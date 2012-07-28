<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	<jsp:include page="/js/jquery-1.7.2.min.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/js/jquery-ui-1.8.20.custom.min.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/css/bootstrap/js/bootstrap.min.js" flush="true"/>
</script>
<style type="text/javascript">
<jsp:include page="/css/match/create.js" flush="true"/>
</style>
<style type="text/css">
<jsp:include page="/css/jquery-ui-1.8.20.custom.css" flush="true"/>
</style>
<style type="text/css">
<jsp:include page="/css/jquery.ui.datepicker.css" flush="true"/>
</style>
<style type="text/css">
<jsp:include page="/css/bootstrap/css/bootstrap.min.css" flush="true"/>
</style>
<style type="text/css">
<jsp:include page="/css/bootstrap/css/bootstrap-responsive.css" flush="true"/>
</style>
<style type="text/css">
<jsp:include page="/css/base.css" flush="true"/>
</style>
<style type="text/css">
<jsp:include page="/css/match/create.css" flush="true"/>
</style>
<title>Создание нового матча</title>
</head>
<body>
<div id="centerPanel">
	<p>
	<h1>Создание нового матча:</h1>
	</p>
	<c:if test="${not empty incorrectData}">
	<div class="alert alert-error">
	 <a class="close">×</a>
		<p>
			<c:out value="${incorrectData}" />
		</p>
		</div>
	</c:if>
	<form id="formPanel" class="form-horizontal" method="post" action="football">
	<fieldset>
		<input type="hidden" name="command" value="matchSave"> 
		<div class="control-group">
			<label class="control-label" for="team1">Первая команда:</label> 
			<div class="controls">
			<input type="text" name="team1"
				value="${team1}"
				placeholder="Название первой команды" 
				required pattern="[а-яА-Яa-zA-Z]{3,20}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="team2">Вторая команда:</label> 
			<div class="controls">
			<input type="text" name="team2"
				placeholder="Название второй команды" 
				value="${team2}" required pattern="[а-яА-Яa-zA-Z]{3,20}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="count1">Счет:</label> 
			<div class="controls inline">
			<input class="inline" type="number" name="count1"
				min="0" max="9" value="${count1!=null?count1:0}" required
				pattern="[0-9]">
			<label class="inline"> : </label>	
			<input class="inline" type="number" name="count2"
				min="0" max="9" value="${count2!=null?count2:0}" required
				pattern="[0-9]">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="date">Дата матча:</label> 
			<div class="controls">
			<input id="datepicker" type="text" name="date"
				value="${date}" 
				onfocus="$(this).datepicker({
					showOn: 'button',
					buttonImage: 'images/calendar.png',
					buttonImageOnly: true,
					dateFormat: 'yy-mm-dd'
				});"
				placeholder="Дата проведения матча" 
				readonly
				pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" required>
			</div>
		</div>
		<div class="control-group">
		<div id="formFooter" class="controls inline">
			<input type="reset" class="btn btn-inverse" value="Сброс" />
			<input class="btn btn-success btn-large" type="submit" value="Создать">
		</div>
		</div>
		</fieldset>
	</form>
	<p>
	<form class="footer" method="get" action="football">
		<input type="hidden" name="command" value="matchList"> <input class="btn btn-success"
			type="submit" value="Вывести список всех">
	</form>
	</p>
	</div>
</body>
</html>