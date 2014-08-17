<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">

<%@ page language="java" contentType="text/html" %>
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
<table border="1" cellpadding="5px" cellspacing="0">
    <h1><c:out value="${bean.employee.name}"/></h1>
    <h3><c:out value="${bean.club.name}"/></h3>
    <h5><c:out value="${bean.securityNotice}"/></h5>
    <hr/>
    <tr><th>ID</th><th>Name</th></tr>
    <c:forEach items="${bean.eventTypes}" var="eventType" >
        <tr>
            <td>
                <c:out value="${eventType.eventTypeId}"/>
            </td>
            <td>
                <c:out value="${eventType.eventName}"/>
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
                <c:forEach items="${bean.clubs}" var="club">
                    <option value="${club.id}">${club.number} ${club.name}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
</table>
<hr/>
<a href="/daoDemo-1.0-exploded/newMember.html">Add Member</a>
<br/>
<a href="/daoDemo-1.0-exploded/showMembers.html">Show Members</a>
<br/>
</body>
</html>