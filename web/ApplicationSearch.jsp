<%-- 
    Document   : Configuration
    Created on : 10-Dec-2016, 16:00:15
    Author     : garyl
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:master>
    <jsp:attribute name="title">Fraud & Risk Manager</jsp:attribute>
    <jsp:attribute name="pageheader">Search</jsp:attribute>
    <jsp:attribute name="mainBody">

        <form action="ViewApplication" method="POST">
            <label>Reference:</label>
                <input type="text" name="reference"/>
                <input type="submit" value="Search"/><br/>
                <p class="Errormsg">${errorMessage}<p/>
        </form>
    </jsp:attribute>
</t:master>