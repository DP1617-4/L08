<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${creditCard}">
	<p><span> <spring:message code="creditCard.holder" />: <jstl:out value="${creditCard.holderName }" /> </span> 
	<span> <spring:message code="creditCard.brandName" />: <jstl:out value="${creditCard.brandName}" /> </span>
	<span> <spring:message code="creditCard.Number" />: <jstl:out value="${ccNumber}" /> </span><span>creditCard.CVV				=	CVV number </span>
	<span> <spring:message code="creditCard.Time" />: <jstl:out value="${creditCard.expirationMonth}" />/<jstl:out value="${creditCard.expirationYear}" /> </span><span>creditCard.Year				=	Expiration year</span>
	</p>
		
	<a href="creditcard/lessor/edit.do"><spring:message code="creditCard.edit" /></a>
</jstl:if>
<jstl:if test="${!creditCard}">
	<spring:message code="creditCard.missing" /> <a href="creditcard/lessor/create.do"><spring:message code="creditCard.here" /></a>
</jstl:if>