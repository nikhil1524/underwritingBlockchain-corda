"use strict";

$(document).ready(function() {

    $("#btn-submit").click( function(){
        var ssn = $("#input-ssn").val();
        var url = 'http://localhost:10055/requestHealthDetails/' +ssn;

        $.ajax({
            url: url,
          success: function( data ) {
                $('#errormessage').html('');
                $("#success-block").html(data);
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
                $("#success-block").html('');
            }
        });
    });

    $("#btn-getIncommingRequests").click(function () {
            var url = 'http://localhost:10055/fetchRespondedHealthDetails'
            $.ajax({
                url: url,
                success: function (data) {
                    console.log(data);
                    console.log(JSON.parse(data).length);
                    if (JSON.parse(data).length > 0) {
                        $("#data-incomming-requests").html(
                            getHTMLRows(data));
                    } else{
                        $("#data-incomming-requests").html(
                            "<h5> <span class=\"text-center\">No Response Recieved</span></h5>");
                    }
                },
                error: function (jqXHR, exception) {
                    console.log(exception);
                }
            })
        }
    );

    function getHTMLRows(datas) {
        var htmlCode = '';
        $.each(JSON.parse(datas), function (index, data) {
            htmlCode += '   <tr>\n' +
                '                    <th scope="row">' + (index+1) + '</th>\n' +
                '                    <td>' + data.ssn + '</td>\n' +
                '                    <td>' + data.date + '</td>\n' +
                '                    <td>' + data.responder + '</td>\n' +
                '                    <td>' + data.name +' ' +data.surname + '</td>\n' +
                '                    <td>' + data.dob + '</td>\n' +
                '                    <td>' + data.bmi + '</td>\n' +
                '                    <td>' + data.diabatics + '</td>\n' +
                '                    <td>' + data.bp + '</td>\n' +
                '                    <td>' + data.heartProblen + '</td>\n' +
                '                </tr>';
        });
        return htmlCode;
    }
})

