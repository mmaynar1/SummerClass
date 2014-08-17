<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">

<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="abc" uri="/WEB-INF/tags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Event Types</title>
    <link rel='stylesheet' type='text/css' href='stylesheets/demo.css'>
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script type="text/javascript" src="javascript/common.js"></script>
</head>
<body>
<div class="header">Event Types</div>
<img src="images/logo.jpg">
<br/>
<hr/>
Custom Tag Output: <abc:header header="dfsdf" text="something goes here"/><br/>
<hr/>
<abc:orderedList caption="Clubs go here" clubs="${model.clubs}"/>
<hr/>
application scope: ${applicationScope.pepsi} ${pepsi}
<br/>
session scope: ${sessionScope.drpepper} ${drpepper}
<br/>
request scope: ${requestScope.coke} ${coke}
<br/>
<table border="1" cellpadding="5px" cellspacing="0">
    <h1><c:out value="${model.employee.name}"/></h1>
    <h3><c:out value="${model.club.name}"/></h3>
    <h5><c:out value="${model.securityNotice}"/></h5>
    <hr/>
    <tr><th>Name</th></tr>
    <c:forEach items="${model.eventTypes}" var="bob" >
        <tr>
            <td id='<c:out value="${bob.eventTypeId}"/>'>
                <c:out value="${bob.eventName}"/>
            </td>
        </tr>
    </c:forEach>
</table>
<div style="margin-top: 25px;">
    Random drop down combo for demonstration
</div>
<table>
    <tr>
        <td>
            <label for="club">Club:</label>
        </td>
        <td>
            <select name="club" id="club">
                <c:forEach items="${model.clubs}" var="club">
                    <option value="${club.id}">${club.number} ${club.name}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
</table>
<jsp:include page="menu.jsp"/>
</body>
</html>