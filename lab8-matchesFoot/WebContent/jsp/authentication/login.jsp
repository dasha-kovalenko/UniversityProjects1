<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
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
<title><s:text name="title.login" /></title>
<s:head />
</head>
<body>
<s:set name="theme" value="'simple'" scope="page" />
	<div id="centerPanel">
		<p>
			<h1><s:text name="title.login" />:</h1>
		</p>
		<s:form id="formPanel" cssClass="form-horizontal" action="login"
			method="post" accept-charset="utf-8">
			<div class="control-group">
				<label class="control-label" for="loginName"><s:text name="label.name" />:</label>
				<div class="controls">
					<s:textfield name="loginName" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="loginPassword"><s:text name="label.password" />:</label>
				<div class="controls">
					<s:password name="loginPassword" />
				</div>
			</div>
			<div class="control-group">
				<div id="formFooter" class="controls inline">
					<s:url id="createUser" namespace="/" action="createUser" />
					<s:a cssClass="btn btn-info" href="%{createUser}">
						<s:text name="label.register" />
					</s:a>
					<s:submit cssClass="btn btn-success btn-large" key="label.login"/>
				</div>
			</div>
		</s:form>
		<p>
			<s:fielderror cssClass="alert alert-error" />
		</p>
		<jsp:include page="/jsp/i18n.jsp" flush="true"/>
	</div>
</body>
</html>