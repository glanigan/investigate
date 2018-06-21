<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master>
    <jsp:attribute name="title">Fraud & Risk Manager</jsp:attribute>
    <jsp:attribute name="pageheader">${title}</jsp:attribute>
    <jsp:attribute name="mainBody">
        <p>${message}</p>
    </jsp:attribute>
</t:master>