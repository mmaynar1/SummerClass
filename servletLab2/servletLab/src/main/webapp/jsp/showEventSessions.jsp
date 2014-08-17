<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Show Events</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script type="text/javascript">
        $('document').ready(function()
        {
            $('#searchButton').bind('click',function()
            {
                $('#search').submit();
            });
        });
    </script>
</head>
<body>
<%@ include file="banner.jsp" %>

<h2><c:out value="${bean.eventCreatedStatus.message}"/></h2>
<fieldset>
    <legend>Filter Search</legend>
<form id="search" action='CreateEventResult.html' method="post">
    <table>
        <tr>
            <td>Member:</td>
            <td><input type="text" name="memberName" value="${firstName} ${lastName}"></td>
        </tr>
        <tr>
            <td>Club:</td>
            <td><input type="text" name="clubName" value="${bean.filters.club}"></td>
        </tr>
        <tr>
            <td>Employee:</td>
            <td><input type="text" name="employeeName" value="${bean.filters.employeeName}"></td>
        </tr>
        <tr>
            <td>Event Type:</td>
            <td><input type="text" name="eventTypeName" value="${bean.filters.eventType}"></td>
        </tr>
        <tr>
            <td>Status:</td>
            <td><input type="text" name="statusName" value="${bean.filters.status}"></td>
        </tr>
    </table>

    <input type="button" id="searchButton" value="Search">
</form>
</fieldset>

<table border="1">
    <tr>
        <td><strong>Club</strong></td>
        <td><strong>Event Type Name</strong></td>
        <td><strong>Member</strong></td>
        <td><strong>Employee</strong></td>
        <td><strong>Status</strong></td>
        <td><strong>Date/Time</strong></td>
    </tr>
    <c:forEach items="${bean.eventSessions}" var="eventSession">
        <tr>
            <td><c:out value="${eventSession.club.name}"/></td>
            <td><c:out value="${eventSession.eventType.name}"/></td>
            <td><c:out value="${eventSession.member.name}"/></td>
            <td><c:out value="${eventSession.employee.name}"/></td>
            <td><c:out value="${eventSession.status.name}"/></td>
            <td><c:out value="${eventSession.startDateTime}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
