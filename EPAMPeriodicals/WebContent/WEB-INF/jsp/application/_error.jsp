<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:bundle basename="by.kovalenko.periodicals.properties.locale">

	<div class="alert alert-error">
		<input type="hidden" name="command" value="error">
		<h1>
			<c:out value="${incorrectData}" />
		</h1>

	</div>
	<a href="periodicals?command=editionList" class="btn btn-inverse"><fmt:message
			key="backButton" /></a>
</fmt:bundle>

