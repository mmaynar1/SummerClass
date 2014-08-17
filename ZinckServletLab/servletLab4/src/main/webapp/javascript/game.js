function Game()
{
   this.emptyTileIds = [];

   this.IMAGE_PATH = "images/";
   this.IMAGE_TYPE = ".JPG";
   this.BLANK_NUMERIC_VALUE = "0";
   this.BLANK_IMAGE_SOURCE = (this.IMAGE_PATH + "0" + this.IMAGE_TYPE);
   this.DEFAULT_PERCENTAGE_FOR_TWO = 85;
   this.DEFAULT_ROWS = 4;
   this.DEFAULT_COLUMNS = 4;

   this.percentageForTwo = this.DEFAULT_PERCENTAGE_FOR_TWO;
   this.rows = this.DEFAULT_ROWS;
   this.columns = this.DEFAULT_COLUMNS;

   this.texturePath = "numbers/";
}

Game.prototype.getTwoOrFour = function ()
{
   var result = 2;
   var percentage = Math.floor((Math.random() * 100) + 1);

   if (percentage > this.percentageForTwo)
   {
      result = 4;
   }

   return result;
};

Game.prototype.getEmptyTileId = function ()
{
   var randomIndex;

   this.populateEmptyTileIds();

   randomIndex = Math.floor((Math.random() * (this.emptyTileIds.length)));
   return this.emptyTileIds[randomIndex];
};

Game.prototype.endGame = function ()
{
   alert("GAME OVER");
   this.start();
};

Game.prototype.isGameOver = function ()
{
   var row;
   var column;
   var currentId;
   var southRow;
   var southId;
   var eastColumn;
   var eastId;

   var isBoardFull = (this.getEmptyTileId() === undefined);
   var isMatchFound = false;

   for (row = 0; row < this.rows; ++row)
   {
      for (column = 0; column < this.columns; ++column)
      {
         currentId = this.getCellId(row, column);
         southRow = (parseInt(row) + 1);
         southId = this.getCellId(southRow, column);
         eastColumn = (parseInt(column) + 1);
         eastId = this.getCellId(row, eastColumn);

         if (southRow < this.rows)
         {
            if (this.hasSameValue(currentId, southId))
            {
               isMatchFound = true;
            }
         }
         if (eastColumn < this.columns)
         {
            if (this.hasSameValue(currentId, eastId))
            {
               isMatchFound = true;
            }
         }
      }
   }

   return (isBoardFull && !isMatchFound);
};

Game.prototype.insertRandomTile = function ()
{
   var number = this.getTwoOrFour();
   var id = this.getEmptyTileId();
   var image = $('#' + id);

   image.attr("src", this.IMAGE_PATH + this.texturePath + number + this.IMAGE_TYPE);
   image.attr("numericValue", number);

   if (this.isGameOver())
   {
      this.endGame();
   }
};

Game.prototype.populateEmptyTileIds = function ()
{
   this.emptyTileIds = [];

   var images = $('#gridContainer').find('img');
   var id;

   for (var i = 0; i < images.length; ++i)
   {
      id = $(images[i]).attr("id");

      if (this.isBlank(id))
      {
         this.emptyTileIds.push(id);
      }
   }
};

Game.prototype.getNeighborCellId = function (direction, id)
{
   var coordinates = id.split(page.ID_DELIMETER);
   var row = coordinates[1];
   var column = coordinates[2];
   var neighborId;

   switch (direction)
   {
      case page.NORTH:
         neighborId = this.getCellId((parseInt(row) - 1), column);
         break;
      case page.EAST:
         neighborId = this.getCellId( row, (parseInt(column) + 1));
         break;
      case page.WEST:
         neighborId = this.getCellId( row, (parseInt(column) - 1));
         break;
      case page.SOUTH:
         neighborId = this.getCellId((parseInt(row) + 1), column);
         break;
      default :
         alert("ERROR: Invalid direction found in function 'getNeighborCellId()': " + direction);
         break;
   }

   return neighborId;
};

Game.prototype.moveTile = function (currentId, neighborId)
{
   var currentTile = $('#' + currentId);
   var neighborTile = $('#' + neighborId);

   neighborTile.attr("src", currentTile.attr("src"))
      .attr("numericValue", currentTile.attr("numericValue"));

   currentTile.attr("src", this.BLANK_IMAGE_SOURCE)
      .attr("numericValue", this.BLANK_NUMERIC_VALUE);
};

