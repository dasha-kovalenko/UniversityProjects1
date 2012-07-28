<%@ tag body-content="empty" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="errorPage" required="false"%>
<%@ attribute name="sequence_number" required="true" type="java.lang.Integer"%>

<c:if test="${empty errorPage}" >
	<c:set var="errorPage" value="${pageContext.request.contextPath}/plot?command=error" />
</c:if>

<%-- <c:if test="${sequence_number < 1 || sequence_number > 5}" >
	<c:redirect context="${errorPage}" url="${pageContext.request.requestURL}"/>
</c:if> --%>

<c:set var="sequence_name" value="Борисовдрев" />
<c:set var="sequence_x" value="20 30 40 50 60 70" />
<c:set var="sequence_y" value="100 200 250 200 150 100" />

<div id="sequence_${sequence_number}" class="sequence">
<label class="control-label" for="sequence_x_${sequence_number}">Последовательность
	#${sequence_number} X и Y:</label>
<div class="controls">
	<input class="input-xlarge" type="text" name='sequence_name_${sequence_number}'
		value="${sequence_name}" placeholder="Название графика"
		pattern="[а-яА-Яa-zA-Z0-9 ]{1,15}" required> <input
		class="input-xlarge" type="text" name='sequence_x_${sequence_number}'
		value="${sequence_x}"
		placeholder='Введите последовательность X # ${sequence_number}' pattern="[0-9 ]{1,100}"
		required> <input class="input-xlarge" type="text"
		name='sequence_y_${sequence_number}' value="${sequence_y}"
		placeholder='Введите последовательность Y # ${sequence_number}' pattern="[0-9 ]{1,100}"
		required>
</div>
</div>