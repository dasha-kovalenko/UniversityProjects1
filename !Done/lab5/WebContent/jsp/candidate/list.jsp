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
	<jsp:include page="/js/candidate/list.js" flush="true"/>
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
<jsp:include page="/css/candidate/list.css" flush="true"/>
</style>
<title>Список всех возможных кандидатов</title>
</head>
<body>
<div id="centerPanel">
	<p>
	<h1>Выберите критерии для поиска:</h1>
	</p>
	<form id="formPanel" class="form-horizontal" method="post" action="candidate" accept-charset="utf-8">
		<fieldset>
		<input type="hidden" name="command" value="candidateList"> <input
			id="country" type="hidden" name="countryTemp"
			value="${country==null?"Любая":country}"> <input id="city"
			type="hidden" name="cityTemp" value="${city==null?"Любой":city}">
		<input id="male" type="hidden" name="maleTemp" value="${male}">
		<div class="control-group">
			<label class="control-label" for="minAge">Минимальный возраст:</label> 
			<div class="controls">
			<input type="number"
				 name="minAge" min="18" max="99" value="${minAge}" required
				pattern="[1-9][0-9]">
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label" for="maxAge">Максимальный возраст:</label> 
			<div class="controls">
		    <input 
				type="number" name="maxAge" min="18" max="99" value="${maxAge}"
				required pattern="[1-9][0-9]">
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label" for="male">Пол:</label> 
			<div class="controls">
			<label class="radio inline">
			<input type="radio" name="male"
				value="true">
				Мужской
			</label>
			<label class="radio inline">
			<input type="radio" name="male"
				value="false">
				Женский
			</label>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label" for="country">Страна:</label> 
			<div class="controls">
			<select id="countrySelect"
				name="country">
				<option value="Любая">Любая</option>
			</select>
			</div>
		</div>
		<div class="control-group">
			<label  class="control-label" for="city">Город:</label> 
			<div class="controls">
			<select id="citySelect" name="city">
				<option value="Любой">Любой</option>
			</select>
			</div>
		</div>
		<div class="control-group">
		<div id="formFooter" class="controls">
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
		<c:when test="${not empty candidates}">
			<h1>Cписок всех возможных кандидатов:</h1>
			<table class="table table-condensed">
				<thead>
					<tr>
						<th>Возраст</th>
						<th>Имя</th>
						<th>Пол</th>
						<th>Страна</th>
						<th>Город</th>
						<th>Телефон</th>
						<th>Удалить</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${candidates}" var="candidate">
						<tr>
							<td><c:out value="${candidate.age}" /></td>
							<td><form method="get" action="candidate">
									<input type="hidden" name="command" value="candidateGet">
									<input type="hidden" name="id" value="${candidate.id}">
									<input class="btn btn-info" type="submit" value="${candidate.name}">
								</form></td>
							<td><c:out value="${candidate.male?'Мужской':'Женский'}" /></td>
							<td><c:out
									value="${candidate.country!=null?candidate.country:'-----'}" /></td>
							<td><c:out
									value="${candidate.city!=null?candidate.city:'-----'}" /></td>
							<td><c:out value="${candidate.phone}" /></td>
							<td><form method="post" action="candidate">
									<input type="hidden" name="command" value="candidateDelete">
									<input type="hidden" name="id" value="${candidate.id}">
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
	<p>
	<form class="footer" method="get" action="candidate">
		<input type="hidden" name="command" value="candidateCreate"> <input class="btn btn-success" 
			type="submit" value="Зарегистрировать нового кандидата">
	</form>
	</p>
	</div>
</body>
</html>