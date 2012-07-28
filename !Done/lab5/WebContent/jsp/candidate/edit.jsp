<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	<jsp:include page="/js/jquery-1.7.1.min.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/js/candidate/edit.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/js/candidate/formInit.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/css/bootstrap/js/bootstrap.min.js" flush="true"/>
</script>
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
<jsp:include page="/css/candidate/edit.css" flush="true"/>
</style>
<title>Обновление кандидата ${candidate.name}</title>
</head>
<body>
<div id="centerPanel">
	<p>
	<h1>Обновление кандидата:</h1>
	</p>
	<c:if test="${not empty incorrectData}">
	<div class="alert alert-error">
	<a class="close">×</a>
		<p>
			<c:out value="${incorrectData}" />
		</p>
	</div>	
	</c:if>
	<form id="formPanel" class="form-horizontal" method="post" action="candidate">
	<fieldset>
		<input type="hidden" name="command" value="candidateEdit"> <input
			type="hidden" name="id" value="${candidate.id}"> <input
			id="country" type="hidden" name="countryTemp"
			value="${candidate.country==null?"Любая":candidate.country}">
		<input id="city" type="hidden" name="cityTemp"
			value="${candidate.city==null?"Любой":candidate.city}"> <input
			id="male" type="hidden" name="maleTemp" value="${candidate.male}">
		<div class="control-group">
			<label class="control-label" for="age">Возраст:</label> 
			<div class="controls">
			<input type="number" name="age"
				min="18" max="99" value="${candidate.age}" required
				pattern="[1-9][0-9]">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">Имя:</label> 
			<div class="controls">
			<input type="text" name="name"
				value="${candidate.name}" required pattern="[а-яА-Яa-zA-Z]{1,10}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="male">Пол:</label> 
			<div class="controls">
			<label class="radio inline">
			<input type="radio" name="male"
				value="true">
				Мужской 
			</label>
			<label class="radio inline">
				<input type="radio" name="male"
				value="false" checked="checked">
				Женский
			</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="country">Страна:</label> 
			<div class="controls">
			<select id="countrySelect"
				name="country">
				<option value="Любая">Любая</option>
			</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="city">Город:</label> 
			<div class="controls">
			<select id="citySelect" name="city">
				<option value="Любой">Любой</option>
			</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="phone">Номер телефона:</label> 
			<div class="controls">
			<input type="text"
				name="phone" value="${candidate.phone}" required
				pattern="[0-9]{1,25}">
			</div>
		</div>
		<div class="control-group">
		<div id="formFooter" class="controls">
		<input class="btn btn-success btn-large" type="submit" value="Обновить">
		</div>
		</div>
		</fieldset>
	</form>
	<p>
	<form class="footer form-inline" method="get" action="candidate">
		<input type="hidden" name="command" value="candidateList"> <input class="btn btn-success"
			type="submit" value="Вывести список всех кандидатов">
	</form>
	<form class="footer form-inline" method="get" action="candidate">
		<input type="hidden" name="command" value="candidateCreate"> <input class="btn btn-success"
			type="submit" value="Зарегистрировать нового кандидата">
	</form>
	</p>
	</div>
</body>
</html>