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
		<c:when test="${not empty subscriptions}">
			<h1>
				<fmt:message key="subscriptionList" />:
			</h1>

			<table class="table table-striped">
				<thead>
					<tr>
						<th><fmt:message key="startDate"/></th>
						<th><fmt:message key="finishDate"/></th>
						<th><fmt:message key="price"/></th>
						<th><fmt:message key="showEditions" /></th>
						<th><fmt:message key="paid"/></th>
						<th><fmt:message key="deleteButton"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${subscriptions}" var="subscription">
						<tr>
							<td><c:out value="${subscription.startDate}" /></td>
							<td><c:out value="${subscription.finishDate}" /></td>
							<td><c:out value="${subscription.price}" /></td>
							<td><a
								href="periodicals?command=userSubscriptionShow&id=${subscription.id}"
								class="btn btn-info"> <fmt:message key="showEditions"/> </a></td>
							<td><c:choose>
									<c:when test="${subscription.paid}">
										<span class="btn btn-success"><fmt:message key="paid"/> :)</span>
									</c:when>
									<c:otherwise>
										<form method="post" action="periodicals">
											<input type="hidden" name="command"
												value="userSubscriptionPay"> <input type="hidden"
												name="userId" value="${user.id}"> <input
												type="hidden" name="subscriptionId"
												value="${subscription.id}"> <input
												class="btn btn-danger" type="submit" value="<fmt:message key="pay"/>">
										</form>
									</c:otherwise>
								</c:choose></td>
							<td><form method="post" action="periodicals">
									<input type="hidden" name="command"
										value="userSubscriptionDelete"> <input type="hidden"
										name="userId" value="${user.id}"> <input type="hidden"
										name="subscriptionId" value="${subscription.id}"> <input
										class="btn btn-warning" type="submit" value="<fmt:message key="deleteButton"/>">
								</form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


		</c:when>
		<c:otherwise>
			<h3>
				<fmt:message key="noSubscriptions"/>
			</h3>
		</c:otherwise>
	</c:choose>
</fmt:bundle>