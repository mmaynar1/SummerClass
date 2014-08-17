
function Vehicle()
{
   this.x = 0;
   this.y = 0;
   this.velocity = 0;
   this.NEW_LINE = "<br/>";

   this.setLocation = function( x, y )
   {
      this.x = x;
      this.y = y;
   } ;

   this.sayLocation = function()
   {
      document.write( "Vehicle: location = (" + this.x + ", " + this.y + ")" + this.NEW_LINE );
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
      document.write( "Name = Vehicle" + this.NEW_LINE );
   } ;
}

Vehicle.prototype.sayDescription = function()
{
   document.write( "Vehicle: description" + this.NEW_LINE );
   this.sayLocation();
   this.saySpeed();
} ;

function xxx()
{
   this.yyy = 'zzzzzzzzzzzzzzz';
}

function Car()
{
   Vehicle.call( this );
   xxx.call( this );

   this.make = 'Ford';
   this.model = 'Focus';
   this.year = 2010;

   this.sayDescription = function()
   {
      document.write( "Car: description" + this.NEW_LINE );   // use member variable
      Vehicle.prototype.sayDescription.call(this);   // call base class method
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
      this.baseSayName();

      document.write( "Name = Car" + this.NEW_LINE );
   } ;

}

copyPrototypeAndConstructor( Car, Vehicle );