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

    <h2>Add Employee</h2>

    <c:forEach items="${results}" var="result">
            <c:if test="${result.worked}">
                <p class="worked">${result.message}</p>
            </c:if>
            <c:if test="${!result.worked}">
                <p class="failed">${result.message}</p>
            </c:if>
        </c:forEach>

    <form:form id="addEmployee3" modelAttribute="employee" action="addEmployee3.spr" method="post">
        <table>
            <tr>
                <td><label for="hourlyWage">* Hourly Wage:</label></td>
                <td><form:input path="hourlyWage" id="hourlyWage" type="text" maxlength="50"/></td>
            </tr>
            <tr>
                <td>
                    <input type="button" name="addEmployee3ButtonPrevious" id="addEmployee3ButtonPrevious" value="Previous"/>
                </td>
                <td>
                    <input type="button" name="addEmployee3ButtonFinish" id="addEmployee3ButtonFinish" value="Finish"/>
                </td>
            </tr>
        </table>
        <input type="hidden" id="previousPage2" name="previousPage2" value="false">
    </form:form>

</div>

</body>
</html>
