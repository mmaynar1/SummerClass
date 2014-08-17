<%@ page import="com.summerclass.servlet.GameServlet" %>
<%@ page import="com.summerclass.servlet.Keywords2048" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>2048</title>
    <link rel='stylesheet' type='text/css' href='stylesheets/main.css'>
    <link rel='stylesheet' type='text/css' href='stylesheets/game.css'>

    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script>
    <script src='javascript/main.js'></script>
    <script type="text/javascript" src="javascript/event.js"></script>
    <script type="text/javascript" src="javascript/game.js"></script>

    <script type="text/javascript">
        var page = {
            event: new Event(),
            game: new Game(),

            WEST: 37,    // todo move to class
            NORTH: 38,
            EAST: 39,
            SOUTH: 40,
            RESTART: 83,
            ROW_CHANGE: 82,
            COLUMN_CHANGE: 67,
            TWO_LIKELIHOOD: 50,
            NUMBER_GAME: 78,
            LEGO_GAME: 76,

            INPUT: "<%= Keywords2048.input.getText() %>",
            CELL: "<%= Keywords2048.cell.getText() %>",
            ID_DELIMETER: "<%= Keywords2048.delimiter.getText() %>"
        };
        var colIndex;
        var rowIndex;

        function initialize()
        {
            page.game.start( "${score}", ${board} );
        }

        $(document).keydown(function (event)
        {
            page.event.checkKey(event.keyCode);
            return false;
        });

        $(document).ready(function ()
        {
           $("#newGame").click(function ()
           {
              page.game.start();
           });

           $('#saveGame').click( function ()
           {
              page.game.saveGame();
              $('#submitType').val( "<%= Keywords2048.save.getText() %>");
              $('form').submit();
           });

           $('#quitGame').click( function()
           {
              page.game.saveGame();
              $('#submitType').attr("value", "<%= Keywords2048.quit.getText() %>");
              $('#gameState').submit();
           });
        });
    </script>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body onload="initialize();">
<div class="container">
    <jsp:include page="header.jsp"/>

    <div class="positioner">
        <form id="gameState" action="2048.html" method="post">
            <input type="hidden" name="submitType" id="submitType" value=""/>
            <table id="gameContainer" class="gameContainer">
                <tr>
                    <td id="name" class="name">
                        <img src="images/numbers/2048.JPG" alt="2048"/>
                    </td>
                    <td id="scoreName" class="info scoreName">Score:
                        <span id="score" class="info score">0</span>
                        <input type="hidden" name="score" value=""/></td>
                    <td class="name">
                        <input id="newGame" type="button" value="New Game"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div id="gridContainer"></div>
                    </td>
                    <td><input id="saveGame" type="button" value="Save Game"/></td>
                </tr>
                <tr>
                    <td colspan="2" id="instructions" class="info instructions">
                        Instructions <br/>
                        ==================== <br/>
                        Swipe = direction arrows <br/>
                        Start over = "S" <br/>
                    </td>
                    <td><input id="quitGame" type="button" value="Quit Game"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
