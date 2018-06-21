<%-- 
    Document   : Referals
    Created on : 10-Dec-2016, 15:59:18
    Author     : garyl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master>
    <jsp:attribute name="title">Referred Applications</jsp:attribute>
    <jsp:attribute name="pageheader">Referred Applications</jsp:attribute>
    <jsp:attribute name="mainBody">
        <table id="myTable" class="tablesorter">
            <thead>
                <tr>
                    <th>Application Reference</th>
                    <th>Application Date</th>
                    <th>Product</th>
                    <th>Applicant</th>
                    <th>Recommendation</th>
                    <th>Risk Score</th>
                </tr>    
            </thead>
            <tbody>
                <c:forEach items="${applications}" var="application">
                    <tr>
                        <td>
                            <form method="POST" action="ViewApplication">
                                <input class="btn-link" type="submit" value="${application.reference}" name="reference"/>
                            </form>
                        </td>
                        <td><c:out value="${application.applicationDateString}"/></td>
                        <td><c:out value="${application.product.description}"/></td>
                        <td><c:out value="${application.mainApp.fullname}"/></td>
                        <td><c:out value="${application.recommendation}"/></td>
                        <td><c:out value="${application.riskScore}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</t:master>
