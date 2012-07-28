<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
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
<title><s:text name="title.user.create" /></title>
<s:head />
</head>
<body>
<s:set name="theme" value="'simple'" scope="page" />
<div id="centerPanel">
	<h1><s:text name="title.user.create" />:</h1>
		<s:form id="formPanel" cssClass="form-horizontal" method="post"
			action="saveUser" accept-charset="utf-8">
				<s:push value="user">
					<div class="control-group">
						<label class="control-label" for="name"><s:text name="label.name" />:</label>
						<div class="controls">
							<s:textfield name="name" required="true"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password"><s:text name="label.password" />:</label>
						<div class="controls">
							<s:password name="password" required="true"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="passwordConfirmation"><s:text name="label.password_confirmation" />:</label>
						<div class="controls">
							<s:password name="passwordConfirmation" required="true"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="admin"><s:text name="label.admin" />:</label>
						<div class="controls">
							<s:checkbox name="admin" />
						</div>
					</div>
					<div class="control-group">
						<div id="formFooter" class="controls inline">
							<s:reset cssClass="btn btn-inverse" key="label.default" />
							<s:submit cssClass="btn btn-success btn-large" key="label.register" />
						</div>
					</div>
				</s:push>
		</s:form>
		<p>
			<s:fielderror cssClass="alert alert-error" />
		</p>
		<s:url id="loginPageURL" action="loginPage" />
		<s:a cssClass="btn btn-info" href="%{loginPageURL}"><s:text name="label.login" /></s:a>
		<jsp:include page="/jsp/i18n.jsp" flush="true"/>
	</div>
</body>
</html>