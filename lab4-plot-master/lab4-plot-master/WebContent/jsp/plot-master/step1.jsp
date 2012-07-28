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
<jsp:include page="/js/plot-master/step1.js" flush="true"/>
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
<jsp:include page="/css/plot-master/step1.css" flush="true"/>
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
	<h1>Внешний вид графика:</h1>
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
	<form  class="span10 row-fluid form-horizontal" method="post" action="plot">
	<fieldset class="span12" >  
		<input type="hidden" name="command" value="step1">
		<div class="control-group">
			<label class="control-label" for="name">Название:</label> 
			<div class="controls">
			<input class="input-xlarge" type="text" name="name" value="График уровня зарплаты" 
				placeholder="Введите название графика" pattern="[0-9a-zA-Zа-яА-Я ]{3,25}"required>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="description">Краткое описание:</label> 
			<div class="controls">
			<input class="input-xlarge" type="text" name="description" value="Средний уровень зарплаты в зависимости возраста" 
				placeholder="Кратко опишите результаты" pattern="[0-9a-zA-Zа-яА-Я ]{3,50}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="signature_x">Название оси X:</label> 
			<div class="controls">
			<input class="input-xlarge" type="text" name="signature_x" value="Возраст" 
				placeholder="Введите название оси X" pattern="[0-9a-zA-Zа-яА-Я ]{3,25}" required>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="signature_y">Название оси Y:</label> 
			<div class="controls">
			<input class="input-xlarge" type="text" name="signature_y" value="Уровень зарплаты" 
				placeholder="Введите название оси Y" pattern="[0-9a-zA-Zа-яА-Я ]{3,25}" required>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="smoothing">Сглаживание:</label> 
			<div class="controls">
			<label class="input-xlarge radio">
			<input type="radio" name="smoothing"
				value="true" checked>
				Присутствует 
				</label>
			<label class="input-xlarge radio">
			<input type="radio" name="smoothing"
				value="false">
				Отсутствует
			</label>
			</div>
		</div>
		<div class="control-group">
            <label class="control-label" for="signature_points">Подписывать точки</label>
            <div class="controls">
              <label class="checkbox">
                <input type="checkbox" name="signature_points" value="true">
              </label>
              <p class="input-xlarge help-inline"><strong class="badge badge-info">Замечание:</strong> При наведении курсора на точку графика появляется ее краткое описание.</p>
            </div>
          </div>
		<div class="control-group">
			<div class="controls "> 
			<div class="row-fluid">
				<input class="span8 btn btn-inverse" type="reset" value="Значения по умолчанию">
				</div> 
				<div class="row-fluid">
				<input class="span8 btn btn-success" type="submit" value="Указать параметры модели">
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