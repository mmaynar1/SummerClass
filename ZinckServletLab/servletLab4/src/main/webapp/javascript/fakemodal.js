function showDialog(message, confirmFunction, cancelFunction, contents)
{
    $('#confirmContents').html( contents );

    $( "#confirmDelete" ).dialog({
        modal: true,
       width: 600,
       minHeight: 50,
       maxHeight: 400,
       maxWidth: 600,
        title: message,
        buttons: { "OK": function(){confirmFunction(); $( this ).dialog( "close" );}  ,
                   "CANCEL": function(){ cancelFunction(); $( this ).dialog( "close" ); } }
    });
}

