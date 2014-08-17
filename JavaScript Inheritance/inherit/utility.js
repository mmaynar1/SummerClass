
function copyPrototypeAndConstructor(descendant, parent)
{
    var constructor = parent.toString();
    //alert( constructor );
    var match = constructor.match( /\s*function (.*)\(/ );
    if ( match != null )
    {
       //alert( match[1] );
       descendant.prototype[match[1]] = parent;   // Car.Vehicle = Vehicle;
    }

    // copy in methods created at prototype level
    for (var member in parent.prototype)
    {
        //alert( member );
        descendant.prototype[member] = parent.prototype[member];
    }
}