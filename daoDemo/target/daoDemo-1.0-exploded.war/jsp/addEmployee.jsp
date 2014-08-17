<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">

<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="abc" uri="/WEB-INF/tags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add Employee</title>
    <link rel='stylesheet' type='text/css' href='stylesheets/demo.css'>
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script type="text/javascript" src="javascript/common.js"></script>

    <script type="text/javascript">
        $(document).ready(function ()
        {
            $('#submitButton').click( go );
        } );

        function go()
        {
            $('form').submit();
        }
    </script>
</head>
<body>
<div class="header">Add Employee</div>

<br/>
<c:choose>
    <c:when test="${result.worked}">
        <div style="color: green;">${result.message}</div>
    </c:when>
    <c:otherwise>
        <div style="color: red;">${result.message}</div>
    </c:otherwise>
</c:choose>

<hr/>

<c:if test="${result.worked}">
    <div style="color: green;">${result.message}</div>
</c:if>
<c:if test="${!result.worked}">
    <div style="color: red;">${result.message}</div>
</c:if>

<form action="addEmployee.spr" method="post">
    <table>
        <tr>
            <td>*</td>
            <td><label for="firstName">First Name:</label></td>
            <td><input id='firstName' name="firstName" type="text" maxlength="50"/></td>
        </tr>
        <tr>
            <td>*</td>
            <td><label for="lastName">Last Name:</label></td>
            <td><input id='lastName' name="lastName" type="text" maxlength="50"/></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>
                <input type="button" name="submitButton" id="submitButton" value="Add Employee"/>
            </td>
        </tr>
    </table>
</form>
<img src="images/logo.jpg">
<br/>
<a href="addEventType.spr">Add Event Type</a>
<br/>
<a href="helloUrl.spr">Hello</a>
<br/>
<a href="/daoDemo-1.0-exploded/newMember.html">Add Member</a>
<br/>
<a href="/daoDemo-1.0-exploded/showMembers.html">Show Members</a>
<br/>
<a href="/daoDemo-1.0-exploded/showEventTypes.action">Event Types</a>
</body>
</html>