Game.prototype.getStartingIndex = function (direction, dimension)
{
   var index = 0;
   var isRow = (dimension === "row");

   switch (direction)
   {
      case page.NORTH:
         index = isRow ? 1 : 0;
         break;
      case page.SOUTH:
         index = isRow ? (this.rows - 2) : (this.columns - 1);
         break;
      case page.EAST:
         index = isRow ? (this.rows - 1) : (this.columns - 2);
         break;
      case page.WEST:
         index = isRow ? 0 : 1;
         break;
      default:
         alert("ERROR: Invalid direction in 'getStartingIndex()': " + direction);
         break;
   }

   return index;
};

Game.prototype.getEndingIndex = function (direction, dimension)
{
   var index = 0;
   var isRow = (dimension === "row");

   switch (direction)
   {
      case page.NORTH:
      // fall through
      case page.WEST:
         index = isRow ? this.rows : this.columns;
         break;
      case page.SOUTH:
      // fall through
      case page.EAST:
         index = -1;
         break;
      default:
         alert("ERROR: Invalid direction in 'getEndingIndex()': " + direction);
         break;
   }

   return index;
};

Game.prototype.indexCondition = function (direction, current, ending)
{
   var condition = false;

   switch (direction)
   {
      case page.NORTH:
      // fall through
      case page.WEST:
         condition = (current < ending);
         break;
      case page.EAST:
      // fall through
      case page.SOUTH:
         condition = (current > ending);
         break;
      default:
         alert("ERROR: Invalid direction in 'indexCondition()': " + direction);
         break;
   }

   return condition;
};

Game.prototype.getIndexAdjustment = function (direction)
{
   var adjustment = 1;

   switch (direction)
   {
      case page.NORTH:
      // fall through
      case page.WEST:
         adjustment = 1;
         break;
      case page.EAST:
      // fall through
      case page.SOUTH:
         adjustment = -1;
         break;
      default:
         alert("ERROR: Invalid direction in 'getIndexAdjustment()': " + direction);
         break;
   }

   return adjustment;
};

Game.prototype.getCellId = function(row, column)
{
   return (page.CELL + page.ID_DELIMETER + row + page.ID_DELIMETER + column);
};

Game.prototype.moveTiles = function (direction)
{
   var row;
   var column;
   var currentId;
   var neighborId;
   var isMoving = false;
   var hasMoved = false;
   var startingRowIndex = this.getStartingIndex(direction, "row");
   var endingRowIndex = this.getEndingIndex(direction, "row");
   var startingColumnIndex = this.getStartingIndex(direction, "column");
   var endingColumnIndex = this.getEndingIndex(direction, "column");

   do
   {
      isMoving = false;

      for (row = startingRowIndex;
           this.indexCondition(direction, row, endingRowIndex);
           row += this.getIndexAdjustment(direction))
      {
         for (column = startingColumnIndex;
              this.indexCondition(direction, column, endingColumnIndex);
              column += this.getIndexAdjustment(direction))
         {
            currentId = this.getCellId(row, column);
            neighborId = this.getNeighborCellId(direction, currentId);

            if (!this.isBlank(currentId) && this.isBlank(neighborId))
            {
               isMoving = true;
               this.moveTile(currentId, neighborId);
               hasMoved = true;
            }
         }
      }
   } while (isMoving);

   return hasMoved;
};

Game.prototype.isBlank = function (id)
{
   return ($('#' + id).attr("numericValue") === this.BLANK_NUMERIC_VALUE);
};

Game.prototype.hasSameValue = function (id1, id2)
{
   var id1Tile = $('#' + id1);
   var id2Tile = $('#' + id2);

   return (id1Tile.attr("numericValue") === id2Tile.attr("numericValue"));
};

