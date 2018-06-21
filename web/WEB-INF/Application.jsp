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
                <label  style="margin-top: 0.5em; float:right;">Recommendation:<label><c:out value="${application.recommendation}" />
                        </header>
                        <form method="POST" action="UpdateApplication"
                        <label>Application Decision:</label><select name="decision">
                            <c:if test="${application.decision.equals('PENDING')==true}">
                                <option value="PENDING" selected>Pending</option>
                                <option value="APPROVE">Approve</option>
                                <option value="DECLINE">Decline</option>
                                <option value="REFER">Refer</option>
                            </c:if>
                            <c:if test="${application.decision.equals('APPROVED')==true}">
                                <option value="PENDING">Pending</option>
                                <option value="APPROVE" selected>Approve</option>
                                <option value="DECLINE">Decline</option>
                                <option value="REFER">Refer</option>
                            </c:if>
                            <c:if test="${application.decision.equals('DECLINE')==true}">
                                <option value="PENDING">Pending</option>
                                <option value="APPROVE">Approve</option>
                                <option value="DECLINE" selected>Decline</option>
                                <option value="REFER">Refer</option>
                            </c:if>
                            <c:if test="${application.decision.equals('REFER')==true}">
                                <option value="PENDING">Pending</option>
                                <option value="APPROVE">Approve</option>
                                <option value="DECLINE">Decline</option>
                                <option value="REFER" selected>Refer</option>
                            </c:if>
                        </select></br></br>
                        <label>Fraud Status:</label><select name="fraudStatus">
                            <c:choose>
                                <c:when test="${application.fraudStatus.equals('FRAUD')==true}">
                                    <option value="CLEAR">Clear</option>
                                    <option value="FRAUD" selected>Fraud</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="CLEAR" selected>Clear</option>
                                    <option value="FRAUD">Fraud</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                        <br/><br/>
                        <label>Investigation Status:</label><select name="investigationStatus">
                            <c:choose>
                                <c:when test="${application.investigationStatus.equals('UNWORKED')==true}">
                                    <option value="UNWORKED" selected>Unworked</option>
                                    <option value="PENDING">Pending</option>
                                    <option value="COMPLETE">Completed</option>
                                </c:when>
                                <c:when test="${application.investigationStatus.equals('PENDING')==true}">
                                    <option value="UNWORKED">Unworked</option>
                                    <option value="PENDING" selected>Pending</option>
                                    <option value="COMPLETE">Completed</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="UNWORKED">Unworked</option>
                                    <option value="PENDING">Pending</option>
                                    <option value="COMPLETE" selected>Completed</option>
                                </c:otherwise>
                            </c:choose>
                        </select><br/><br/>
                        <input type="hidden" name="applicationNo" value="${application.appNo}">
                        <input type="submit" value="Update Application"/>
                        </form><br/>
                        </section>
                        <section class="application">
                            <header class="header">
                                <h2>Application Details</h2>
                            </header>
                            <label>Product:</label><c:out value="${application.product.description}"/><br/>
                            <label>Reference:</label><c:out value="${application.reference}"/><br/>
                            <label>Application Date:</label><c:out value="${application.applicationDateString}"/><br/>   
                            <label>Application Value:</label><c:out value="${application.value}"/><br/><br/>
                            <header class="header">
                                <h2>Applicant Details</h2>
                            </header>
                            <label>Title:</label><c:out value="${application.mainApp.title}"/><br/>
                            <label>First Name:</label><c:out value="${application.mainApp.firstname}"/><br/>
                            <label>Middle Names:</label><c:out value="${application.mainApp.middlename}"/><br/>
                            <label>Surname:</label><c:out value="${application.mainApp.surname}"/><br/>
                            <label>DOB:</label><c:out value="${application.mainApp.stringDob}"/><br/>
                            <label>Gender:</label><c:out value="${application.mainApp.gender}"/><br/>
                            <label>Income:</label><c:out value="${application.mainApp.income}"/><br/><br/>
                            <header class="header">
                                <h2>Current Address Details</h2>                
                            </header>
                            <label>Building Name:</label><c:out value="${application.currentAddress.buildingName}"/><br/>
                            <label>Building Number:</label><c:out value="${application.currentAddress.buildingNumber}"/><br/>
                            <label>Street:</label><c:out value="${application.currentAddress.street}"/><br/>
                            <label>Post Town:</label><c:out value="${application.currentAddress.postTown}"/><br/>
                            <label>Postcode:</label><c:out value="${application.currentAddress.postcode}"/><br/><br/>
                            <c:if test="${application.telephone.isNull()==false}">
                                <header class="header">
                                    <h2>Telephone Details</h2>                
                                </header>
                                <label>Type:</label><c:out value="${application.telephone.type}"/><br/>
                                <label>Number:</label><c:out value="${application.telephone.number}"/><br/><br/>
                            </c:if>
                            <c:if test="${application.bank.isNull()==false}">
                                <header class="header">
                                    <h2>Bank Details</h2>                
                                </header>
                                <label>Sort Code:</label><c:out value="${application.bank.sortCode}"/><br/>
                                <label>Account Number:</label><c:out value="${application.bank.accountNumber}"/><br/><br/>
                            </c:if>
                        </section>

                        <section class="matches">
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