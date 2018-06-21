<%-- 
    Document   : Application
    Created on : 11-Dec-2016, 17:32:31
    Author     : garyl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:master>
    <jsp:attribute name="title">Fraud & Risk Manager</jsp:attribute>
    <jsp:attribute name="pageheader">Rule</jsp:attribute>
    <jsp:attribute name="mainBody">
        
        <section>
            <c:forEach items="conditions" var="condition">
                

            </c:forEach>    
        </section>
    </jsp:attribute>
</t:master>