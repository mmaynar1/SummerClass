
function formatMoney(number)
{
   number = number.toString().replace(/\$|\,/g, '');

   if (isNaN(number))
   {
      number = "0";
   }

   sign = (number == (number = Math.abs(number)));
   number = Math.floor(number * 100 + 0.50000000001);
   cents = number % 100;
   number = Math.floor(number / 100).toString();

   if (cents < 10)
   {
      cents = "0" + cents;
   }

   for (var i = 0; i < Math.floor(( number.length - ( 1 + i ) ) / 3); i++)
   {
      number = number.substring(0, number.length - ( 4 * i + 3 )) + ',' + number.substring(number.length - ( 4 * i + 3 ));
   }

   return ( ( ( sign ) ? '' : '-' ) + '$' + number + '.' + cents );
}