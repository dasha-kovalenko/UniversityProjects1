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
		<h1>
			<fmt:message key="updateEdition" />
			${edition.title}
		</h1>
		<form method="post" class="well form-horizontal" action="periodicals">
			<fieldset>
				<input type="hidden" name="command" value="editionUpdate"> <input
					type="hidden" name="id" value="${edition.id}">
				<div class="control-group">
					<label class="control-label" for=title><fmt:message
							key="title" /></label>
					<div class="controls docs-input-sizes">
						<input class="span3" type="text" name="title"
							placeholder="<fmt:message key="insertTitleHere"/>" required
							pattern="[A-Za-zА-Яа-я0-9 ]{1,50}" value="${edition.title}">
						<p class="help-block">
							<fmt:message key="enterTitleHelpBlock" />
						</p>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for=description><fmt:message
							key="description" /></label>
					<div class="controls docs-input-sizes">
						<textarea class="span3" name="description"
							placeholder="<fmt:message key="insertDescriptionHere"/>" required
							maxlength=400 rows=3>${edition.description}</textarea>
						<p class="help-block">
							<fmt:message key="enterDescriptionHelpBlock" />
						</p>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="appendedInput"><fmt:message
							key="price" /></label>
					<div class="controls">
						<div class="input-append">
							<input class="span3" name="price" id="appendedInput" size="16"
								placeholder="<fmt:message key="insertPriceHere"/>" type="number"
								required min=1 max=2000 step="0.01" pattern="[1-9][0-9]{0,3}"
								value="${edition.price}">
						</div>
					</div>
				</div>
				<div class="form-actions">
					<button type="submit" class="btn btn-primary">
						<fmt:message key="saveChangesButton" />
					</button>
					<button type="reset" class="btn btn-inverse">
						<fmt:message key="cancelButton" />
					</button>
				</div>
			</fieldset>
		</form>
	</div>
</fmt:bundle>