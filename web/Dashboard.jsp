<%-- 
    Document   : Dashboard
    Created on : 10-Dec-2016, 16:00:43
    Author     : garyl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master>
    <jsp:attribute name="title">Fraud & Risk Manager</jsp:attribute>
    <jsp:attribute name="pageheader">Dashboard</jsp:attribute>
    <jsp:attribute name="mainBody">
       
        <output id="numApproved" hidden="Y"/>324</output>
        <output id="numRefer" hidden="Y"/>34</output>
        <p>TODO: Content</p>
        
        <div style="display: block; float: left; height:15em;  max-width:40%; padding:1em 1em 1em 1em; max-height:200px; border: #333">
            <canvas id="myChart"></canvas>
        </div>
        <div style="display: block; float: left; width:40%; max-width:40%; max-height:700px;">
            <canvas id="Chart2"></canvas>
        </div>
        <script src="script/Chart.js" type="text/javascript"></script>  
        <script src="script/Dashboard.js" type="text/javascript"></script>
    </jsp:attribute>
</t:master>