<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>2048</title>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/game2048.js"></script>
    <script type="text/javascript" src="js/sendGameState.js"></script>
    <script>
        var board = new Board();
        var gameState = new GameState();

        $(document).keydown(board, function (eventObject)
        {
            var LEFT_ARROW = 37;
            var UP_ARROW = 38;
            var RIGHT_ARROW = 39;
            var DOWN_ARROW = 40;

            switch (eventObject.which)
            {
                case LEFT_ARROW:
                    board.swipeLeft();
                    break;
                case UP_ARROW:
                    board.swipeUp();
                    break;
                case RIGHT_ARROW:
                    board.swipeRight();
                    break;
                case DOWN_ARROW:
                    board.swipeDown();
                    break;
                default:
                    //Do nothing
                    break;
            }
        });

        function applyHoverStyle(id)
        {
            $('#' + id).css('border', 'black solid 2px').css('cursor', 'pointer');

        }
        function applyLeaveStyle(id)
        {
            $('#' + id).css('border', '2px solid lightgray').css('cursor', 'default').css('background-color', 'floralwhite');
        }
        function applyMouseDownStyle(id)
        {
            $('#' + id).css('border', '2px solid lightgray').css('cursor', 'default').css('background-color', 'lightsalmon');
        }
        function refresh()
        {
            window.location.reload();
        }
    </script>

    <link rel="stylesheet" type="text/css" href="css/game2048.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body onload="board.initialize('${gameState.boardSize}','${gameState.score}','${gameState.board}');">
<%@ include file="banner.jsp" %>
<div class="container">
    <div class="title">2048</div>
    <div class="scoreBox">
        <div class="score">0</div>
    </div>
    <div class="gameOver"></div>
    <table class="board">
        <tr id="firstRow">
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
        </tr>
        <tr id="secondRow">
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
        </tr>
        <tr id="thirdRow">
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
        </tr>
        <tr id="fourthRow">
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
        </tr>
    </table>
    <div class="arrows">
        <table>
            <tr>
                <td colspan="2" align="center"><input type="button" value="&#8593;" onclick="board.swipeUp()"></td>
            </tr>
            <tr>
                <td><input type="button" value="&#8592;" onclick="board.swipeLeft()"></td>
                <td><input type="button" value="&#8594;" onclick="board.swipeRight()"></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="button" value="&#8595;" onclick="board.swipeDown()"></td>
            </tr>
        </table>
    </div>
    <div class="gameButtons">
    <div id="newGame"
         onmouseover="applyHoverStyle(this.id)"
         onmouseout="applyLeaveStyle(this.id)"
         onclick="refresh()"
         onmousedown="applyMouseDownStyle(this.id)">New Game
    </div>
    <div id="undo"
             onmouseover="applyHoverStyle(this.id)"
             onmouseout="applyLeaveStyle(this.id)"
             onclick="board.undo()"
             onmousedown="applyMouseDownStyle(this.id)">Undo
    </div>
    </div>
    <p class="directions">
        Use the buttons or the arrow keys to slide tiles.<br> Adjacent tiles will combine.<br> Can you get 2048?
    </p>

    <div style="clear: both;"></div>
</div>
<form id="gameStateForm" name="gameStateForm" action="gameSaved.html" method="post">
    <input type="hidden" id="boardSize" name="boardSize">
    <input type="hidden" id="gameScore" name="gameScore">
    <input type="hidden" id="boardAsString" name="boardAsString">
    <input type="button" value="Save Game and Quit" id="saveGameButton" onclick="gameState.submitGameState(board.getBoardSize(), board.getScore(), board.getBoardAsString())">
</form>
</body>
</html>
