function initialize()
{
   var maxInt = 9;
   var firstInt = getRandomInt(maxInt);
   var secondInt = getRandomInt(maxInt);
   var answer = doRandomOperation(firstInt, secondInt);
   setValues(firstInt, secondInt, answer);
}

function getRandomInt(value)
{
   return Math.floor((Math.random() * value) + 1);
}

function doRandomOperation(firstInt, secondInt)
{
   var maxIndex = 4;
   var operators = [ '+', '-', '*', '/' ];
   var index = getRandomInt(maxIndex);

   document.getElementById('operator').innerHTML = operators[index - 1];
   return eval( firstInt + operators[index - 1] + secondInt );
}

// todo name?
function showHidden()
{
   document.getElementById('equation').style.visibility = 'visible';
   document.getElementById('roots').style.visibility = 'visible';
}

function setValues(firstInt, secondInt, answer)
{
   document.getElementById('firstInt').innerHTML = firstInt;
   document.getElementById('secondInt').innerHTML = secondInt;
   document.getElementById('answer').innerHTML = answer;

   document.getElementById('firstValue').innerHTML = firstInt;
   document.getElementById('firstSquare').innerHTML = getSquare(firstInt);
   document.getElementById('firstCube').innerHTML = getCube(firstInt);

   document.getElementById('secondValue').innerHTML = secondInt;
   document.getElementById('secondSquare').innerHTML = getSquare(secondInt);
   document.getElementById('secondCube').innerHTML = getCube(secondInt);

   document.getElementById('answerValue').innerHTML = answer;
   document.getElementById('answerSquare').innerHTML = getSquare(answer);
   document.getElementById('answerCube').innerHTML = getCube(answer);
   showHidden();
}

function getSquare(value)
{
   return value * value;
}

function getCube(value)
{
   return Math.pow( value, 3 );
}

