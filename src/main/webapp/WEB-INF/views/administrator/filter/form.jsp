<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<acme:form>
	<acme:form-double code="administrator.spamfilter.form.label.threshold" path="threshold"/>
			
	<acme:form-submit test="${command == 'update'}" code="manager.task.form.button.update" action="/administrator/spamfilter/update"/>
</acme:form>