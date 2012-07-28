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
	<div class="well sidebar-nav">
		<h3>
			<fmt:message key="navigation" />
		</h3>
		<ul class="nav nav-list">
			<li class="nav-header"><fmt:message key="editions" /></li>
			<c:if test="${sessionScope.admin}">
				<li><a href="periodicals?command=editionCreate"> <span
						class="label label-info"><fmt:message key="createEdition" /></span></a>
				</li>
			</c:if>
			<li><a href="periodicals?command=editionList"><span
					class="label label-info"><fmt:message key="editionList" /></span></a></li>
		</ul>
		<c:if test="${sessionScope.admin}">
			<ul class="nav nav-list">
				<li class="nav-header"><fmt:message key="users" /></li>
				<li><a href="periodicals?command=userList"><span
						class="label label-info"><fmt:message key="userList" /></span></a></li>
			</ul>
		</c:if>
		<c:if test="${sessionScope.id!=null}">
			<ul class="nav nav-list">
				<li class="nav-header"><fmt:message key="userMenu" /></li>
				<li><a
					href="periodicals?command=userGetSubscriptions&id=${sessionScope.id}"><span
						class="label label-info"><fmt:message
								key="userSubscriptions" /></span></a></li>
				<li><a
					href="periodicals?command=userGetCart&id=${sessionScope.id}"><span
						class="label label-info"><fmt:message key="userCart" /></span></a></li>
				<li><a href="periodicals?command=userLogOut"><span
						class="label label-info"><fmt:message key="userLogOut" /></span></a></li>
			</ul>
		</c:if>
	</div>
</fmt:bundle>