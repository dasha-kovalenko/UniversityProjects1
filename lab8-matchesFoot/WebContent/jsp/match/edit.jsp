<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<title><s:text name="title.match.update" /></title>
<s:head/>
</head>
<body>
<s:set name="theme" value="'simple'" scope="page" />
<div id="centerPanel">
	<p>
	<h1><s:text name="title.match.update" /></h1>
	</p>
	<s:form id="formPanel" cssClass="form-horizontal" method="post"
			action="updateMatch" accept-charset="utf-8">	
			<s:push value="model">
				<s:hidden name="id"/>
				<div class="control-group">
					<label class="control-label" for="team1"><s:text
							name="label.match.team1" />:</label>
					<div class="controls">
						<s:textfield name="team1" required="true"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="team2"><s:text
							name="label.match.team2" />:</label>
					<div class="controls">
						<s:textfield name="team2" required="true"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="count1"><s:text
							name="label.match.count" />:</label>
					<div class="controls inline">
						<s:textfield name="count1" required="true"/>
						<s:label class="inline"> : </s:label>	
						<s:textfield name="count2" required="true"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="formattedDate"><s:text
							name="label.match.date" />:</label>
					<div class="controls">
						<s:textfield type="text" name="formattedDate"
							onfocus="$(this).datepicker({
							showOn: 'button',
							buttonImage: 'images/calendar.png',
							buttonImageOnly: true,
							dateFormat: 'yy-mm-dd'
							});"/>
					</div>
				</div>
				<div class="control-group">
					<div id="formFooter" class="controls inline">
						<s:reset cssClass="btn btn-inverse" key="label.default" />
						<s:submit cssClass="btn btn-success btn-large"
							key="label.match.update" />
					</div>
				</div>
			</s:push>
	</s:form>
		<p>
			<s:fielderror cssClass="alert alert-error" />
		</p>
		<s:url id="loginPageURL" action="loginPage" />
		<s:a cssClass="btn btn-info" href="%{loginPageURL}">
			<s:text name="label.login" />
		</s:a>
		<jsp:include page="/jsp/userFooter.jsp" flush="true" />
		<jsp:include page="/jsp/adminFooter.jsp" flush="true" />
	</div>
</body>
</html>