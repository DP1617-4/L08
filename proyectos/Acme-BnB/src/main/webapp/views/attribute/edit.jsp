<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="attribute/administrator/edit.do" modelAttribute="attribute">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="attribute.name" path="attributeName"/>
	<br />
	
	<acme:submit name="save" code="attribute.save"/>

	<jstl:if test="${attribute.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="attribute.delete" />"
			onclick="return confirm('<spring:message code="attribute.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<acme:cancel url="attribute/administrator/list.do" code="attribute.cancel"/>

	

</form:form>
