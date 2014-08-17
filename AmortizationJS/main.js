function Display()
{
   var utility = new Utility();

   this.displayResults = function (amountBorrowed, annualInterestRate, termInYears, details)
   {
      document.getElementById("amountBorrowed").innerHTML = utility.formatMoney(amountBorrowed);
      document.getElementById("annualInterestRate").innerHTML = annualInterestRate;
      document.getElementById("termInYears").innerHTML = termInYears;

      this.displaySchedule(details);
   };

   this.writeRow = function(paymentNumber, monthlyPayment, interest, principal, totalInterest, totalPrincipal, remainingBalance)
   {
      var table = document.getElementById("schedule");
      var row = table.insertRow(table.rows.length);
      var paymentNumberCell = row.insertCell(0);
      var monthlyPaymentCell = row.insertCell(1);
      var interestCell = row.insertCell(2);
      var principalCell = row.insertCell(3);
      var totalInterestCell = row.insertCell(4);
      var totalPrincipalCell = row.insertCell(5);
      var remainingBalanceCell = row.insertCell(6);

      paymentNumberCell.innerHTML = paymentNumber;
      monthlyPaymentCell.innerHTML = utility.formatMoney(monthlyPayment.toFixed(2));
      interestCell.innerHTML = utility.formatMoney(interest.toFixed(2));
      principalCell.innerHTML = utility.formatMoney(principal.toFixed(2));
      totalInterestCell.innerHTML = utility.formatMoney(totalInterest.toFixed(2));
      totalPrincipalCell.innerHTML = utility.formatMoney(totalPrincipal.toFixed(2));
      remainingBalanceCell.innerHTML = utility.formatMoney(remainingBalance.toFixed(2));
   }
}

Display.prototype.displayCancelPage = function ()
{
   document.getElementById("page").innerHTML = "Cancelled Calculation";
};

Display.prototype.displaySchedule = function (details)
{
   var i;
   for (i = 0; details.length; ++i)
   {
      this.writeRow(details[i].paymentNumber,
         details[i].monthlyPayment,
         details[i].interest,
         details[i].principal,
         details[i].totalInterest,
         details[i].totalPrincipal,
         details[i].remainingBalance)
   }
};

function Schedule()
{
   var PAYMENTS_PER_YEAR = 12;
   var MIN_AMOUNT_BORROWED = 1;
   var MAX_AMOUNT_BORROWED = 10000000;
   var display = new Display();

   this.go = function ()
   {
      var amountBorrowed = getAmountBorrowed();
      if (!isCancelled(amountBorrowed))
      {
         var annualInterestRate = getAnnualInterestRate();
         if (!isCancelled(annualInterestRate))
         {
            var termInYears = getTermInYears();
            if (!isCancelled(termInYears))
            {
               var details = calculate(amountBorrowed, annualInterestRate, termInYears);
               display.displayResults(amountBorrowed, annualInterestRate, termInYears, details);
            }
         }
      }
   };

   function getMonthlyInterestRate(annualInterestRate)
   {
      var divisor = PAYMENTS_PER_YEAR * 100;
      return (annualInterestRate / divisor);
   }

   function getTotalNumberOfPayments(termInYears)
   {
      return PAYMENTS_PER_YEAR * termInYears;
   }

   function determineMonthlyPayment(amountBorrowed, annualInterestRate, termInYears)
   {
      var numberOfPayments = getTotalNumberOfPayments(termInYears);
      var monthlyInterestRate = getMonthlyInterestRate(annualInterestRate);
      var numerator = monthlyInterestRate * amountBorrowed * Math.pow((monthlyInterestRate + 1), numberOfPayments);
      var denominator = Math.pow((1 + monthlyInterestRate), numberOfPayments) - 1;
      return (numerator / denominator);
   }

   function calculate(amountBorrowed, annualInterestRate, termInYears)
   {
      var totalNumberOfPayments = getTotalNumberOfPayments(termInYears);
      var monthlyPayment = determineMonthlyPayment(amountBorrowed, annualInterestRate, termInYears);
      var remainingBalance = amountBorrowed;
      var totalInterest = 0;
      var totalPrincipal = 0;
      var paymentNumber;
      var details = [];

      for (paymentNumber = 1; paymentNumber <= totalNumberOfPayments; ++paymentNumber)
      {
         var interest = remainingBalance * getMonthlyInterestRate(annualInterestRate);
         var principal = monthlyPayment - interest;
         totalInterest += interest;
         totalPrincipal += principal;
         remainingBalance = remainingBalance - principal;

         if (paymentNumber === totalNumberOfPayments)
         {
            //Adjustments for last payment fields
            var extraMoney = Math.abs(remainingBalance);
            monthlyPayment -= extraMoney;
            remainingBalance = remainingBalance + extraMoney;
            principal = principal - extraMoney;
            totalPrincipal = totalPrincipal - extraMoney;
         }

         details.push(new PaymentDetail(paymentNumber, monthlyPayment, interest, principal, totalInterest, totalPrincipal, remainingBalance));

      }

      return details;

   }

   function getAmountBorrowed()
   {
      return getValidNumber("Amount Borrowed must be between " + MIN_AMOUNT_BORROWED + " and " + MAX_AMOUNT_BORROWED,
         MIN_AMOUNT_BORROWED,
         MAX_AMOUNT_BORROWED);
   }

   function getAnnualInterestRate()
   {
      return getValidNumber("Annual Interest Rate:", 1, 50);
   }

   function getTermInYears()
   {
      return getValidNumber("Term In Years:", 1, 50);
   }

   function isCancelled(number)
   {
      var cancelled = false;
      if (number == null)
      {
         cancelled = true;
         display.displayCancelPage();
      }
      return cancelled;
   }

   function getValidNumber(message, minNumber, maxNumber)
   {
      var number = prompt(message);

      if (!isCancelled(number))
      {
         while (isNaN(number) || number < minNumber || number > maxNumber)
         {
            number = prompt(message);
            if (isCancelled(number))
            {
               break;
            }
         }
      }

      return number;
   }

}

function PaymentDetail(paymentNumber, monthlyPayment, interest, principal, totalInterest, totalPrincipal, remainingBalance)
{
   this.paymentNumber = paymentNumber;
   this.monthlyPayment = monthlyPayment;
   this.interest = interest;
   this.principal = principal;
   this.totalInterest = totalInterest;
   this.totalPrincipal = totalPrincipal;
   this.remainingBalance = remainingBalance;

   return this;
}