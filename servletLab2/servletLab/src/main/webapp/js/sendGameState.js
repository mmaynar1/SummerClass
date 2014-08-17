function GameState()
{
   this.submitGameState = function(boardSize, gameScore, boardAsString)
   {
      $('#boardSize').val(boardSize);
      $('#gameScore').val(gameScore);
      $('#boardAsString').val(boardAsString);
      $('#gameStateForm').submit();
   };

}
