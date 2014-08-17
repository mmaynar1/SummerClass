function submitForm()
{
   $("#form").submit();
}

function submitNavigate()
{
   $(".navigation").submit();
}

function submitFilter()
{
   $("#filterForm").submit();
}

function submitButton()
{
   $('#addMemberForm').submit();
}

function addEmployee1()
{
   $('#addEmployee1').submit();
}

function addEmployee2()
{
   $('#addEmployee2').submit();
}

function addEmployee3()
{
   $('#addEmployee3').submit();
}


function editMemberButton()
{
   $('#editMemberForm').submit();
}

function editEmployeeButton()
{
   $('#editEmployeeForm').submit();
}

function filterMember()
{
   $('#filterMemberForm').submit();
}

function filterEmployee()
{
   $('#filterEmployeeForm').submit();
}

function addEmployee2GoPrevious()
{
   $('#previousPage').val('true');
   addEmployee2();
}

function addEmployee3GoPrevious()
{
   $('#previousPage2').val('true');
   addEmployee3();
}


var page =
{
   ajaxError: function ajaxError(response)
   {
      alert("AJAX Error: status = " + response.status + " " + response.statusText);
   },

   ajaxStart: function ()
   {
      $.blockUI({ message: null});
      page.startAjax = new Date();
   },

   ajaxStop: function ()
   {
      $.unblockUI();

      page.stopAjax = new Date();
      var elapsedTime = page.stopAjax - page.startAjax;
      $('#ajaxTime').html(elapsedTime + ' ms');
   } };


$(document).ready(function ()
{
   $(document).ajaxError(page.ajaxError).ajaxStart(page.ajaxStart).ajaxStop(page.ajaxStop);

   $.ajaxSetup({ type: 'POST', timeout: 10000, cache: false });

   $("#createEventSessionButton").click(submitForm);
   $(".createEventSessionNavigationButton").click(submitNavigate);
   $("#filterButton").click(submitFilter);
   $("#indexButton").click(function ()
   {
      document.location.href = '/servletLab-1.0-exploded/createEventSession.html';
   });

   $("#button").click(submitForm);
   $("#loginButton").click(submitForm);

   $('#submitButton').click(submitButton);

   $('#addEmployee1Button').click(addEmployee1);

   $('#addEmployee2ButtonNext').click(addEmployee2);

   $('#addEmployee2ButtonPrevious').click(addEmployee2GoPrevious);

   $('#addEmployee3ButtonPrevious').click(addEmployee3GoPrevious);

   $('#addEmployee3ButtonFinish').click(addEmployee3);

   $('#editMemberButton').click(editMemberButton);

   $('#editEmployeeButton').click(editEmployeeButton);

   $('#filterMember').click(filterMember);

   $('#filterEmployee').click(filterEmployee);
});