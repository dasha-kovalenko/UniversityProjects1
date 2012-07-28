<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
<c:choose>
	<c:when test="${not empty param.locale}">
		<fmt:setLocale value="${param.locale}" />
		<c:set var="locale" value="${param.locale}"/>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${not empty sessionScope.locale }">
				<fmt:setLocale value="${sessionScope.locale}" />
			</c:when>
			<c:otherwise>
				<fmt:setLocale value="ru" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
<fmt:setBundle basename="by.kovalenko.periodicals.properties.locale" />
<fmt:bundle basename="by.kovalenko.periodicals.properties.locale">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	<jsp:include page="/js/jquery-1.7.2.min.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/js/jquery-ui-1.8.21.custom.min.js" flush="true"/>
</script>
<script type="text/javascript">
	<jsp:include page="/js/users/cart.js" flush="true"/>
</script>
<style type="text/css">
	<jsp:include page="/css/users/cart.css" flush="true"/>
</style>
<style type="text/css">
	<jsp:include page="/css/jquery-ui-1.8.21.custom.css" flush="true"/>
</style>
<style type="text/css">
	<jsp:include page="/css/jquery.ui.datepicker.css" flush="true"/>
</style>
<jsp:include page="/WEB-INF/jsp/application/_head.jsp" flush="true" />
<title><fmt:message key="cart"/></title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/application/_header.jsp" flush="true" />
	<div class="container">
		<div class="content">
			<div class="row">
				<div class="span9">
					<jsp:include page="/WEB-INF/jsp/users/_cart.jsp" flush="true" /> 
				</div>
				<div class="span1">&nbsp;</div>
				<div class="span3">
					<jsp:include page="/WEB-INF/jsp/application/_rightbar.jsp" flush="true" />
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/jsp/application/_footer.jsp" flush="true" />
</body>
</html>
</fmt:bundle>