Game.prototype.combineTiles = function (currentId, neighborId)
{
   var currentTile = $('#' + currentId);
   var neighborTile = $('#' + neighborId);
   var doubleAmount = (parseInt(currentTile.attr("numericValue")) * 2);

   neighborTile.attr("src", this.IMAGE_PATH + this.texturePath + doubleAmount + this.IMAGE_TYPE)
      .attr("numericValue", doubleAmount);

   currentTile.attr("src", this.BLANK_IMAGE_SOURCE)
      .attr("numericValue", this.BLANK_NUMERIC_VALUE);

   page.event.animate(neighborTile);

   if (doubleAmount === 2048)
   {
      alert("Congratulations, you got the 2048 tile!");
   }

   if (doubleAmount === 4096)
   {
      alert("Congratulations, you got the 4096 tile!");
   }

   return doubleAmount;
};

Game.prototype.updateScore = function (score)
{
   var scoreElement = $('#score');
   var currentScore = scoreElement.html();

   scoreElement.html(parseInt(currentScore) + score);

   $('#scoreName input').attr("value", scoreElement.html());
};

Game.prototype.mergeTiles = function (direction)
{
   var row;
   var column;
   var currentId;
   var neighborId;
   var hasMerged = false;
   var startingRowIndex = this.getStartingIndex(direction, "row");
   var endingRowIndex = this.getEndingIndex(direction, "row");
   var startingColumnIndex = this.getStartingIndex(direction, "column");
   var endingColumnIndex = this.getEndingIndex(direction, "column");
   var score = 0;

   for (row = startingRowIndex;
        this.indexCondition(direction, row, endingRowIndex);
        row += this.getIndexAdjustment(direction))
   {
      for (column = startingColumnIndex;
           this.indexCondition(direction, column, endingColumnIndex);
           column += this.getIndexAdjustment(direction))
      {
         currentId = this.getCellId(row, column);
         neighborId = this.getNeighborCellId(direction, currentId);

         if (!this.isBlank(currentId) && this.hasSameValue(currentId, neighborId))
         {
            score += this.combineTiles(currentId, neighborId);
            hasMerged = true;
         }
      }
   }

   if (hasMerged)
   {
      this.updateScore(score);
   }

   return hasMerged;
};

Game.prototype.swipe = function (direction)
{
   var hasMoved = this.moveTiles(direction);
   var hasMerged = this.mergeTiles(direction);

   if (hasMerged)
   {
      this.moveTiles(direction);
   }

   if (hasMoved || hasMerged)
   {
      this.insertRandomTile();
   }
};

Game.prototype.getInputName = function (row, column)
{
   return (page.INPUT + page.ID_DELIMETER + row + page.ID_DELIMETER + column);
};

Game.prototype.initializeGrid = function (numericValues)
{
   var row;
   var column;

   var board = $('#gridContainer');

   board.html("<table id='grid' class='grid' border='0'>");

   for (row = 0; row < this.rows; ++row)
   {
      board.append("<tr id='" + row + "' class='row'>");

      for (column = 0; column < this.columns; ++column)
      {
         var numericValue;
         if (numericValues)
         {
            numericValue = numericValues[row][column];
         }
         else
         {
            numericValue = "0";
         }

         var cellId = this.getCellId(row, column);
         var inputName = this.getInputName(row, column);

         board.append("<td class='tile'><div class='imageContainer'>" +
            "<input type='hidden' id='" + inputName + "' name='" + inputName + "' value='" + numericValue + "'/>" +
            "<img id='" + cellId + "' numericValue='" + numericValue + "' src='" + this.IMAGE_PATH + this.texturePath + numericValue + this.IMAGE_TYPE + "'/>" +
            "</div></td>");
      }

      board.append("</tr>");
   }

   board.append("</table>");

   $('#percentageFor2').html(this.percentageForTwo);
   $('#numberOfColumns').html(this.columns);
   $('#numberOfRows').html(this.rows);
};

Game.prototype.initializeScore = function (score)
{
   if (score)
   {
      $('#score').html(score);
   }
   else
   {
      $('#score').html(0);
   }
};

Game.prototype.start = function (score, board)
{
   this.initializeGrid(board);
   this.initializeScore(score);

   if (!score || !board)
   {
      this.insertRandomTile();
      this.insertRandomTile();
   }
};

Game.prototype.saveGame = function ()
{
   var scoreElement = $('#score');

   $('#scoreName input').val(scoreElement.html());

   $('.imageContainer').each(function ()
      {
         this.childNodes[0].setAttribute("value", this.childNodes[1].getAttribute("numericValue"));
      }
   );
};