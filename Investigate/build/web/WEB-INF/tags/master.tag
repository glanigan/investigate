<%-- 
    Document   : mastertag
    Created on : 10-Dec-2016, 15:30:09
    Author     : garyl
--%>

<%@tag description="Master template page" pageEncoding="UTF-8"%>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="pageheader" fragment="true" %>
<%@attribute name="mainBody" fragment="true" %>

<%-- any content can be specified here e.g.: --%>
<html>
    <head>
        <title><jsp:invoke fragment="title"/></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="style/reset.css" rel="stylesheet" type="text/css"/>
        <link href="style/master.css" rel="stylesheet" type="text/css"/>
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700" rel="stylesheet">
    </head>
    <body>
        <div id="container">
            <nav>
                <ul>
                    <li><a href="Home.jsp">Home</a></li>
                    <li><a href="ViewReferals">Investigate</a></li>
                     <li><a href="Dashboard.jsp">Dashboard</a></li>
                    <li class="dropdown"><a class="dropbtn" href="CreateApplication.jsp">Application</a>
                        <div class="dropdown-content">
                            <a href="CreateApplication.jsp">Create Application</a>
                            <a href="ApplicationSearch.jsp">Search</a>
                        </div>
                    </li>
                    <li class="dropdown"><a class="dropbtn" href="Configuration.jsp">Configuration</a>
                        <div class="dropdown-content">
                            <a href="ViewRules">Rules</a>
                            <a href="#">Configure System Parameters</a>
                        </div>
                    </li>
                    <li style="float:right;"><a>Logout</a></li>
                    <form action="ViewApplication" method="POST" style="display:inline-block; float:right; margin-top:0.75em; margin-bottom: auto; margin-right:10px; padding:0 0 0 0; overflow: hidden;">
                        <input type="text" name="reference" placeholder="Search"/>
                    </form>
                </ul>
            </nav>
            <header id="PageTitle">
                <h1><jsp:invoke fragment="pageheader"/></h1>
            </header>
            <div id="MainBody">
                <jsp:invoke fragment="mainBody"/>
            </div>
            <div class="footerPlaceholder"/>
            <footer>
                <p>Copyright 2016</p>
            </footer>  
        </div>
    </body>  
</html>
