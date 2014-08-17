<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add Employee</title>
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
            color: blue;
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

    <h2>Add Employee</h2>


    <form:form id="addEmployee2" modelAttribute="employee" action="addEmployee2.spr" method="post">
        <table>
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
                <td><label for="zipcode">* Zip Code:</label></td>
                <td><form:input path="zipCode" id="zipcode" type="text" maxlength="5"/></td>
            </tr>

            <tr>
                <td>
                    <input type="button" name="addEmployee2ButtonPrevious" id="addEmployee2ButtonPrevious" value="Previous"/>
                </td>
                <td>
                    <input type="button" name="addEmployee2ButtonNext" id="addEmployee2ButtonNext" value="Next"/>
                </td>
            </tr>
        </table>
        <input type="hidden" name="previousPage" id="previousPage" value="false">
    </form:form>

</div>

</body>
</html>
