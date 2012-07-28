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

	<div class=row>
		<div class=span1>&nbsp;</div>
		<h1><fmt:message key="updateUser"/> ${user.username}</h1>
		<form class="well form-horizontal" action="periodicals">
			<fieldset>
				<input type="hidden" name="command" value="userUpdate"> <input
					type="hidden" name="id" value="${user.id}">
				<div class="control-group">
					<label class="control-label"><fmt:message key="userName"/> </label>
					<div class="controls docs-input-sizes">
						<input class="span3" type="text" name="username"
							placeholder="<fmt:message key="insertNameHere"/> " required
							pattern="[A-Za-zА-Яа-я]{1,50}" value="${user.username}">
						<p class="help-block">
							<fmt:message key="enterUsernameHelpBlock"/>
						</p>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="appendedInput"><fmt:message key="password"/> </label>
					<div class=controls>
						<input type="password" class="span2" name="password"
							placeholder="<fmt:message key="password"/>" pattern="[a-zA-Z0-9]{4,}" required> <input type="password"
							class="span2" name="passwordConfirmation"
							placeholder="<fmt:message key="passwordConfirmation"/> " pattern="[a-zA-Z0-9]{4,}" required>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="optionsCheckbox"><fmt:message key="admin"/> </label>
					<div class="controls">
						<input type="checkbox" id="checkboxOptions" value="true" name="admin"><span class="help-inline"><fmt:message key="enterAdminHelpBlock"/></span>
					</div>
				</div>


				<div class="form-actions">
					<button type="submit" class="btn btn-primary"><fmt:message key="saveChangesButton"/> </button>
					<button type="reset" class="btn btn-inverse"><fmt:message key="cancelButton"/></button>
				</div>
			</fieldset>
		</form>
	</div>
</fmt:bundle>