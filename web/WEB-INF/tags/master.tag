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
        <link href="style/theme.blue.css" rel="stylesheet" type="text/css"/>
        <script src="script/jquery-2.2.0.min.js" type="text/javascript"></script>
        <script src="script/jquery.tablesorter.js" type="text/javascript"></script>
        <script src="script/test.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="container">
            <nav>
                <ul>
                    <li><a href="ViewReferals">Investigate</a></li>
                     <%--<li><a href="Dashboard.jsp">Dashboard</a></li>--%>
                    <li class="dropdown"><a class="dropbtn" href="CreateApplication">Application</a>
                        <div class="dropdown-content">
                            <a href="CreateApplication">Create Application</a>
                            <a href="ApplicationSearch.jsp">Search</a>
                        </div>
                    </li>
                    <li class="dropdown"><a class="dropbtn" href="Configuration">Configuration</a>
                        <div class="dropdown-content">
                            <a href="ViewRules">Rules</a>
                            <a href="Configuration">Configure System Parameters</a>
                        </div>
                    </li>
                    <%--<li style="float:right;"><a href="login.jsp">Logout</a></li>--%>
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
