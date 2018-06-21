<%-- 
    Document   : Application
    Created on : 10-Dec-2016, 15:57:56
    Author     : garyl
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master>
    <jsp:attribute name="title">Create Application</jsp:attribute>
    <jsp:attribute name="pageheader">Create Application</jsp:attribute>
    <jsp:attribute name="mainBody">
        <form id="Appform" action="LoadApplication" method="POST">
            <header class="header">
                <h2>Application Details</h2>
                <label>Fraud Status</label>
                <select name=applicationFraudStatus">
                    <option value="CLEAR">Clear</option>
                    <option value="FRAUD">Fraud</option>
                </select>
            </header>
             <div id="LoadFormBody">
                 <label>Product:</label> 
                    <select name="product">
                        <c:forEach items="${products}" var="product">
                           <option value="${product.productNo}">${product.description}</option>
                        </c:forEach>
                    </select><br><br>
            <label>Application Reference:</label> <input name="appRef" type="text" maxlength="64" required="Y"></input><br><br>
            <label>Application Date:</label> <input name="AppDate" type="date"></input><br><br>
                <label>Application Decision:</label> 
                    <select name="applicationDecision">
                        <option value="PENDING">Pending</option>
                        <option value="APPROVE">Approve</option>
                        <option value="DECLINE">Decline</option>
                        <option value="REFER">Refer</option>
                    </select><br><br>
                <label>Application Value:</label> <input name="applicationValue" type="text"></input><br>
              </div>
            <header class="header">
                <h2>Person Details</h2>
            </header>
            <div id="LoadFormBody"> 
                <label>Title:</label>
                <select name="title" type="text">
                    <option value="NULL"></option>
                    <option value="MR">Mr</option>
                    <option value="MISS">Miss</option>
                    <option value="MRS">Mrs</option>
                    <option value="DR">Dr</option>
                </select><br><br>
                <label>First Name:</label>
                    <input name="firstname" type="text" required="Y"></input><br><br>
                <label>Middle Name:</label>
                    <input name="middlename" type="text"></input><br><br>
                <label>Surname:</label>
                    <input name="surname" type="text" required="Y"></input><br><br>
                <label>Date of Birth:</label>
                    <input name="dob" type="date" required="Y"></input><br><br>
                    <label>Gender:</label>
                        <select name="gender" placeholder="Select Gender">
                            <option value="U">Unknown</option>
                            <option value="M">Male</option>
                            <option value="F">Female</option>
                            <option value="O">Other</option>
                        </select><br><br>
                <label>Income:</label>
                    <input name="income" type="text"></input><br><br>
            </div>
            <header class="header">
                <h2>Address Details</h2>
            </header>
                <div id="LoadFormBody"> 
                    <label>Building Name:</label>
                        <input type="text" name="buildingName" maxlength="50"></input><br><br>
                    <label>Building Number:</label>
                        <input type="text" name="buildingNumber" maxlength="20"></input><br><br>
                    <label>Street:</label>
                        <input type="text" name="street" maxlength="20"></input><br><br>
                    <label>Post Town:</label>
                        <input type="text" name="postTown" maxlength="20"></input><br><br>
                    <label>Postcode:</label>
                        <input type="text" name="postcode" maxlength="20" required></input><br><br>
                    <label>Residential Status:</label>
                        <input type="text" name="residentialStatus" maxlength="20"></input><br><br>
                </div>
            <header class="header">
                <h2>Contact Details</h2>
            </header>
            <div id="LoadFormBody">
                <label>Type:</label>
                    <select name="telephoneType">
                        <option value="u">Unknown</option>
                        <option value="h">Home</option>
                        <option value="m">Mobile</option>
                        <option value="w">Work</option>
                        <option value="o">Other</option>
                    </select>
                    <label>Telephone Number:</label>
                    <input name='telephoneNumber' type="text" maxlength="20" required></input><br><br>
                    <label>Email:</label>
                    <input name='email' type="text" maxlength="20" required></input><br><br>
            </div>
            <header class="header">
                <h2>Bank Details</h2>
            </header>
            <div id="LoadFormBody">
                <label>Sort Code:</label>
                        <input name="sortCode"type="text" maxlength="10"></input>
                <label>Account Number:</label>
                        <input name="accountNumber" type="text" maxlength="18"></input><br><br>
            </div>
            <input type="submit"/>  
        </form>
    </jsp:attribute>
</t:master>