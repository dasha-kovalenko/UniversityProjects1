<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<jsp:include page="/jsp/i18n.jsp" flush="true" />
<s:url id="listMatch" namespace="/" action="listMatch"/>
<s:a cssClass = "btn btn-success btn-large" href = "%{listMatch}">
	<s:text name = "title.match.list" />
</s:a>