
function copyPrototype(descendant, parent)
{
    for (var member in parent.prototype)
    {
        //alert( member );
        descendant.prototype[member] = parent.prototype[member];
    }
}