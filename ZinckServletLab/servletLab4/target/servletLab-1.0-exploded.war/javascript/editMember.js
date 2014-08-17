$(document).ready(function ()
{
   $(".editMember button").each(function ()
   {
      var parentId = this.parent().id;
      this.click(function ()
      {
         $(parentId).submit();
      });
   });

   //console.log("Added click event to forms");
});
