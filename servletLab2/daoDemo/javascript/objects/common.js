
function Common()
{
	var self = this;

	self.BR = "<br/>";

	this.getWholeNumber = function( message, low, high )
	{
		var input;
		var value;

		message = message + low + " and " + high;
		do 
		{
			input  = window.prompt(message);			
			if ( input === null )
			{
				value = null;
				break;
			}
			value = parseInt( input );
		} 	while ( !self.isValidNumber( value, low, high ) );

		return value;
	} ;

	this.writeCell = function( value )
	{
		if ( value === undefined || value === '' || value === null )
		{
			value = '&nbsp;';
		}

		document.write( "<td>" + value + "</td>" );
	} ;

	self.isValidNumber = function( value, low, high )
	{
		return !( isNaN( value ) || value < low || value > high ) 
	} ;

	self.roundToTwoDecimals = function( value )
	{
		var roundedValue = 0;
		if ( value )
		{
			roundedValue = Math.round((value + 0.00001) * 100) / 100;
		}
	
		return roundedValue;
	} ;
} 