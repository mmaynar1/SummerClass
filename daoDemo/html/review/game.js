/*global $, jQuery, alert*/

// global name space?
var LEFT = 37;
var UP = 38;
var RIGHT = 39;
var DOWN = 40;

function Game()
{
    "use strict";
    this.TWO_CHANCE = 75;
    this.FOUR_CHANCE = 25;
    this.board = [['', '', '', ''], ['', '', '', ''], ['', '', '', ''], ['', '', '', '']];

    this.initialize();
}

Game.prototype.getRandomCellIndex = function()
{
   return Math.floor(Math.random() * 4);
} ;

Game.prototype.getRandomCellValue = function()
{
   return Math.floor((Math.random() * 100) + 1) <= this.FOUR_CHANCE ? 4 : 2;
} ;


Game.prototype.initialize = function ()
{
    "use strict";
    this.addRandomCell();
    this.addRandomCell();
    this.display();
};

Game.prototype.display = function () {
    "use strict";
    var rows = $("tr"),
        row = 0,
        column = 0,
        columns;

   /*
   for (row = 0; row < rows.length; ++row)
   {
       for (column = 0; column < columns.length; ++column)
       {
          $('#cell_' + row + '_' + column).html( this.board.[row][column] );
       }
   }
   */

    for (row = 0; row < rows.length; ++row)
    {
        columns = $(rows[row]).children();

        for (column = 0; column < columns.length; ++column)
        {
            $(columns[column]).html(this.board[row][column]);
        }
    }
    this.colorTiles()
};

