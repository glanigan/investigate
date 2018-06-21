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
        <header class="header">
            <form method="POST" action="UpdateRule">
            <input hidden="y" name="ruleNo" value ="${rule.ruleNo}"/>
            <label>Name:</label><input type="text" name="name" value="${rule.name}"/>
            <label>Description:</label><input type="text" name="description" value="${rule.description}"/>
            <label>Risk Score:</label><input type="number" name="score" value="${rule.score}"/>
            <label>Enabled:</label><input type="checkbox" name="enabled" checked="${rule.enabled}"/> 
            <input type="submit" >
                
            </form>
            
        </header>
        <section>
            <h2>Rule Conditions</h2>
            <table id="conditionTable">
                <thead>
                    <tr>
                        <th>Type</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Parameter</th>
                    </tr>
                </thead>
                <tbody id="conbody">
                    <c:forEach items="${rule.conditions}" var="condition">
                        <tr>
                            <td><c:out value="${condition.type}"/></td>
                            <td><c:out value="${condition.name}"/></td>
                            <td><c:out value="${condition.description}"/></td>
                            <td><c:out value="${condition.parameter}"/></td>
                        </tr>
                    </c:forEach>  
                </tbody>
            </table>
              
        </section>
    </jsp:attribute>
</t:master>