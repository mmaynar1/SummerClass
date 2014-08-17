function Board()
{
   //todo use images
   //todo high score
   //todo undo button

   var SIZE = 4;
   var score = 0;

   var history = [];

   var board = [
      [0, 0, 0, 0],
      [0, 0, 0, 0],
      [0, 0, 0, 0],
      [0, 0, 0, 0]
   ];

   this.initialize = function (boardSize, gameScore, boardAsString)
   {
      if (boardSize !== "" && gameScore !== "" && boardAsString !== "" )
      {
         this.getSavedGame(boardSize, gameScore, boardAsString);
      }
      else
      {
         addRandomBlock();
         addRandomBlock();
         this.display();
      }
   };


   function getRandomOpenSpace()
   {
      var row = getRandomNumber();
      var column = getRandomNumber();

      while (board[row][column] !== 0)
      {
         row = getRandomNumber();
         column = getRandomNumber();
      }

      return [row, column];
   }

   this.getBoardAsString = function ()
   {
      var delimiter = ",";
      var boardAsString = "";
      for (var row = 0; row < SIZE; ++row)
      {
         for (var column = 0; column < SIZE; ++column)
         {
            boardAsString += (board[row][column] + delimiter);
         }
      }

      return boardAsString;
   };

   this.getBoardFromString = function (boardAsString)
   {
      //todo make a board instead of an array
      var delimiter = ",";
      var singleArray = boardAsString.split(delimiter);
      var row1 = [];
      var row2 = [];
      var row3 = [];
      var row4 = [];

      for (var i = 0; i < (SIZE * SIZE); ++i)
      {
         singleArray[i] = parseInt(singleArray[i], 10);
      }

      for (var count = 0; count < (SIZE * SIZE); ++count)
      {
         if (count < SIZE)
         {
            row1.push(singleArray[count]);
         }
         else if (count < SIZE * 2)
         {
            row2.push(singleArray[count]);
         }
         else if (count < SIZE * 3)
         {
            row3.push(singleArray[count]);
         }
         else if (count < SIZE * 4)
         {
            row4.push(singleArray[count]);
         }
      }

      //todo for some reason this method does not seem to be recognized as the same as board
      var newBoard = [
         [row1],
         [row2],
         [row3],
         [row4]
      ];

      return newBoard;
   };

   this.getScore = function ()
   {
      return score;
   };

   this.getBoardSize = function ()
   {
      return SIZE;
   };

   function getRandomBlock()
   {
      var percentage = Math.floor((Math.random() * 100) + 1);
      var blockNumber;
      if (percentage <= 65)
      {
         blockNumber = 2;
      }
      else
      {
         blockNumber = 4;
      }
      return blockNumber;
   }

   this.getSavedGame = function (boardSize, gameScore, boardAsString)
   {
      if (boardSize !== null && gameScore !== null && boardAsString !== null)
      {

         board = this.getBoardFromString(boardAsString);
         score = gameScore;
         $('.score').html(score);
         this.display();
      }
      else
      {
         //do nothing and create new game
      }
   };

   function getRandomNumber()
   {
      return Math.floor(Math.random() * SIZE);
   }

   this.display = function ()
   {
      var rowNumber = 0;
      var block = $('.board td:eq(' + rowNumber + ')');

      for (var row = 0; row < SIZE; ++row)
      {
         for (var column = 0; column < SIZE; ++column)
         {
            //todo why doesn't the board from session work when trying to use row/column indexes???
            var number = board[row][column];
            block.html(number);
            applyColor(block, number);
            block = block.next();

         }
         rowNumber += SIZE;
         block = $('.board td:eq(' + rowNumber + ')');
      }
   };


   function applyColor(block, number)
   {
      switch (number)
      {
         case 0:
            block.css("background-color", "lightgray");
            block.css("color", "lightgray");
            break;
         case 2:
            block.css("background-color", "aliceblue");
            block.css("color", "black");
            break;
         case 4:
            block.css("background-color", "antiquewhite");
            block.css("color", "black");
            break;
         case 8:
            block.css("background-color", "lightsalmon");
            block.css("color", "white");
            break;
         case 16:
            block.css("background-color", "darkorange");
            block.css("color", "white");
            break;
         case 32:
            block.css("background-color", "tomato");
            block.css("color", "white");
            break;
         case 64:
            block.css("background-color", "orangered");
            block.css("color", "white");
            break;
         case 128:
            block.css("background-color", "yellow");
            block.css("color", "white");
            break;
         case 256:
            block.css("background-color", "yellow");
            block.css("color", "white");
            break;
         case 512:
            block.css("background-color", "yellow");
            block.css("color", "white");
            break;
         case 1024:
            block.css("background-color", "yellow");
            block.css("color", "white");
            break;
         case 2048:
            block.css("background-color", "yellow");
            block.css("color", "white");
            break;

      }

   }

   function getRotatedBoardRight(board, size)
   {
      var rotatedBoard = [
         [0, 0, 0, 0],
         [0, 0, 0, 0],
         [0, 0, 0, 0],
         [0, 0, 0, 0]
      ];

      for (var i = 0; i < size; ++i)
      {
         for (var j = 0; j < size; ++j)
         {
            rotatedBoard[i][ j] = board[size - j - 1][ i];
         }
      }

      return rotatedBoard;
   }

   function shiftLeft()
   {
      for (var row = 0; row < SIZE; ++row)
      {
         var count = 0;

         for (var column = 0; column < SIZE; ++column)
         {
            if (board[row][column] != 0)
            {
               board[row][count] = board[row][column];
               ++count;
            }
         }
         while (count < SIZE)
         {
            board[row][count] = 0;
            ++count;
         }
      }
   }


   function combineLeft()
   {
      for (var row = 0; row < SIZE; ++row)
      {
         for (var column = 0; column < SIZE; ++column)
         {
            if (board[row][column] !== 0 && board[row][column] === board[row][column + 1])
            {
               var points = board[row][column] * 2;
               board[row][column] = points;
               board[row][column + 1] = 0;
               score += points;
               $('.score').html(score);
               shiftLeft();
            }
            if (board[row][column] === 0)
            {
               break;
            }
         }
      }
   }

   function addRandomBlock()
   {
      var space = getRandomOpenSpace();
      board[space[0]][space[1]] = getRandomBlock();

   }

   function getCopy(board)
   {
      var boardCopy = [
         [0, 0, 0, 0],
         [0, 0, 0, 0],
         [0, 0, 0, 0],
         [0, 0, 0, 0]
      ];
      for (var row = 0; row < SIZE; ++row)
      {
         for (var column = 0; column < SIZE; ++column)
         {
            boardCopy[row][column] = board[row][column];
         }
      }

      return boardCopy;
   }

   function areEqual(board1, board2)
   {
      var equal = true;
      for (var row = 0; row < SIZE; ++row)
      {
         for (var column = 0; column < SIZE; ++column)
         {
            if (board1[row][column] !== board2[row][column])
            {
               equal = false;
            }
         }
      }

      return equal;
   }

   function isGameOver()
   {
      var isGameOver1 = true;
      if (!isFull())
      {
         isGameOver1 = false;
      }
      else
      {
         for (var row = 0; row < SIZE; ++row)
         {
            for (var column = 0; column < SIZE; ++column)
            {
               if ((column + 1 < SIZE) && board[row][column] === board[row][column + 1])
               {
                  isGameOver1 = false;
               }

               if ((row + 1 < SIZE) && board[row][column] === board[row + 1][column])
               {
                  isGameOver1 = false;
               }

            }
         }
      }

      return isGameOver1;
   }

   function isFull()
   {
      var isFull1 = true;
      for (var row = 0; row < SIZE; ++row)
      {
         for (var column = 0; column < SIZE; ++column)
         {
            if (board[row][column] === 0)
            {
               isFull1 = false;
               break;
            }
         }
      }

      return isFull1;
   }

   this.swipeLeft = function ()
   {
      var boardCopy = getCopy(board);
      shiftLeft();
      combineLeft();
      if (!isFull() && !areEqual(board, boardCopy))
      {
         addRandomBlock();
      }
      this.display();
      history.push(getCopy(board));
      if (isGameOver())
      {
         $('.gameOver').html("Game Over");
      }
   };

   this.swipeRight = function ()
   {
      var boardCopy = getCopy(board);
      board = getRotatedBoardRight(board, SIZE);
      board = getRotatedBoardRight(board, SIZE);
      shiftLeft();
      var combined = combineLeft();
      board = getRotatedBoardRight(board, SIZE);
      board = getRotatedBoardRight(board, SIZE);
      if (!isFull() && !areEqual(board, boardCopy))
      {
         addRandomBlock();
      }
      history.push(getCopy(board));
      this.display();
      if (isGameOver())
      {
         $('.gameOver').html("Game Over");
      }
   };

   this.swipeDown = function ()
   {
      var boardCopy = getCopy(board);
      board = getRotatedBoardRight(board, SIZE);
      shiftLeft();
      var combined = combineLeft();
      board = getRotatedBoardRight(board, SIZE);
      board = getRotatedBoardRight(board, SIZE);
      board = getRotatedBoardRight(board, SIZE);
      if (!isFull() && !areEqual(board, boardCopy))
      {
         addRandomBlock();
      }
      this.display();
      history.push(getCopy(board));
      if (isGameOver())
      {
         $('.gameOver').html("Game Over");
      }
   };

   this.swipeUp = function ()
   {
      var boardCopy = getCopy(board);
      board = getRotatedBoardRight(board, SIZE);
      board = getRotatedBoardRight(board, SIZE);
      board = getRotatedBoardRight(board, SIZE);
      shiftLeft();
      var combined = combineLeft();
      board = getRotatedBoardRight(board, SIZE);
      if (!isFull() && !areEqual(board, boardCopy))
      {
         addRandomBlock();
      }
      this.display();
      history.push(getCopy(board));
      if (isGameOver())
      {
         $('.gameOver').html("Game Over");
      }
   };

}
