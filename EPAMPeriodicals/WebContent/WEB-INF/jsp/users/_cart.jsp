<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags"%>
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
		<c:when test="${not empty user.cart.editions}">
			<h1>
				<fmt:message key="editionList" />
				:
			</h1>
			<table class="table table-condensed">
				<thead>
					<tr>
						<th><fmt:message key="title" /></th>
						<th><fmt:message key="description" /></th>
						<th><fmt:message key="price" /></th>
						<th><fmt:message key="deleteButton" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${user.cart.editions}" var="edition">
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
							<td><form method="post" action="periodicals">
									<input type="hidden" name="command"
										value="editionDeleteFromCart"> <input type="hidden"
										name="id" value="${edition.id}"> <a
										href="periodicals?command=editionDeleteFromCart&cartId=${user.cart.id}&editionId=${edition.id}"
										class="btn btn-warning"><fmt:message key="deleteButton" /></a>
								</form></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<h3>  
				<fmt:message key="priceForMonth" />
				: 
				<strong class="badge badge-info">
				<span id="priceForMonth">
					<c:out value="${price}" />
				</span>
				<span id=priceCalculcation> 
				 x 
				 <span id="monthQuantity">
				</span>
				 = 
				 <span id="totalPrice">
				</span>
				</span>
				</strong>
			</h3>
			<br>
			<form action="periodicals" class="form-horizontal">
				<input type="hidden" name="command" value="userSubscriptionCreate">
				<input type="hidden" name="id" value="${user.id}">
				<input id=priceField type="hidden" name="price" value="">
				<c:set var="datePlaceholder">
					<fmt:message key="clickHere"/>
				</c:set>
				<div class="control-group">
					<div class="control-group">
						<label class="control-label" for="startDate"><fmt:message
								key="startDate" />:</label>
						<div class="controls">
							<custom:dateinput name="startDate" id="startDatepicker"
								placeholder="${datePlaceholder}" className="datepicker"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="control-group">
						<label class="control-label" for="finishDate"><fmt:message
								key="finishDate" />:</label>
						<div class="controls">
							<custom:dateinput name="finishDate" id="finishDatepicker"
								placeholder="${datePlaceholder}" className="datepicker"/>
						</div>
					</div>
				</div>

				<div class="form-actions">
					<button id=subscriptionSubmit type="submit" class="btn btn-success" disabled>
						<fmt:message key="registerSubscription" />
					</button>
				</div>
			</form>
		</c:when>
		<c:otherwise>
			<h3>
				<fmt:message key="noEditions" />
			</h3>
		</c:otherwise>
	</c:choose>
</fmt:bundle>