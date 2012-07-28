<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<s:if test="#session.get('admin')==true">
	<s:url id="createMatch" namespace="/" action="createMatch"/>
	<s:a cssClass="btn btn-success btn-large" href="%{createMatch}">
		<s:text name="title.match.create" />
	</s:a>
	<s:url id="listUser" namespace="/" action="listUser"/>
	<s:a  cssClass="btn btn-success btn-large" href="%{listUser}">
		<s:text name="title.user.list" />
	</s:a>
</s:if>