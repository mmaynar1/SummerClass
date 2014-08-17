
function Schedule( principal, rate, years )
{
	var self = this;

	self.print = function()
	{
		var loan = new Loan( principal, rate, years );
		var payments = loan.getPayments();
		var i;

		self.startTable();

		for ( i = 0; i < payments.length; ++i )
		{
			self.addRow( payments[i] );
		}

		self.addRow( { index: 'Total', beginningBalance: '', payment: '', interest: loan.getTotalInterest(), 
                               principal: loan.getTotalPrincipalPaid(), endingBalance: '' } );

		self.endTable();
	} ;

	self.startTable = function()
	{
		document.write("<H1> Home Loan Principal/Interest Payment Schedule</H1>");
		document.write( '<br/>' );
		document.write( "<table border='1'>" );
		document.write( "<tr>" );
		common.writeCell( "Payment#" );
		common.writeCell( "Beginning Balance" );
		common.writeCell( "Payment" );
		common.writeCell( "Interest Portion" );
		common.writeCell( "Principal Portion" );
		common.writeCell( "Ending Balance" );	
		document.write( "</tr>" );		
	} ;

	self.endTable = function()
	{
		document.write( "</table>" );
	} ;

	self.addRow = function( paymentRow )
	{ 
		debugger;
		document.write( "<tr>" );
 		common.writeCell( paymentRow.index ) ;
		common.writeCell( paymentRow.beginningBalance);
		common.writeCell( paymentRow.payment );
		common.writeCell( paymentRow.interest );
		common.writeCell( paymentRow.principal );
		common.writeCell( paymentRow.endingBalance );
		document.write( "</tr>" );
	} ;
}