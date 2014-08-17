<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type="text/javascript">
        function login()
        {
            document.getElementById('login').submit();
        }
    </script>
</head>
<body>
<%@ include file="logo.jsp" %>
<fieldset>
    <form id="login" action='MainMenu.html' method="post">
        <table>
            <tr>
                <td>Username:</td>
                <td ><input type="text" name="username" maxlength="25"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td ><input type="password" name="password" maxlength="25"></td>
            </tr>
        </table>
        <input type="button" value="Login" onclick="login()">
    </form>

</fieldset>
</body>
</html>
