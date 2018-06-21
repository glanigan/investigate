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
    <jsp:attribute name="pageheader">View Application</jsp:attribute>
    <jsp:attribute name="mainBody">
        <section class="applicationReport">
            <header class="header">
                <h2>Application Report</h2>
                <form action="UpdateApplication" method="POST">
                    <label  style="margin-top: 0.5em; float:right;">Recommendation:<label><c:out value="${application.recommendation}" />
                     <input type="hidden" name="applicationNo" value="${application.appNo}">
                     <input style="float:right; margin-top: 0.05em; " type="submit" value="Update Application"/>
                </form>
            </header>
            <label>Product:</label><c:out value="${application.product}"/>
                <label>Reference:</label><c:out value="${application.reference}"/><br/>
                <label>Application Date:</label><c:out value="${application.applicationDate}"/><br/>
                <label>Fraud Status:</label><c:out value="${application.fraudStatus}"/>
                <label>Application Value:</label><c:out value="${application.value}"/>
        </section>
        <section class="application">
            <header class="header">
                <h2>Applicant Details</h2>
            </header>
                <label>First Name:</label><c:out value="${application.mainApp.firstname}"/><br/>
                <label>Surname:</label><c:out value="${application.mainApp.surname}"/><br/>
                <label>DOB:</label><c:out value="${application.mainApp.stringDob}"/><br/>
                <label>Gender:</label><c:out value="${application.mainApp.gender}"/>
                <label>Income:</label><c:out value="${application.mainApp.income}"/>
            <header class="header">
                <h2>Current Address Details</h2>                
            </header>
                <label>Building Name:</label><c:out value="${application.currentAddress.buildingName}"/><br/>
                <label>Building Number:</label><c:out value="${application.currentAddress.buildingNumber}"/><br/>
                <label>Street:</label><c:out value="${application.currentAddress.street}"/><br/>
                <label>Post Town:</label><c:out value="${application.currentAddress.postTown}"/><br/>
                <label>Postcode:</label><c:out value="${application.currentAddress.postcode}"/>
            <c:if test="${application.telephone.isNull()==false}">
                    <header class="header">
                    <h2>Telephone Details</h2>                
                    </header>
                        <label>Type:</label><c:out value="${application.telephone.type}"/><br/>
                        <label>Number:</label><c:out value="${application.telephone.number}"/>
            </c:if>
            <c:if test="${application.bank.isNull()==false}">
                <header class="header">
                    <h2>Bank Details</h2>                
                </header>
                    <label>Sort Code:</label><c:out value="${application.bank.sortCode}"/><br/>
                    <label>Account Number:</label><c:out value="${application.bank.accountNumber}"/>
            </c:if>
        </section>
         
        <section class="matches" style="margin-bottom:2em;">
            <header class="header">
                <h2>Match Report</h2><p style="float:right;">Risk Score:${application.riskScore}</p>
            </header>
            <c:if test="${matches.isEmpty()==true}"><p>No Matches found</p></c:if>
            <c:if test="${matches.isEmpty()==false}">
                <table style="font-size:0.75em;">
                <c:forEach items="${matches}" var="match">
                        <div class="match">
                            <tr>
                                <td>${match.rule.type}</td>
                                <td>${match.rule.name}</td>
                                <td>
                                    <form method="POST" action="ViewApplication">
                                        <input class="btn-link" type="submit" value="${match.referenceRHS}" name="reference"/>
                                    </form> 
                                </td>
                                <td>${match.rule.score}</td>
                            </tr>
                        </div>
                </c:forEach>
                </table>    
            </c:if>
        </section>
    </jsp:attribute>
</t:master>