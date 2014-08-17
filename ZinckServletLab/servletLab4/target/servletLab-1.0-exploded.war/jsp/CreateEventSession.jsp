<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Create Event Session</title>
    <link rel='stylesheet' href='stylesheets/main.css'>
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script type='text/javascript' src='javascript/jquery-blockUI-2.35.min.js' ></script>
    <script src='javascript/main.js'></script>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="mine" uri="/WEB-INF/tags.tld" %>
</head>
<script type="text/javascript">
    function createEventSession()
    {
        var form = $('#createEventForm');
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.serializeArray(),
            dataType: 'text',
            timeout: 10000,
            cache: false,
            error: ajaxError,
            success: function (output)
            {
                alert(output + ' event created');
            }});
    }

    function ajaxError(response)
    {
        alert("AJAX Error: status = " + response.status + " " + response.statusText);
    }
</script>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>

    <h2>Create Event Session</h2>

    <form id='createEventForm' action='ajax/createEvent.spr' method='post'>
        <div>
            <h3>Member</h3>

            <h3><mine:out value="${currentMember.name}"/></h3>
        </div>
        <div>
            <h3>Employee</h3>
            <mine:idNameSelect idNames="${employees}" selectName="employee"/>
        </div>
        <div>
            <h3>Club</h3>
            <mine:idNameSelect idNames="${clubs}" selectName="club"/>
        </div>
        <div>
            <h3>Event Type</h3>
            <mine:idNameSelect idNames="${eventTypes}" selectName="eventType"/>
        </div>
        <br/>
        <input type="hidden" name="memberId" value="${currentMember.id}">
    </form>
    <input id='button' type='button' value='Create Event Session' onclick="createEventSession()">
</div>
</body>
</html>