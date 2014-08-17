function Display()
{
   this.reset();
}

Display.prototype.getOperator = function (id)
{
   var operator;
   switch (id)
   {
      case "addition":
         operator = "+";
         break;
      case "multiplication":
         operator = "*";
         break;
      case "division":
         operator = "/";
         break;
      default:
         operator = "ERROR";
         break;
   }

   return operator;
};

Display.prototype.backspace = function()
{
   var length = this.text.length;
   var lastCharacter = this.text.charAt(length - 1);
   if (lastCharacter === "." || lastCharacter === "-")
   {
      this.decimalPlaceable = true;
   }
   else if (lastCharacter === "+" ||  lastCharacter === "*" || lastCharacter === "/")
   {
      this.operatorPlaceable = true;
   }
   else
   {
      //no change to status variables
   }
   this.text = this.text.slice(0,-1);
   this.display();
};

Display.prototype.reset = function ()
{
   this.text = "";
   this.operatorPlaceable = false;
   this.decimalPlaceable = true;
};

Display.prototype.evaluate = function ()
{
   this.text = (eval(this.text)).toString();
   this.display();
};

Display.prototype.addOperatorToDisplay = function (id)
{
   var operator = this.getOperator(id);

   if (this.operatorPlaceable)
   {
      this.addText(operator);
      this.operatorPlaceable = false;
      this.decimalPlaceable = true;
   }
};

Display.prototype.addNumberToDisplay = function (id)
{
   var numbers =
   {
      0: "0",
      1: "1",
      2: "2",
      3: "3",
      4: "4",
      5: "5",
      6: "6",
      7: "7",
      8: "8",
      9: "9",
      pi: "3.14"
   };

   if (id === "pi")
   {
      if (this.decimalPlaceable)
      {
         this.addText(numbers[id]);
         this.decimalPlaceable = false;
      }
   }
   else
   {
      this.addText(numbers[id]);
   }
   this.operatorPlaceable = true;
};

Display.prototype.addText = function (text)
{
   this.text += text;
   this.display();
};

Display.prototype.clear = function ()
{
   this.reset();
   this.display();
};

Display.prototype.display = function ()
{
   document.getElementById("display").innerHTML = this.text;
};

Display.prototype.addDecimal = function ()
{
   var decimal = ".";
   var length = this.text.length;
   var lastElement = this.text.charAt(length - 1);
   if (lastElement !== decimal && this.decimalPlaceable)
   {
      this.addText(decimal);
      this.decimalPlaceable = false;
   }
};

Display.prototype.addNegative = function ()
{
   var negativeSign = "-";
   var decimal = ".";
   var length = this.text.length;
   var lastElement = this.text.charAt(length - 1);
   if (lastElement !== negativeSign && lastElement !== decimal)
   {
      this.addText(negativeSign);
      this.decimalPlaceable = true;
      this.operatorPlaceable = false;
   }
};