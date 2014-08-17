<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit Employee</title>
    <link rel='stylesheet' href='stylesheets/main.css'>
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script src='javascript/main.js'></script>
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
</head>

<body>
<div class="container">
    <jsp:include page="header.jsp"/>

    <h2>Edit Employee</h2>

    <c:if test="${result.worked}">
        <p class="worked">${result.message}</p>
    </c:if>
    <c:if test="${!result.worked}">
        <p class="failed">${result.message}</p>
    </c:if>


    <form:form id="editEmployeeForm" modelAttribute="employee" action="editEmployee.spr" method="post">
        <form:input path="employeeId" type="hidden"/>
        <table>
            <tr>
                <td><label for="firstName">* First Name:</label></td>
                <td><form:input path="firstName" id="firstName" type="text" maxlength="50"/></td>
            </tr>
            <tr>
                <td><label for="lastName">* Last Name:</label></td>
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
                    <input type="button" name="editEmployeeButton" id="editEmployeeButton" value="Edit Employee"/>
                </td>
            </tr>
        </table>
    </form:form>
</div>

</body>
</html>
