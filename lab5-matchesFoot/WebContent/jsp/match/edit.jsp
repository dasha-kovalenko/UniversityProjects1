<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<jsp:include page="/css/match/edit.js" flush="true"/>
</style>
<style type="text/css">
<jsp:include page="/css/jquery-ui-1.8.20.custom.css" flush="true"/>
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
<jsp:include page="/css/match/edit.css" flush="true"/>
</style>
<title>Обновление матча ${match.team1}:${match.team2}</title>
</head>
<body>
<div id="centerPanel">
	<p>
	<h1>Обновление матча ${match.team1}:${match.team2}</h1>
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
		<input type="hidden" name="command" value="matchUpdate">
		<input type="hidden" name="id" value="${match.id}">
		<div class="control-group">
			<label class="control-label" for="team1">Первая команда:</label> 
			<div class="controls">
			<input type="text" name="team1"
				value="${match.team1}" required pattern="[а-яА-Яa-zA-Z]{1,10}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="team2">Вторая команда:</label> 
			<div class="controls">
			<input type="text" name="team2"
				value="${match.team2}" required pattern="[а-яА-Яa-zA-Z]{1,10}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="count1">Счет:</label> 
			<div class="controls">
			<input class="inline" type="number" name="count1"
				min="0" max="9" value="${match.count!=null?fn:substring(match.count,0,fn:indexOf(match.count, ':')):0}" required
				pattern="[0-9]">
			<label class="inline"> : </label>	
			<input class="inline" type="number" name="count2"
				min="0" max="9" value="${match.count!=null?fn:substring(match.count,fn:indexOf(match.count, ':')+1, fn:length(match.count) ):0}" required
				pattern="[0-9]">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="date">Дата матча:</label> 
			<div class="controls">
			<input id="datepicker" type="text" name="date"
				value="${match.matchdate}" 
				onfocus="$(this).datepicker({
					showOn: 'button',
					buttonImage: 'images/calendar.png',
					buttonImageOnly: true,
					dateFormat: 'yy-mm-dd'
				});"
				readonly
				pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" required>
			</div>
		</div>
		<div class="control-group">
			<div id="formFooter" class="controls inline">
			<input type="reset" class="btn btn-inverse" value="Сброс" />
			<input class="btn btn-success btn-large" type="submit" value="Обновить">
		</div>
		</div>
		</fieldset>
	</form>
	<p>
	<form class="footer form-inline" method="get" action="football">
		<input type="hidden" name="command" value="matchList"> <input class="btn btn-success"
			type="submit" value="Вывести список всех матчей">
	</form>
	<form class="footer form-inline" method="get" action="football">
		<input type="hidden" name="command" value="matchCreate"> <input class="btn btn-success"
			type="submit" value="Зарегистрировать новый матч">
	</form>
	</p>
	</div>
</body>
</html>