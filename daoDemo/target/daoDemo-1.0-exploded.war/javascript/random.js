
function getRandomInteger( low, high )
{
   return Math.floor( (Math.random() * high) + low );
}

function getRandomClub()
{
   return 9000 + getRandomInteger( 0, 10 );
}

function getRandomTotal()
{
   return getRandomInteger( 50, 500 );
}

function getRandomLetter()
{
   return String.fromCharCode( 65 + getRandomInteger( 0, 26 ) );
}

function getRandomWord( low, high )
{
   var length = getRandomInteger( low - 1, high - 2 );
   var word = getRandomLetter().toUpperCase();
   var i;
   for ( i = 0; i < length; ++i )
   {
      word += getRandomLetter().toLowerCase();
   }

   return word;
}

function getRandomName()
{
   return getRandomWord( 3, 8 );
}

function getRandomMessage()
{
   var message = getRandomWord( 4, 9 );
   var i;
   var maxWords = getRandomInteger( 5, 20 );
   for ( i = 0; i < maxWords; ++i )
   {
      message += " " + getRandomWord( 4, 9 ).toLowerCase();
   }

    return message;
}
