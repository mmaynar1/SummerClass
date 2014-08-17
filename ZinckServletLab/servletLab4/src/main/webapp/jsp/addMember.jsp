<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add Member</title>
    <link rel='stylesheet' href='stylesheets/main.css'>
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script src='javascript/main.js'></script>
    <style type="text/css">
        select {
            width: 100%;
        }

        .worked {
            color: blue;
        }

        .failed {
            color: red;
        }
    </style>
</head>

<body>
<div class="container">
    <jsp:include page="header.jsp"/>

    <h2>Add Member</h2>

    <c:if test="${not empty result}">
        <c:if test="${result.worked}">
            <p class="worked">${result.message}</p>
        </c:if>
        <c:if test="${!result.worked}">
            <p class="failed">${result.message}</p>
        </c:if>
    </c:if>

    <form:form id="addMemberForm" modelAttribute="member" action="addMember.spr" method="post">
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
                <td><label for="agreementType">Agreement Type:</label></td>
                <td>
                    <form:select path="agreementTypeId" id="agreementType">
                        <c:forEach var="agreementTypes" items="${agreementTypes}">
                            <form:option value="${agreementTypes.id}">${agreementTypes.name}</form:option>
                        </c:forEach>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td><label for="username">* Username:</label></td>
                <td><form:input path="username" id="username" type="text" maxlength="50"/></td>
            </tr>
            <tr>
                <td><label for="password">* Password:</label></td>
                <td><form:input path="password" id="password" type="text" maxlength="50"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="button" name="submitButton" id="submitButton" value="Add Member"/>
                </td>
            </tr>
        </table>
    </form:form>
</div>

</body>
</html>
