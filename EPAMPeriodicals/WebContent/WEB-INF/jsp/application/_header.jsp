<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-target=".nav-collapse"
				data-toggle="collapse"> <span class="icon-bar"></span> <span
				class="icon-bar"></span> <span class="icon-bar"></span>
			</a> <a class="brand" href="${pageContext.request.contextPath}"><fmt:message key="periodicalsHead"/></a>
			<div class="container nav-collapse">
				<c:choose>
					<c:when test="${sessionScope.id==null}">
						<form method="post" class="navbar-search pull-left" action="periodicals">
							<input type="hidden" name="command" value="userLogIn"> <input
								type="text" class="span2" name="username" placeholder="<fmt:message key="userName"/>">
							<input type="password" class="span2" name="password"
								placeholder="<fmt:message key="password"/>"> <input type="submit"
								value="<fmt:message key="signIn"/>" class="btn btn-inverse navbar-search span2">
						</form>
					</c:when>
					<c:otherwise>
						<form class="navbar-search pull-left" action="periodicals">
							<h3><fmt:message key="welcomeHead"/>
								<c:out value="${sessionScope.username}!" />
							</h3>
						</form>
						<ul class="nav pull-right">
							<li><a
								href="periodicals?command=userGetSubscriptions&id=${sessionScope.id}"><fmt:message key="subscriptions"/></a></li>
							<li><a
								href="periodicals?command=userGetCart&id=${sessionScope.id}"><fmt:message key="cart"/></a></li>

							<li class="divider-vertical"></li>
							<li><a href="periodicals?command=userLogOut"><fmt:message key="logOut"/></a></li>
						</ul>

					</c:otherwise>
				</c:choose>
				<ul class="nav pull-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><fmt:message key="languages"/><b class="caret"></b></a>
						<ul class="dropdown-menu">
							<c:set var="localeString" value="&locale=${param.locale}"
								scope="request" />
							<c:set var="query"
								value='${fn:replace(pageContext.request.queryString,localeString,"")}' />

							<li><a href="periodicals?${query}&locale=ru"> <fmt:message key="russian"/> <c:if
										test="${param.locale eq 'ru' }">
										<span class=pull-right>&#10004;</span>
									</c:if>
							</a></li>
							<li class="divider"></li>
							<li><a href="periodicals?${query}&locale=en"> <fmt:message key="english"/> <c:if
										test="${param.locale eq 'en' }">
										<span class=pull-right>&#10004;</span>
									</c:if>
							</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</div>
</div>
</fmt:bundle>
