<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<jsp:include page="/css/jquery.ui.datepicker.css" flush="true"/>
</style>
<style type="text/css">
<jsp:include page="/css/match/list.css" flush="true"/>
</style>
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
	<jsp:include page="/js/jquery.ui.datepicker.js" flush="true" /> 
</style>
<style type="text/javascript">
	<jsp:include page="/js/match/list.js" flush="true" /> 
</style>
<title>Список всех возможных матчей</title>
</head>
<body>
<div id="centerPanel">
	<p>
	<h1>Выберите критерии для поиска:</h1>
	</p>
	<form id="formPanel" class="form-horizontal" method="post" action="football" accept-charset="utf-8">
		<fieldset>
		<input type="hidden" name="command" value="matchList">
		<div class="control-group">
			<label class="control-label" for="team">Название команды:</label> 
			<div class="controls">
			<select name="team">
				<c:forEach items="${teams}" var="team">
					<option value="${team}">${team}</option>
				</c:forEach>
			</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="date1">Период:</label> 
			<div class="controls inline">
			<input id="datepicker1" type="text" name="date1"
				value="${date1}"
				onfocus="$(this).datepicker({
					showOn: 'button',
					buttonImage: 'images/calendar.png',
					buttonImageOnly: true,
					dateFormat: 'yy-mm-dd'
				});"
				placeholder="Начальная дата"
				readonly
				pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" required>
			<input id="datepicker2" type="text" name="date2"
				value="${date2}" 
				onfocus="$(this).datepicker({
					showOn: 'button',
					buttonImage: 'images/calendar.png',
					buttonImageOnly: true,
					dateFormat: 'yy-mm-dd'
				});"
				placeholder="Конечная дата"
				readonly
				pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" required>
			</div>
		</div>
		<div class="control-group">
		<div id="formFooter" class="controls inline">
			<input type="reset" class="btn btn-inverse" value="Сброс" />
			<input class="btn btn-success btn-large" type="submit" value="Искать">
		</div>
		</div>
		</fieldset>
	</form>
	<c:if test="${not empty incorrectData}">
	<div class="alert alert-error">
	 <a class="close">×</a>
		<p>
			<c:out value="${incorrectData}" />
		</p>
	</div>
	</c:if>
	<c:choose>
		<c:when test="${not empty matches}">
			<h1>Cписок всех сыгранных матчей:</h1>
			<table class="table table-condensed">
				<thead>
					<tr>
						<th>Первая команда</th>
						<th>Вторая команда</th>
						<th>Счет</th>
						<th>Дата</th>
						<th>Удалить</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${matches}" var="match">
						<tr>
							<td><c:out value="${match.team1}" /></td>
							<td><c:out value="${match.team2}" /></td>
							<td><c:out value="${match.count}" /></td>
							<td><form method="get" action="football">
									<input type="hidden" name="command" value="matchEdit">
									<input type="hidden" name="id" value="${match.id}">
									<input class="btn btn-info" type="submit" value="${match.matchdate}">
								</form></td>
							<td><form method="post" action="football">
									<input type="hidden" name="command" value="matchDelete">
									<input type="hidden" name="id" value="${match.id}">
									<input class="btn btn-warning" type="submit" value="Удалить">
								</form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<h3><c:out value="Выберите критерии для поиска." /></h3>
		</c:otherwise>
	</c:choose>
	<form class="footer" method="get" action="football">
		<input type="hidden" name="command" value="matchCreate"> <input class="btn btn-success" 
			type="submit" value="Зарегистрировать новый матч">
	</form>
	<form class="footer" method="get" action="football">
		<input type="hidden" name="command" value="matchList"> <input class="btn btn-success" 
			type="submit" value="Список всех матчей">
	</form>
	</div>
</body>
</html>