<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="comments" requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	<spring:message code="comment.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />
	
	<spring:message code="comment.stars" var="starsHeader" />
	<display:column property="stars" title="${starsHeader}" sortable="true" />

	<spring:message code="comment.actor" var="actorHeader" />
	<display:column title="${actorHeader}">
		<a href="user/display.do?userId=${row.actor.id}"><spring:message
				code="comment.actor"/> </a>
	</display:column>
	
</display:table>

<div>
	<a href="socialIdentity/actor/create.do"> 
	<spring:message code="socialidentity.create" />
	</a>
</div>

<input type="button" name="back"
		value="<spring:message code="comment.back" />"
		onclick="location.href = 'actor/list.do';" />&nbsp;
	<br />

<security:authorize access="hasAnyRole('AUDITOR','ADMINISTRATOR','LESSOR','TENANT')">
	<div>
		<a href="comment/commentable/edit.do?actorId=${actorId}"> <spring:message
				code="comment.create" />
		</a>
	</div>
</security:authorize>