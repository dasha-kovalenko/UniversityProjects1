<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="area_label" required="true" type="java.lang.String"%>
<%@ attribute name="area_value" required="true" type="java.lang.String"%>
<%@ attribute name="area_name" required="true" type="java.lang.String"%>
<%@ attribute name="comment" fragment="true" required="false"%>

<%
	if (!area_label.matches("[A-Za-z ]{2,20}")){
		//System.out.println("error");
		throw new IllegalStateException("Area Label is not matched to the pattern");
	}
%>

<div id=bodyWork>
	<label for="${area_name}" id=work>${area_label}</label>
	<textarea rows="10" cols="5" id="${area_name}" name="${area_name}"
		required>${area_value}</textarea>
	<jsp:invoke fragment="comment"/>
</div>