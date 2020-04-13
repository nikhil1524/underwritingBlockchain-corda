"use strict";

$(document).ready(function() {

    $("#btn-submit").click( function(){
        var ssn = $("#input-ssn").val();
        var url = 'http://localhost:10060/sendHealthDetails/' +ssn;

        $.ajax({
            url: url,
            success: function( data ) {
                $("#success-block").html('Responded  to the insurance company TransactionId'+ data)
            },
            error: function (jqXHR, exception) {
                var msg = '';
                if (jqXHR.status === 0) {
                    msg = 'Not connect.\n Verify Network.';
                } else if (jqXHR.status == 404) {
                    msg = 'Requested page not found. [404]';
                } else if (jqXHR.status == 500) {
                    msg = 'Internal Server Error [500].';
                } else if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n' + jqXHR.responseText;
                }
                console.log(exception);
                $('#errormessage').html(msg);
            }
        });
    });
})

