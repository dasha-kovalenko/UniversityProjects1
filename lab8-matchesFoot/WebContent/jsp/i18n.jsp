<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<s:url id="localeEN" namespace="/" action="locale">
	<s:param name="request_locale">en</s:param>
</s:url>
<s:url id="localeRU" namespace="/" action="locale">
	<s:param name="request_locale">ru</s:param>
</s:url>
<s:a class="btn btn-success btn-large" cssClass="btn btn-success btn-large" href="%{localeEN}">English</s:a>
<s:a class="btn btn-success btn-large" cssClass="btn btn-success btn-large" href="%{localeRU}">Русский</s:a>