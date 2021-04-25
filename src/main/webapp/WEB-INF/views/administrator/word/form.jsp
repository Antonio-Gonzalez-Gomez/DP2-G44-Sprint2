<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:if test="${command == 'show'}">
	<jstl:set var="readonly" value="true"/>
</jstl:if>

<jstl:if test="${command == 'create'}">
	<jstl:set var="readonly" value="false"/>
</jstl:if>

<acme:form readonly="${readonly}">
	<acme:form-textbox code="administrator.word.form.label.word" path="word"/>
			
	<acme:form-submit test="${command == 'show'}" code="administrator.word.form.button.delete" action="/administrator/word/delete"/>
	<acme:form-submit test="${command == 'create'}" code="administrator.word.form.button.create" action="/administrator/word/create"/>
	<acme:form-submit test="${command == 'delete'}" code="administrator.word.form.button.delete" action="/administrator/word/delete"/>		
	<acme:form-return code="administrator.word.form.button.return"/>
</acme:form>