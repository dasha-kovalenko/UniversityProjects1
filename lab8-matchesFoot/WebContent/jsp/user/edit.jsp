<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<title><s:text name="title.user.edit" /></title>
<s:head/>
</head>
<body>
<s:set name="theme" value="'simple'" scope="page" />
<div id="centerPanel">
	<p>
	<h1><s:text name="title.user.edit" />:</h1>
	</p>
		<s:form id="formPanel" cssClass="form-horizontal" method="post"
			action="updateUser" accept-charset="utf-8">
				<s:push value="user">
					<s:hidden name="id"/>
					<div class="control-group">
						<label class="control-label" for="name"><s:text name="label.name" />:</label>
						<div class="controls">
							<s:textfield name="name" required="true"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password"><s:text name="label.password" />:</label>
						<div class="controls">
							<s:password name="password" value="" required="true"/>
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
						<div id="formFooter" class="controls">
							<s:reset cssClass="btn btn-inverse" key="label.default" />
							<s:submit cssClass="btn btn-success btn-large"
								key="label.update"/>
						</div>
					</div>
				</s:push>
		</s:form>
		<p>
			<s:fielderror cssClass="alert alert-error" />
		</p>
		<s:url id="listUserURL" action="listUser" />
		<s:a cssClass="btn btn-info" href="%{listUserURL}"><s:text name="label.listUsers" /></s:a>
		<s:url id="logoffURL" action="logoff" />
		<s:a cssClass="btn btn-info" href="%{logoffURL}"><s:text name="label.logoff" /></s:a>
		<jsp:include page="/jsp/userFooter.jsp" flush="true"/>
		<jsp:include page="/jsp/adminFooter.jsp" flush="true" />
		
	</div>
</body>
</html>