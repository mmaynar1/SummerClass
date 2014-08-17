
function Loan( principal, annualRate, periods )
{
	var self = this;
	
	self.principal = principal;
	self.annualRate = annualRate;
	self.periods = periods;
	self.paymentsPerPeriod = 12;
	self.totalInterest = 0;
	self.totalPrincipalPaid = 0;
	self.payments = [];

	this.getTotalPrincipalPaid = function()
	{
		return common.roundToTwoDecimals( self.totalPrincipalPaid );
	} ;

	this.getTotalInterest = function()
	{
		return common.roundToTwoDecimals( self.totalInterest );
	} ;

	this.getPeriodInterestRate = function()
	{
		return ( ( self.annualRate / 100 ) / self.paymentsPerPeriod );
	} ;

	this.getPaymentCount = function()
	{
		return ( self.periods * self.paymentsPerPeriod );
	} ;

        this.getPayment = function()
        {
		var rate = self.getPeriodInterestRate();
		var payment = (self.principal * ( rate / ( 1 - Math.pow( 1 + rate, -1 * self.getPaymentCount() ) ) ) );
                return common.roundToTwoDecimals( payment );
        } ;

	this.getPayments = function()
	{
		self.prepareSchedule();
		return self.payments;
	} ;

	this.prepareSchedule = function()
	{
		var i;
		var payment = self.getPayment();
		var beginningBalance = self.principal;
		var interestPortion;
                var principalPortion;
		var endingBalance;
		var paymentCount = self.getPaymentCount();
		var rate = self.getPeriodInterestRate();

		self.payments = [];
		for ( i = 1; i <= paymentCount; ++i )
		{
			if ( i === paymentCount )
			{
				// special handle last payment
				interestPortion = common.roundToTwoDecimals(beginningBalance * rate);
				principalPortion = beginningBalance;
				payment = common.roundToTwoDecimals( interestPortion + principalPortion );
				endingBalance = 0;
			}
			else
			{
				interestPortion = common.roundToTwoDecimals(beginningBalance * rate);
				principalPortion = common.roundToTwoDecimals(payment - interestPortion);
				endingBalance = common.roundToTwoDecimals(beginningBalance - principalPortion);
			}

			self.totalInterest += interestPortion;
			self.totalPrincipalPaid += principalPortion;

			self.payments.push( { index: i, beginningBalance: beginningBalance,  payment: payment, 
					      interest: interestPortion, principal: principalPortion,  endingBalance: endingBalance } );

			beginningBalance = endingBalance;
		}
	} ;
}