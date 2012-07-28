<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="name" required="true" type="java.lang.String"%>
<%@ attribute name="id" required="false" type="java.lang.String" %>
<%@ attribute name="className" required="false" type="java.lang.String" %>
<%@ attribute name="placeholder" required="false"
	type="java.lang.String" rtexprvalue="true"%>

<input id="${id}" class="${className}" type="text" name="${name}"
	onfocus="$(this).datepicker({
					showOn: 'button',
					buttonImage: 'images/calendar.png',
					buttonImageOnly: true, 
					dateFormat: 'yy-mm-dd'
				});"
	placeholder="${placeholder}"
	pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" required>