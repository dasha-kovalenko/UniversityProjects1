<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
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
<title><s:text name="title.match.list" /></title>
<s:head/>
</head>
<body>
<s:set name="theme" value="'simple'" scope="page" />
<div id="centerPanel">
	<p>
	<h1><s:text name="title.match.list" />:</h1>
	</p>
	<s:form id="formPanel" cssClass="form-horizontal" method="post"
			action="listMatch" accept-charset="utf-8">	 
		<div class="control-group">
			<label class="control-label" for="team"><s:text name="label.team" />:</label> 
			<div class="controls">
			<select name="team">
				<s:iterator value="teamList" status="teamListStatus">
					<option value='<s:property/>'><s:property/></option>
				</s:iterator>
			</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="startDate"><s:text
					name="label.dates" />:</label>
			<div class="controls">
				<s:textfield id="datepicker1" type="text" name="startDate"
				onfocus="$(this).datepicker({
					showOn: 'button',
					buttonImage: 'images/calendar.png',
					buttonImageOnly: true,
					dateFormat: 'yy-mm-dd'
				});"/>
			<s:textfield id="datepicker2" type="text" name="finishDate"
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
				<s:reset  cssClass="btn btn-inverse" value = "Reset"/>
				<s:submit cssClass="btn btn-success btn-large" value = "Find"/>
			</div>
		</div>
	</s:form>
	<p>
		<s:fielderror cssClass="alert alert-error" />
	</p>
		<s:if test="matchList.size() > 0">
			<h1><s:text name="title.match.list.played" />:</h1>
			<table class="table table-condensed">
				<thead>
					<tr>
						<th><s:text name="title.match.team1" /></th>
						<th><s:text name="title.match.team2" /></th>
						<th><s:text name="title.match.count" /></th>
						<th><s:text name="title.match.date" /></th>
						<s:if test="#session.get('admin')==true">
							<th><s:text name="label.delete" /></th>
						</s:if>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="matchList" status="matchListStatus">
						<tr>
							<td><s:property value="team1" /></td>
							<td><s:property value="team2" /></td>
							<td><s:property value="count" /></td>
							<td>
								<s:if test="#session.get('admin')==true">
								<s:url id="matchListUrl#matchListStatus.index"
									action="editMatch">
									<s:param name="id" value="%{id}" />
								</s:url> 
								<s:a cssClass="btn btn-info"
									href="%{matchListUrl#matchListStatus.index}">
									<s:property value="matchdate" />
								</s:a>
								</s:if>
								<s:else>
									<s:property value="matchdate" />
								</s:else>	
								</td>
							<s:if test="#session.get('admin')==true">
								<td><s:url
										id="deleteMatchListUrl#matchListStatus.index"
										action="deleteMatch">
										<s:param name="id" value="%{id}" />
									</s:url> <s:a cssClass="btn btn-warning"
										href="%{deleteMatchListUrl#matchListStatus.index}">
										<s:text name="label.delete" />
									</s:a></td>
							</s:if>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</s:if>
		<s:else>
			<h3><s:text name="label.chooseSearchCriteria" /></h3>
		</s:else>
	<s:url id="logoffURL" action="logoff" />
		<s:a cssClass="btn btn-info" href="%{logoffURL}"><s:text name="label.logoff" /></s:a>
		<jsp:include page="/jsp/userFooter.jsp" flush="true"/>
		<jsp:include page="/jsp/adminFooter.jsp" flush="true" />
	</div>
</body>
</html>