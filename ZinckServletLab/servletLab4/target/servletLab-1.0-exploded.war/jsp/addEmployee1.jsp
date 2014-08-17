<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add Employee</title>
    <link rel='stylesheet' href='stylesheets/main.css'>
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script type='text/javascript' src='javascript/jquery-blockUI-2.35.min.js' ></script>
    <script src='javascript/main.js'></script>
    <style type="text/css">
        select
        {
            width: 100%;
        }

        .worked
        {
            color: blue;
        }

        .failed
        {
            color: red;
        }
    </style>
    <script type="text/javascript">
        function showEmployees()
        {
           var firstName = $('#firstName').val();
            $.ajax({ type: 'POST',
                url: 'ajax/getEmployees.spr',
                data: { employeeFirstName: firstName },
                dataType: 'json',
                timeout: 10000,
                cache: false,
                error: ajaxError,
                success: function (employees)
                {
                    $('#employeeTableContainer').html('');

                    var table = $('<table style="width: 100%;" border="1" cellpadding="5" cellspacng="0"/>');
                    var i;
                    var employee;

                    for (i = 0; i < employees.length; ++i)
                    {
                        employee = employees[i];
                        table.append('<tr><td>' + employee.firstName + '</td><td>' + employee.lastName + '</td><td>' + employee.address + '</td></tr>');
                    }

                    $('#employeeTableContainer').append(table).fadeIn(1500);
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

    <h2>Add Employee</h2>

    <form:form id="addEmployee1" modelAttribute="employee" action="addEmployee1.spr" method="post">
        <table>
            <tr>
                <td><label for="firstName">* First Name:</label></td>
                <td><form:input path="firstName" id="firstName" type="text" maxlength="50"/></td>
                <td><input type="button" value="Check Availability" onclick="showEmployees()"></td>
            </tr>
            <tr>
                <td><label for="lastName">* Last Name:</label></td>
                <td><form:input path="lastName" id="lastName" type="text" maxlength="50"/></td>
            </tr>
            <tr>
                <td><label for="barcode">Barcode:</label></td>
                <td><form:input path="barcode" id="barcode" type="text" maxlength="25"/></td>
            </tr>

            <tr>
                <td colspan="2">
                    <input type="button" name="addEmployee1Button" id="addEmployee1Button" value="Next"/>
                </td>
            </tr>
        </table>
    </form:form>

    <div id="employeeTableContainer"
         style="display: none; border: 1px solid darkgrey; width: 300px; height: 150px; overflow: auto;"></div>
</div>

</body>
</html>
