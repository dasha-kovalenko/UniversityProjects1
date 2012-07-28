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
	<jsp:include page="/css/bootstrap/js/bootstrap.min.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/js/highcharts/highcharts.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/js/highcharts/themes/gray.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/js/layouts/base.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/js/plot-master/step3.js" flush="true"/>
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
<jsp:include page="/css/plot-master/step3.css" flush="true"/>
</style>
<title>Мастер создания графиков - внешний вид графика</title>
</head>
<body>
<jsp:include page="/jsp/layouts/header.jsp" flush="true"/>
<div class="container-fluid">
<div class="row-fluid">
<div class="span3">&zwnj;</div>
<div id="centerPanel" class="span6">
	<p>
	<h1>Построенный график:</h1>
	</p>
	<div class="row-fluid">
	<div id="plot" class="span12"></div>
	</div>
	</div>
	</div>
	</div>
</body>
</html>