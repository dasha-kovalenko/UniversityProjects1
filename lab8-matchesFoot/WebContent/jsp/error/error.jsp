<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	<jsp:include page="/js/jquery-1.7.1.min.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/js/candidate/create.js" flush="true"/>
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
	<jsp:include page="/css/errors/error.css" flush="true"/>
</style>
	<title><s:text name="title.error" /></title>
</head>
<body>
<s:set name="theme" value="'simple'" scope="page" />
	<div id="centerPanel">
		<div class="alert alert-error">
			<h1 class="alert-heading"><s:text name="title.error" /></h1>
		</div>
		<s:url id="loginURL" action="loginPage" />
		<s:a cssClass="btn btn-info" href="%{loginURL}"><s:text name="label.login" /></s:a>
		<jsp:include page="/jsp/i18n.jsp" flush="true"/>
	</div>
</body>
</html>