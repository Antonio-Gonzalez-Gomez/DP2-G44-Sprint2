<%--
- form.jsp
-
- Copyright (C) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
    <acme:form-textbox code="authenticated.task.form.label.title" path="title" readonly="true"/>
    <acme:form-textarea code="authenticated.task.form.label.description" path="description" readonly="true"/>
    <acme:form-url code="authenticated.task.form.label.link" path="link" readonly="true"/>
    <acme:form-moment code="authenticated.task.form.label.startDate" path="startDate" readonly="true"/>
    <acme:form-moment code="authenticated.task.form.label.endingDate" path="endingDate" readonly="true"/>
    <acme:form-double code="authenticated.task.form.label.workload" path="workload" readonly="true"/>
    
    <acme:form-return code="authenticated.task.form.button.return"/>    
</acme:form>



