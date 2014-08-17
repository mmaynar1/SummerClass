<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Login</title>
    <link rel='stylesheet' type='text/css' href='stylesheets/main.css'>
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script type='text/javascript' src='javascript/main.js'></script>
</head>
<body>
<div class="container">
<jsp:include page="header.jsp" />
<h2>Login</h2>
<form id='form' action='/servletLab-1.0-exploded/login' method='post'>
    <table>
        <tr>
            <td>Username: </td>
            <td><input type='text' id='username' name="username" value="Leon" maxlength="52"></td>
        </tr>
        <tr>
            <td>Password: </td>
            <td><input type='password' id='password' name="password" value="password" maxlength="52"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input id='loginButton' type='button' value='Login'>
            </td>
        </tr>
    </table>
</form>
</div>
</body>
</html>
