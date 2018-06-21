<%-- 
    Document   : ViewRules
    Created on : 11-Dec-2016, 13:24:49
    Author     : garyl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:master>
    <jsp:attribute name="title">Fraud & Risk Manager</jsp:attribute>
    <jsp:attribute name="pageheader">Rules</jsp:attribute>
    <jsp:attribute name="mainBody">
        <table >
                <th>Type</th>
                <th>Rule</th>
                <th>Description</th>
                <th>Decision</th>
                <th>Risk Score</th>
                <th>Enabled</th>
                <c:forEach items="${rules}" var="rule">
                    <tr>
                        <td><c:out value="${rule.type}"/></td>
                        <td>
                            <form method="POST" action="EditRule">
                                <button class="btn-link" value="${rule.ruleNo}"name="ruleNo"><c:out value="${rule.name}"/></button>
                            </form>
                        </td>
                        <td><c:out value="${rule.description}"/></td>
                        <td><c:out value="${rule.decision}"/></td>
                        <td><c:out value="${rule.score}"/></td>
                        <td><c:out value="${rule.enabled}"/></td>
                    </tr>
                </c:forEach>
        </table>
    </jsp:attribute>
</t:master>