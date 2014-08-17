<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">

<%@ page language="java" contentType="text/html" %>
<%@ taglib prefix="abc" uri="/WEB-INF/tags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Add Event Type</title>
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
<div class="header">Add Event Type</div>

<abc:header text="This is from a custom tag." header=""/>
<br/>
<c:choose>
    <c:when test="${result.worked}">
        <div style="color: green;">${result.message}</div>
    </c:when>
    <c:otherwise>
        <div style="color: red;">${result.message}</div>
    </c:otherwise>
</c:choose>


<form:form id='form' modelAttribute='eventType' action="addEventType.spr" method="post">
    <table>
        <tr>
            <td>*</td>
            <td><label for="eventName">Name:</label></td>
            <td><form:input path="eventName" id="eventName" type="text" maxlength="50"/></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>
                <input type="button" name="submitButton" id="submitButton" value="Add Event Type"/>
            </td>
        </tr>
    </table>
</form:form>
<img src="images/logo.jpg">
<br/>
<a href="addEmployee.spr">Add Employee</a>
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