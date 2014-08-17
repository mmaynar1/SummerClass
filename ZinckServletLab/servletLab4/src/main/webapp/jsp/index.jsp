<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Main Menu</title>
    <link rel='stylesheet' type='text/css' href='stylesheets/main.css'>
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script type="text/javascript" src="javascript/main.js"></script>
    <script type="text/javascript">
        $(document).ready(function ()
        {
            $("#button").click(function ()
            {
                document.location.href = '/servletLab-1.0-exploded/createEventSession.html'
            });
        });
    </script>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h2>Main Menu</h2>
    <h4>Welcome, ${loginMember.firstName}</h4>
</div>
</body>
</html>