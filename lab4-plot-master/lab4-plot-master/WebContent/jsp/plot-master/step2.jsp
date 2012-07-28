<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	<jsp:include page="/js/jquery-1.7.1.min.js" flush="true"/>
</script>
<script type="text/javascript">
<jsp:include page="/js/plot-master/step2.js" flush="true"/>
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
<jsp:include page="/css/layouts/base.css" flush="true"/>
</style>
<style type="text/css">
<jsp:include page="/css/plot-master/step2.css" flush="true"/>
</style>
<title>Мастер создания графиков - внешний вид графика</title>
</head>
<body>
<jsp:include page="/jsp/layouts/header.jsp" flush="true"/>
<div class="container-fluid">
<div class="row-fluid">
<div class="span3">&zwnj;</div>
<div id="centerPanel" class="row-fluid span6">
	<p>
	<h1>Параметры модели:</h1>
	</p>
	<c:if test="${not empty incorrectData}">
	<div class="alert alert-error">
	 <a class="close">×</a>
		<p>
			<c:out value="${incorrectData}" />
		</p>
		</div>
	</c:if>
	<div class="span1">&zwnj;</div>
	<form class="span10 row-fluid form-horizontal" method="post" action="plot">
	<fieldset class="span12" >
		<input type="hidden" name="command" value="step2">
		<input id="sequence_number" type="hidden" name="sequence_number" value="1">
		<div class="control-group">
			<label class="control-label" for="min_x">Промежуток по оси X:</label> 
			<div class="controls">
			<input class="input-xlarge" type="number" name="min_x" value="18" 
				min="-1000" max="1000" required>
			<input class="input-xlarge" type="number" name="max_x" value="80" 
				min="-1000" max="1000" required>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="min_y">Промежуток по оси Y:</label> 
			<div class="controls">
			<input class="input-xlarge" type="number" name="min_y" value="50" 
				min="-1000" max="1000" required>
			<input class="input-xlarge" type="number" name="max_y" value="400" 
				min="-1000" max="1000" required>
			</div>
		</div>
		<div class="control-group">
			<div id="sequences">
				<custom:sequence sequence_number="1" />
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<div id="remove_sequence" class="btn btn-info">
					Удалить последовательность
				</div>
				<div id="add_sequence" class="btn btn-info">
					Добавить последовательность
				</div>
				<p class="input-xlarge help-block"><strong class="badge badge-info">Замечание:</strong> Максимум 5.</p>				
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
			<div class="row-fluid">
				<input class="span8 btn btn-inverse" type="reset" value="Значения по умолчанию">
				</div>
				<div class="row-fluid">
				<input class="span8 btn btn-success" type="submit" value="Построить график!">
			</div>
			</div>
		</div>
		</fieldset>
	</form>
	</div>
	</div>
	</div>
</body>
</html>