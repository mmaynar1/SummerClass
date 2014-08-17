<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>View Employees</title>
    <link rel='stylesheet' href='stylesheets/main.css'>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css" />
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
   <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <script type="text/javascript" src="javascript/jquery-migrate-1.2.1.js"></script>
    <script type='text/javascript' src='javascript/jquery-blockUI-2.35.min.js' ></script>

    <script src='javascript/main.js'></script>
    <script src='javascript/editEmployee.js'></script>
    <script src="javascript/fakemodal.js"></script>
    <style type="text/css">
        select
        {
            width: 100%;
        }

        .worked
        {
            color: lime;
        }

        .failed
        {
            color: red;
        }
    </style>
    <script type="text/javascript">

        function launchDeleteEventsDialogue(employeeId)
        {
            showDialog("Confirm",
            function (){deleteEvents(employeeId)},
            function(){},
             "<p>You are about to delete all<br/>of this employee's events. Proceed?</p>"
            );
/*            if (confirm("You are about to delete all of this employee's events. Proceed?") == true)
            {
                deleteEvents(employeeId);
            }
            else
            {
                //Do nothing
            }*/
        }
        function deleteEvents(employeeId)
        {
            $.ajax({ type: 'POST',
                url: 'ajax/deleteEmployeeEvents.spr',
                data: { employeeId: employeeId },
                dataType: 'text',
                timeout: 10000,
                cache: false,
                error: ajaxError,
                success: function (output)
                {
                    $('#' + employeeId).html(output + ' events deleted');
                }});
        }

        function ajaxError(response)
        {
            alert("AJAX Error: status = " + response.status + " " + response.statusText);
        }
    </script>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>

    <h2>View Employees</h2>

    <c:forEach items="${results}" var="result">
        <c:if test="${result.worked}">
            <p class="worked">${result.message}</p>
        </c:if>
        <c:if test="${!result.worked}">
            <p class="failed">${result.message}</p>
        </c:if>
    </c:forEach>

    <span class="twoSpan">
        <form:form id="filterEmployeeForm" modelAttribute="employee" action="viewEmployees.spr" method="post">
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
                    <td><label for="barcode">Barcode:</label></td>
                    <td><form:input path="barcode" id="barcode" type="text" maxlength="50"/></td>
                </tr>
                <tr>
                    <td><label for="address">* Address:</label></td>
                    <td><form:input path="address" id="address" type="text" maxlength="50"/></td>
                </tr>
                <tr>
                    <td><label for="city">* City:</label></td>
                    <td><form:input path="city" id="city" type="text" maxlength="50"/></td>
                </tr>
                <tr>
                    <td><label for="state">* State:</label></td>
                    <td><form:input path="state" id="state" type="text" maxlength="2"/></td>
                </tr>
                <tr>
                    <td><label for="zipCode">* Zip Code:</label></td>
                    <td><form:input path="zipCode" id="zipCode" type="text" maxlength="5"/></td>
                </tr>
                <tr>
                    <td><label for="hourlyWage">* Hourly Wage:</label></td>
                    <td><form:input path="hourlyWage" id="hourlyWage" type="text" maxlength="10"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="button" name="filterEmployee" id="filterEmployee" value="Filter Employees"/>
                    </td>
                </tr>
            </table>
        </form:form>
    </span>
    <span class="twoSpan">
        <div style="height: 600px; overflow: auto">
            <table>
                <thead>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Barcode</th>
                    <th>Address</th>
                    <th>City</th>
                    <th>State</th>
                    <th>Zip Code</th>
                    <th>Hourly Wage</th>
                </tr>
                </thead>
                <c:forEach var="employee" items="${employees}">
                    <tr>
                        <td>${employee.firstName}</td>
                        <td>${employee.lastName}</td>
                        <td>${employee.barcode}</td>
                        <td>${employee.address}</td>
                        <td>${employee.city}</td>
                        <td>${employee.state}</td>
                        <td>${employee.zipCode}</td>
                        <td>${employee.hourlyWage}</td>
                        <td><a href="editEmployee.spr?employeeId=${employee.employeeId}">Edit</a></td>
                        <td><input type="button" onclick="launchDeleteEventsDialogue('${employee.employeeId}')" value="Delete Events">
                        </td>
                        <td id="${employee.employeeId}"></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </span>
</div>

</body>
</html>

