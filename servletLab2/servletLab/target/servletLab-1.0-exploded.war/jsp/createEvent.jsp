<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mine" uri="/WEB-INF/tags.tld" %>
<html>
<head>
    <title>Create Event</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script type='text/javascript'>
        function submitCreateEvent()
        {
            document.getElementById("createEvent").submit();
        }
    </script>
</head>
<body>
<%@ include file="banner.jsp" %>
<h1>Create Event</h1>

<h2><mine:output text="${bean.memberName}"/></h2>
<fieldset>
    <legend>Create Event Session</legend>
    <form id="createEvent" action="CreateEventResult.html" method="post">
        <div class="input">
            <table>
                <tr>
                    <td>
                        Employee:
                    </td>
                    <td>
                        <mine:comboBox idNames="${bean.employees}" selectIdName="employeeName"></mine:comboBox>
                    </td>
                </tr>

                <tr>
                    <td>
                        Club:
                    </td>
                    <td>
                        <mine:comboBox idNames="${bean.clubs}" selectIdName="clubName"></mine:comboBox>
                    </td>
                </tr>

                <tr>
                    <td>
                        Event Type:
                    </td>
                    <td>
                        <mine:comboBox idNames="${bean.eventTypes}" selectIdName="eventTypeName"></mine:comboBox>
                    </td>
                </tr>
            </table>
            <input type="hidden" name="uri" id="uri" value="${bean.uri}">
        </div>
        <div class="createEventButton">
            <input type="button" id="createButton" value="Create Event" onclick="submitCreateEvent()">
        </div>
        <br/>
    </form>
</fieldset>
<a href="MainMenu.html">Main Menu</a>
</body>
</html>
