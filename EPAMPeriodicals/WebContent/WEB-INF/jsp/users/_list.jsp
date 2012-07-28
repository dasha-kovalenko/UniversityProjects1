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
<c:choose>
	<c:when test="${not empty users}">
		<h1><fmt:message key="userList"/>:</h1>
		<table class="table table-striped table-condensed">
			<thead>
				<tr>
					<th><fmt:message key="userName"/></th>
					<th><fmt:message key="isAdmin"/></th>
					<th><fmt:message key="deleteUser"/></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td><a href="periodicals?command=userEdit&id=${user.id}"><c:out
									value="${user.username}"/></a></td>
						<td><c:out value="${user.admin}" /></td>
						<td><a
							href="periodicals?command=userDelete&id=${user.id}"
							class="btn btn-warning"><fmt:message key="deleteButton"/></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<h3>
			<fmt:message key="noUsers"/>
		</h3>
	</c:otherwise>
</c:choose>
</fmt:bundle>