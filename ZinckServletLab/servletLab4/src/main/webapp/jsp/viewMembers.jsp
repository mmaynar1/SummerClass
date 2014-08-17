<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>View Members</title>
    <link rel='stylesheet' href='stylesheets/main.css'>

    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <script type="text/javascript" src="javascript/jquery-migrate-1.2.1.js"></script>
    <script type='text/javascript' src='javascript/jquery-blockUI-2.35.min.js'></script>
    <script src='javascript/main.js'></script>
    <script src='javascript/editMember.js'></script>
    <script src="javascript/fakemodal.js"></script>

    <script src='dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommon.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgridcell.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid_export.js' type='text/javascript'></script>

    <script src='dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_filter.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_mcol.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_ssc.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_start.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_splt.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js' type='text/javascript'></script>

    <script src='dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_link.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_tree.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_3but.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_acheck.js' type='text/javascript'></script>
    <%--<script src='dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_calendar.js' type='text/javascript'></script>--%>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_cntr.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_context.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_dhxcalendar.js' type='text/javascript'></script>
    <script src='dhtmlxSuite/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_grid.js' type='text/javascript'></script>

    <script type='text/javascript' src='dwr/engine.js'></script>
    <script type='text/javascript' src='dwr/interface/MemberRpc.js'></script>

    <link rel="STYLESHEET" type="text/css" href="dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.css">
    <style type="text/css">
        select
        {
            width: 100%;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function ()
        {
            loadManageMembersGrid();
        });
        function getCount()
        {
            var loggedInMemberId = '${loginMember.memberId}';
            $.ajax({ type: 'POST',
                url: 'ajax/memberEventsCount.spr',
                data: {memberId: loggedInMemberId},
                dataType: 'text',
                timeout: 10000,
                cache: false,
                error: ajaxError,
                success: function (count)
                {
                    $('#count').html(count);
                }});
        }

        function viewPendingEvents(memberId)
        {
            MemberRpc.getPendingEvents(memberId, function (events)
            {
                var pendingEventsGrid = "<div id='pendingEventsGrid' style='width: 500px; height: 500px; border: 1px solid gray;'></div>";
                if (events.length == 0)
                {
                    pendingEventsGrid = "No pending events";
                    showDialog("Pending Events", function ()
                                   {
                                   }, function ()
                                   {
                                   }, pendingEventsGrid);
                }
                else
                {
                    showDialog("Pending Events", function ()
                                   {
                                   }, function ()
                                   {
                                   }, pendingEventsGrid);
                                   loadPendingEventsGrid(events)


                }
            });
        }

        function loadPendingEventsGrid(events)
         {
             var grid = new dhtmlXGridObject('pendingEventsGrid');

             grid.setHeader('Club,Event,Start Time');
             grid.setColSorting( 'str,str,str' );
             grid.setInitWidths( "150,150,150" );
             grid.setImagePath( "/dhtmlxSuite/dhtmlxGrid/codebase/imgs/" );
             grid.init();
             var values = [];
             var i;


             $.each(events, function (index, event)
             {
                 values.push([ event.club.name , event.eventType.name , event.startDateTime ]);
             });


             grid.parse(values, "jsarray");
         }

        function loadManageMembersGrid()
        {
            var grid = new dhtmlXGridObject('manageMembersGrid');

            grid.setHeader('First Name,Last Name,Agreement Type,Username,Edit,View Pending Events');
            grid.init();
            grid.setColSorting( 'str,str,str,str' );
            grid.setImagePath( "/dhtmlxSuite/dhtmlxGrid/codebase/imgs/" );
            var rows = getMemberRows();
            var event;
            var values = [];
            var i;

            for (i = 0; i < rows.length; ++i)
            {
                event = rows[i];
                values.push([ event[0], event[1], event[2], event[3], event[4], event[5]]);
            }

            grid.parse(values, "jsarray");
        }

        function getMemberRows()
        {
            var memberRows = [];
            <c:forEach items="${members}" var="member">
            var editLink = "<a href=\"editMember.spr?memberId=${member.memberId}\">Edit</a>";
            var viewLink = "<a href='#' onclick=\"viewPendingEvents('${member.memberId}')\">View Pending Events</a>";
            memberRows.push(['${member.firstName}',
                '${member.lastName}',
                '${member.agreementType}',
                '${member.username}',
                editLink,
                viewLink]);
            </c:forEach>
            return memberRows;
        }
    </script>
</head>
<body>

<div class="container">
    <jsp:include page="header.jsp"/>

    <h2>View Members</h2>
    <table>
        <tr>
            <td><img src="images/theCount.jpg" width="100px" onclick="getCount()" height="50px" alt="count"></td>
            <td id="count"></td>
        </tr>
    </table>
    <span class="twoSpan">
        <form:form id="filterMemberForm" modelAttribute="member" action="viewMembers.spr" method="post">
            <table>
                <tr>
                    <td><label for="firstName">First Name:</label></td>
                    <td><form:input path="firstName" id="firstName" type="text" maxlength="50"/></td>
                </tr>
                <tr>
                    <td><label for="lastName">Last Name:</label></td>
                    <td><form:input path="lastName" id="lastName" type="text" maxlength="50"/></td>
                </tr>
                <tr>
                    <td><label for="agreementType">Agreement Type:</label></td>
                    <td>
                        <form:select path="agreementTypeId" id="agreementType">
                            <form:option value=""></form:option>
                            <c:forEach var="agreementTypes" items="${agreementTypes}">
                                <form:option value="${agreementTypes.id}">${agreementTypes.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="button" name="filterMember" id="filterMember" value="Filter Member"/>
                    </td>
                </tr>
            </table>
        </form:form>
    </span>
    <span class="twoSpan">
        <div id="manageMembersGrid" style="width: 900px; height: 400px; border: 1px solid gray;"></div>
    </span>

</div>

</body>
</html>
