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
    <jsp:attribute name="pageheader">System Configuration</jsp:attribute>
    <jsp:attribute name="mainBody">
        <section class="applicationReport">
            <header class="header"><h2>Referral Criteria</h2></header>
            <br/><br/>
            <form action="UpdateConfiguration" method="POST" onsubmit="return validateForm()">
                <label>Enable Machine Learning Decisions:</label>
                <c:choose>
                    <c:when test="${Configuration.rDecision==true}">
                        <input name="rDecision" type="checkbox" checked/>
                    </c:when>
                    <c:otherwise>
                        <input name="rDecision" type="checkbox"/>
                    </c:otherwise>
                </c:choose>
                <br/><p>(Refers applications using machine learning model)</p><br/><br/>
                <label>Enable Rule Score Decisions:</label>
                <c:choose>
                    <c:when test="${Configuration.scoreDecision==true}">
                        <input id="riskScoreCheckbox" name="scoreDecision" type="checkbox" checked/>
                        <input id="riskScoreValue" name="scoreDecisionValue" type="text"
                               value="${Configuration.scoreDecisionInt}"/>
                    </c:when>
                    <c:otherwise>
                        <input id="riskScoreCheckbox" name="scoreDecision" type="checkbox" value="0"/>
                        <input id="riskScoreValue" name="scoreDecisionValue" type="text"
                               value="${Configuration.scoreDecisionInt}" hidden=""/>
                    </c:otherwise>
                </c:choose>
                <br/>
                <p>(Refers applications if they have a risk score greater than or equal to the set value)</p><br/><br/>
                <label>Enable Match Decisions:</label>
                <c:choose>
                    <c:when test="${Configuration.matchDecision==true}">
                        <input name="matchDecision" type="checkbox" checked/>
                    </c:when>
                    <c:otherwise>
                        <input name="matchDecision" type="checkbox"/>
                    </c:otherwise>
                </c:choose><br/><p>(Refers applications if they have any matches)</p><br/><br/>
                <input type="submit"/><br/><br/>
            </form>
        </section>
        <script src="script/configuration.js" type="text/javascript"></script>
    </jsp:attribute>
</t:master>