<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:choose>
	<c:when test="${not empty param.locale}">
		<fmt:setLocale value="${param.locale}" />
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
	<div class="row">
		<div class="span1">&nbsp;</div>
		<div class="span3">
			<footer>
				<p>
					&copy;
					<fmt:message key="copyright" />
				</p>
			</footer>
		</div>
	</div>
</fmt:bundle>