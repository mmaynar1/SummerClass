/**
 * Created by MitchM on 7/18/14.
 */
function doArithmetic()
{
   var result;
   var first = Math.floor((Math.random() * 9) + 1);
   setValueById("first", first);
   var second = Math.floor((Math.random() * 9) + 1);
   setValueById("second", second);

   var multiply = "*";
   var divide = "/";
   var add = "+";
   var subtract = "-";
   var operatorId = "operator";
   var operator;
   operator = Math.floor((Math.random() * 4) + 1);
   switch (operator)
   {
      case 1:
         setValueById(operatorId, multiply);
         result = first * second;
         break;
      case 2:
         setValueById(operatorId, divide);
         result = first / second;
         break;
      case 3:
         setValueById(operatorId, add);
         result = first + second;
         break;
      case 4:
         setValueById(operatorId, subtract);
         result = first - second;
         break;
   }

   setValueById("result", result);
}

function square(number)
{
   return (number * number);
}

function cube(number)
{
   return (number * number * number);
}

function setValueById(id, value)
{
   document.getElementById(id).innerHTML = value;
}

function getRandomColor()
{
   return Math.floor(Math.random()*16777215).toString(16);
}

