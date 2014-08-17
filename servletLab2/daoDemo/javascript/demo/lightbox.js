
// jQuery replacement
function JavaScriptHelper()
{
	this.html = function( id, html )
	{
		document.getElementById( id ).innerHTML = html;
	} ;

	this.hide = function( id )
	{
		document.getElementById( id ).style.display = 'none';
	} ;

	this.show = function( id )
	{
		document.getElementById( id ).style.display = 'inline';
	}
}

function Display()
{
   var mainId = 'popUpContainer';

   this.center = function()
	{
	      var dialog = document.getElementById( mainId );
	      dialog.style.display = "inline";
	      dialog.style.top = (document.body.clientHeight - dialog.offsetHeight) / 2;
	      dialog.style.left = (document.body.clientWidth - dialog.offsetWidth) / 2;
	} ;
	
   this.hide = function()
	{
		$.hide( mainId );
		$.hide( 'blocker' );
	} ;
	
   this.show = function( caption, contents )
	{
		$.html( 'popUpCaption', caption );
		$.html( 'popUpContents', contents );
		$.show( 'blocker' );
      this.center();
	} ;
}

var $ = new JavaScriptHelper();
var display = new Display();

