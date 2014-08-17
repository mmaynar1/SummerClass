<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Frameset//EN' 'http://www.w3.org/TR/html4/frameset.dtd'>
<html>
<head>
    <title>Event Sesions</title>
    <link rel='stylesheet' type='text/css' href='stylesheets/main.css'>
    <link rel="stylesheet" type="text/css" href="stylesheets/jquery.ui.timepicker.css">
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css"/>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <script type="text/javascript" src="javascript/jquery-migrate-1.2.1.js"></script>
    <script type='text/javascript' src='javascript/jquery-blockUI-2.35.min.js'></script>
    <script src='javascript/main.js'></script>
    <script src='javascript/fakemodal.js'></script>
    <script type="text/javascript" src="javascript/jquery.ui.timepicker.js"></script>
    <script type='text/javascript' src='dwr/engine.js'></script>
    <script type='text/javascript' src='dwr/interface/EventRpc.js'></script>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script type="text/javascript">
        function editEvent(eventId, employeeId, date, time)
        {
            EventRpc.editEvent(eventId, employeeId, date, time, function (message)
            {
                showDialog("Edit Event", function(){$('#filterButton').trigger('click');},function(){$('#filterButton').trigger('click');},message);

            });
        }

        function launchEditDialog(eventId,employeeId)
        {
            showDialog("Edit Event",
                    function(){editEvent(eventId, $('#employees').val(), $('#date').val(), $("#time").val())},
                    function ()
                    {
                    }, "<div id='editEventContents'>" +
                            "<table>" +
                            "<tr>" +
                            "<td>Employee</td>" +
                            "<td><select id='employees'></select></td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Date</td>" +
                            "<td><input id='date' name='date' type='text' value='' maxlength='10'/></td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>Time</td>" +
                            "<td><input id='time' name='time' type='text' value='' maxlength='10'/></td>" +
                            "</tr>" +
                            "</table>" +
                            "</div>"
            );
            getEmployees(employeeId);
            $('#date').datepicker();
            $('#time').timepicker({
                showPeriod: true,
                showLeadingZero: true,
                minutes: {
                    starts: 0,                // First displayed minute
                    ends: 45,                 // Last displayed minute
                    interval: 15,              // Interval of displayed minutes
                    manual: []                // Optional extra entries for minutes
                }
            });
        }

        function getEmployees(employeeId)
        {
            $.ajax({ type: 'POST',
                url: 'ajax/getAllEmployees.spr',
                dataType: 'json',
                timeout: 10000,
                cache: false,
                error: ajaxError,
                success: function (employees)
                {
                    var toAppend = '';
                    for (var i = 0; i < employees.length; i++)
                    {
                        if(employees[i]['employeeId'] !== employeeId)
                        {
                            toAppend += '<option value=' + employees[i]['employeeId'] + '>' + employees[i]['firstName'] + ' ' + employees[i]['lastName'] + '</option>';
                        }
                        else
                        {
                            toAppend += '<option selected value=' + employees[i]['employeeId'] + '>' + employees[i]['firstName'] + ' ' + employees[i]['lastName'] + '</option>';
                        }
                    }

                    $('#employees').append(toAppend);
                }});
        }
    </script>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h2 id='successMessage'>${successMessage}</h2>

    <h2>Event Sessions</h2>
    <br/>

   <span class="twoSpan">
        <form id='filterForm' action='EventSessions.html' method='post'>
            <table id='filters'>
                <tr>
                    <th colspan='2'>Filters</th>
                </tr>
                <tr>
                    <td>Member:</td>
                    <td>
                        <input type="text" id="memberFilter" name="memberFilter" value="${defaultMember}"
                               maxlength="52">
                    </td>
                </tr>
                <tr>
                    <td>Employee:</td>
                    <td>
                        <input type="text" id="employeeFilter" name="employeeFilter" maxlength="52">
                    </td>
                </tr>
                <tr>
                    <td>Club:</td>
                    <td>
                        <input type="text" id="clubFilter" name="clubFilter" maxlength="52">
                    </td>
                </tr>
                <tr>
                    <td>Event Type:</td>
                    <td>
                        <input type="text" id="eventTypeFilter" name="eventTypeFilter" maxlength="52">
                    </td>
                </tr>
                <tr>
                    <td>Status</td>
                    <td>
                        <input type="text" id="statusFilter" name="statusFilter" maxlength="52">
                    </td>
                </tr>
                <tr>
                    <th colspan='2'><input id='filterButton' type='button' value='Filter Results'></th>
                </tr>
            </table>
        </form>
   </span>

    <span class="twoSpan">
        <div style="height: 600px; overflow: auto">
            <table id='eventSessions'>
                <tr>
                    <th>Member</th>
                    <th>Employee</th>
                    <th>Club</th>
                    <th>Event</th>
                    <th>Start Time</th>
                    <th>Status</th>
                </tr>
                <c:forEach items='${events}' var='event'>
                    <tr>
                        <td>${event.member.name}</td>
                        <td>${event.employee.name}</td>
                        <td>${event.club.name}</td>
                        <td>${event.eventType.name}</td>
                        <td>${event.startDateTime}</td>
                        <td>${event.status.name}</td>
                        <td><a href="#" onclick="launchEditDialog('${event.eventId}','${event.employee.id}')">Edit</a></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="6">
                        <form class='navigation' action='/servletLab-1.0-exploded/createEventSession.html'
                              method='get'>
                            <input class='createEventSessionNavigationButton' type='button'
                                   value='Add Event Session'>
                        </form>
                    </td>
                </tr>
            </table>
        </div>
      </span>
</div>
</body>
</html>