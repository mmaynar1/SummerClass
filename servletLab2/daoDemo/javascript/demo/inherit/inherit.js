
function Vehicle()
{
   this.x = 0;   // public data
   this.y = 0;
   this.velocity = 0;
   this.NEW_LINE = "<br/>";

   // private data
   var employee = 'Selectable';

   // private method
   function sayHello()
   {
      employee = 'Robert';
      alert( 'set to Robert' );

      // can see private data and member variables if called with call( this )
      alert( "Hello " + employee +  ' velocity = ' + this.velocity );
   }

   this.setLocation = function( x, y )
   {
      this.x = x;
      this.y = y;
   } ;

   this.sayLocation = function()
   {
      document.write( "Vehicle: location = (" + this.x + ", " + this.y + ") employee = " + employee + this.NEW_LINE );
   } ;

   this.increaseSpeed = function()
   {
      this.velocity += 5;
   } ;

   this.saySpeed = function()
   {
      document.write( 'Vehicle: speed = ' + this.velocity + this.NEW_LINE );
   } ;

   this.sayName = function()
   {
      sayHello();   // crazy! no access to the object
      sayHello.call( this );   // the private method has access to the object
      document.write( "Name = Vehicle" + this.NEW_LINE );
   } ;
}

Vehicle.prototype.sayDescription = function()
{
   document.write( "Vehicle: description" + this.NEW_LINE );
   this.sayLocation();
   this.saySpeed();
} ;

function Car()
{
   this.Vehicle();   // part 1

   this.make = 'Ford';
   this.model = 'Focus';
   this.year = 2010;

   this.sayDescription = function()
   {
      document.write( "Car: description" + this.NEW_LINE );   // use member variable
      Vehicle.prototype.sayDescription.call(this);   // call base class method      super.
      document.write( 'Car: ' + this.make + ' ' + this.model + ' ' + this.year + ' speed = ' + this.velocity + this.NEW_LINE );
   } ;

   // overrides increaseSpeed in Vehicle
   this.increaseSpeed = function()
   {
      this.velocity += 20;
   } ;

   this.baseSayName = this.sayName;   // save off the old method

   this.sayName = function()
   {
      this.baseSayName();    // super.

      document.write( "Name = Car" + this.NEW_LINE );
   } ;

}

copyPrototypeAndConstructor( Car, Vehicle );   // part 2