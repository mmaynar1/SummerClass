function Event()
{
   // add methods added via prototype
}

Event.prototype.checkKey = function (keyCode)
{
   switch (parseInt(keyCode))
   {
      case page.WEST:
      // fall through
      case page.NORTH:
      // fall through
      case page.EAST:
      // fall through
      case page.SOUTH:
         page.game.swipe(keyCode);
         break;
      case page.RESTART:
         page.game.start();
         break;
      case page.NUMBER_GAME:
         page.game.switchTexture(page.NUMBER_GAME);
         break;
      case page.LEGO_GAME:
         page.game.switchTexture(page.LEGO_GAME);
         break;
      case page.TWO_LIKELIHOOD:
         page.game.setNewTwoLikelihood();
         break;
      case page.COLUMN_CHANGE:
         page.game.setNewColumns();
         break;
      case page.ROW_CHANGE:
         page.game.setNewRows();
         break;
      default :
         // do nothing
         break;
   }
};

Event.prototype.animate = function (tile)
{
   tile.animate({
      top: -10,
      left: -10,
      width: '105%',
      height: '105%',
      margin: 0
   }, 100, function ()
   {
      tile.animate({
         top: 0,
         left: 0,
         width: '100%',
         height: '100%',
         margin: 0
      }, 100);
   })
};