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
<title><s:text name="title.user.list" /></title>
<s:head />
</head>
<body>
	<s:set name="theme" value="'simple'" scope="page" />
	<div id="centerPanel">
		<p>
		<h1><s:text name="title.user.list" />:</h1>
		</p>
		<s:if test="userList.size() > 0">
			<table class="table table-condensed">
				<thead>
					<tr>
						<th><s:text name="label.name" /></th>
						<th><s:text name="label.password" /></th>
						<th><s:text name="label.admin" /></th>
						<s:if test="#session.get('admin')==true">
							<th><s:text name="label.delete" /></th>
						</s:if>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="userList" status="userListStatus">
						<tr>
							<td><s:url id="userListUrl#userListStatus.index"
									action="editUser">
									<s:param name="id" value="%{id}" />
								</s:url> <s:if test="#session.get('admin')==true">
									<s:a cssClass="btn btn-info"
										href="%{userListUrl#userListStatus.index}">
										<s:property value="name" />
									</s:a>
								</s:if> <s:else>
									<s:property value="name" />
								</s:else></td>
							<td><s:property value="password" /></td>
							<td><s:property value="admin" /></td>
							<s:if test="#session.get('admin')==true">
								<td><s:url
										id="deleteUserListUrl#userListStatus.index"
										action="deleteUser">
										<s:param name="id" value="%{id}" />
									</s:url> <s:a cssClass="btn btn-warning"
										href="%{deleteUserListUrl#userListStatus.index}">
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


