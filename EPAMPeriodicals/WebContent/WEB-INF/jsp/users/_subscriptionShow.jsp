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
		<c:when test="${not empty editions}">
			<h1>
				<fmt:message key="editionListOfSubscription" />
				:
			</h1>
			<table class="table table-striped table-condensed">
				<thead>
					<tr>
						<th><fmt:message key="title" /></th>
						<th><fmt:message key="description" /></th>
						<th><fmt:message key="price" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${editions}" var="edition">
						<tr>
							<c:choose>
								<c:when test="${sessionScope.admin==true}">
									<td><a
										href="periodicals?command=editionEdit&id=${edition.id}"><c:out
												value="${edition.title}" /></a></td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${edition.title}" /></td>
								</c:otherwise>
							</c:choose>
							<td><c:out value="${edition.description}" /></td>
							<td><c:out value="${edition.price}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<h3>
				<fmt:message key="noEditions" />
			</h3>
		</c:otherwise>
	</c:choose>
</fmt:bundle>