Game.prototype.moveUp = function () {
    "use strict";
    //$("td").html("up");
    var row = 0,
        column = 0,
        columns;

    debugger;
    for (row = 1; row < 4; ++row) 
    {

        for (column = 0; column < 4; ++column) 
        {
            if (this.board[0][column] === '') 
            {
                this.board[0][column] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else if (this.board[1][column] === '') 
            {
                this.board[1][column] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else if (this.board[2][column] === '') 
            {
                this.board[2][column] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else 
            {
                this.board[3][column] = this.board[row][column];
            }
        }
        
    }
    this.combineLikeTiles("up");
    this.placeNewTile();
    this.display();
};

Game.prototype.moveDown = function () {
    "use strict";
    //$("td").html("down");
    var row = 0,
        column = 0,
        columns;

   debugger;
    for (row = 2; row > -1; --row)
    {
        for (column = 3; column > -1; --column) 
        {
            if (this.board[3][column] === '') 
            {
                this.board[3][column] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else if (this.board[2][column] === '') 
            {
                this.board[2][column] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else if (this.board[1][column] === '') 
            {
                this.board[1][column] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else 
            {
                this.board[0][column] = this.board[row][column];
            }
        }
        
    }
    this.combineLikeTiles("down");
    this.placeNewTile();
    this.display();
};

Game.prototype.moveLeft = function () {
    "use strict";
    //$("td").html("left");
    var row = 0,
        column = 0,
        columns;

   debugger;
    for (row = 0; row < 4; ++row)
    {
        for (column = 1; column < 4; ++column) 
        {
            if (this.board[row][0] === '') 
            {
                this.board[row][0] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else if (this.board[row][1] === '') 
            {
                this.board[row][1] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else if (this.board[row][2] === '') 
            {
                this.board[row][2] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else {
                this.board[row][3] = this.board[row][column];
            }
        }
        
    }
    this.combineLikeTiles("left");
    this.placeNewTile();
    this.display();
};

Game.prototype.moveRight = function () {
    "use strict";
    //$("td").html("right");
    var row = 0,
        column = 0,
        columns;

   debugger;
    for (row = 3; row >= 0; --row)
    {
        for (column = 2; column >= 0; --column)
        {
            if (this.board[row][3] === '') 
            {
                this.board[row][3] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else if (this.board[row][2] === '') 
            {
                this.board[row][2] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else if (this.board[row][1] === '') 
            {
                this.board[row][1] = this.board[row][column];
                this.board[row][column] = '';
            } 
            else 
            {
                this.board[row][0] = this.board[row][column];
            }
        }
        
    }
    this.combineLikeTiles("right");
    this.placeNewTile();
    this.display();
};

Game.prototype.isGameFinished = function() {
    var noMoreSpaces = true,
        goalReached = false,
        row,
        column;
   debugger;
    for( row = 0; row < 4; ++row)
    {
        for( column = 0; column < 4; ++column)
        {
            if (this.board[row][column] === '')
            {
                noMoreSpaces = false;
            }
            if( parseInt(this.board[row][column]) >= 2048 )
            {
                goalReached = true;
            }
        }
    }
    return (noMoreSpaces || goalReached);
};

Game.prototype.placeNewTile = function()
{
   "use strict";

    if(!this.isGameFinished())
    {
       this.addRandomCell();
    }
};

Game.prototype.addRandomCell = function()
{
   "use strict";
    var row;
    var column;

   do {
       row = this.getRandomCellIndex();
       column = this.getRandomCellIndex();
   } while (this.board[row][column] !== '');

   this.board[row][column] = this.getRandomCellValue();
} ;

Game.prototype.colorTiles = function()
{
    "use strict";
    var rows = $("tr"),
        row = 0,
        column = 0,
        columns;

    //this.backgroundColors = {};
    //this.backgroundColors[2] = " #eee4da";
    //this.backgroundColors[4] = " #ede0c8";

    for (row = 0; row < rows.length; ++row) {
        columns = $(rows[row]).children();

        for (column = 0; column < columns.length; ++column) {
            if(this.board[row][column] == 2)
            {
                $(columns[column]).css("background", " #eee4da");
            }
            else if(this.board[row][column] == 4)
            {
                $(columns[column]).css("background", "#ede0c8");
            }
            else if(this.board[row][column] == 8)
            {
                $(columns[column]).css("background", "#f2b179");
            }
            else if(this.board[row][column] == 16)
            {
                $(columns[column]).css("background", "#f59563");
            }
            else if(this.board[row][column] == 32)
            {
                $(columns[column]).css("background", "#f9f6f2");
            }
            else if(this.board[row][column] == 64)
            {
                $(columns[column]).css("background", "#f9f6f2");
            }
            else if(this.board[row][column] == 128)
            {
                $(columns[column]).css("background", "#edcf72");
            }
            else if(this.board[row][column] == 256)
            {
                $(columns[column]).css("background", "#edcc61");
            }
            else if(this.board[row][column] == 512)
            {
                $(columns[column]).css("background", "#edc850");
            }
            else if(this.board[row][column] == 1024)
            {
                $(columns[column]).css("background", "#edc53f");
            }
            else if(this.board[row][column] == 2048)
            {
                $(columns[column]).css("background", "#f9f6f2");
            }
            else
            {
                $(columns[column]).css("background", "lightgray");
            }
        }
    }
};

Game.prototype.combineUp = function()
{
   debugger;
    for( row = 3; row > 0; --row)
    {
        for( column = 3; column > -1; --column)
        {
            if(this.board[row-1][column] === this.board[row][column] && this.board[row][column] !== '')
            {
                this.board[row-1][column] = parseInt(this.board[row][column])*2;
                this.board[row][column] = '';
            }
        }
    }
}

Game.prototype.combineDown = function()
{
   debugger;
    for( row = 0; row < 3 ; ++row)
    {
        for( column = 0; column < 4; ++column)
        {
            if(this.board[row+1][column] === this.board[row][column] && this.board[row][column] !== '')
            {
                this.board[row+1][column] = parseInt(this.board[row][column])*2;
                this.board[row][column] = '';
            }
        }
    }
} ;

Game.prototype.combineLeft = function()
{
   debugger;
    for( row = 3; row > -1 ; --row)
    {
        for( column = 3; column > 0; --column)
        {
            if(this.board[row][column-1] === this.board[row][column] && this.board[row][column] !== '')
            {
                this.board[row][column-1] = parseInt(this.board[row][column])*2;
                this.board[row][column] = '';
            }
        }
    }
} ;

Game.prototype.combineRight = function()
{
   debugger;
    for( row = 0; row < 4 ; ++row)
    {
        for( column = 0; column < 3; ++column)
        {
            if(this.board[row][column+1] === this.board[row][column] && this.board[row][column] !== '')
            {
                var newValue = parseInt(this.board[row][column]) * 2;
                if ( newValue > 16 )
                {
                   debugger;
                }
                this.board[row][column+1] = newValue;
                this.board[row][column] = '';
            }
        }
    }
}

Game.prototype.combineLikeTiles = function(direction)
{
   debugger;
    if(direction === 'up')
        this.combineUp();
    else if(direction === 'down')
        this.combineDown();
    else if(direction === 'left')
        this.combineLeft();
    else
        this.combineRight();
};

$(document).ready(function () {
    "use strict";
    var game = new Game();

    $(document).keydown(function (event)
    {
        if ( !game.isGameFinished() )
        {
           switch ( event.which )
           {
              case UP:
                 game.moveUp();
                 break;
              case DOWN:
                 game.moveDown();
                 break;
              case RIGHT:
                 game.moveRight();
                 break;
              case LEFT:
                 game.moveLeft();
                 break;
           }
        }
    });
});