"use strict";

$(document).ready(function () {
    $("#btn-submit").click(function () {
        var ssn = $("#input-ssn").val();
        var url = 'http://localhost:10060/sendHealthDetails/' + ssn;

        $.ajax({
            url: url,
            success: function (data) {
                $('#errormessage').html('');
                $("#success-block").html(data)
            },
            error: function (jqXHR, exception) {
                var msg = '';
                if (jqXHR.status === 0) {
                    msg = 'Not connect.\n Verify Network.';
                } else if (jqXHR.status == 404) {
                    msg = 'Requested page not found. [404]';
                } else if (jqXHR.status == 500) {
                    msg = 'ssn not requested.';
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
                $("#success-block").html('');
                $('#errormessage').html(msg);
            }
        });
    });

    $("#myButtonId").click(function () {
        console.log('hello');
    });


    $("#id-btn1").click( function (){
        console.log('clicked');
        /*  var urlSendHealthDetails = 'http://localhost:10060/sendHealthDetails/' + ssn;
          var id = '#id-btn-' + ssn;
          console.log('sending data');
          $.ajax({
              url: urlSendHealthDetails,
              success: function (data) {
                  $(id).addClass("disabled");
              },
              error: function (jqXHR, exception) {
                  console.log(exception);
              }
          });*/

    });

    $("#btn-getPendingRequests").click(function () {
            var url = 'http://localhost:10060/getOpenHealthDetailsRequests'
            $.ajax({
                url: url,
                success: function (data) {
                    console.log(data);
                    $("#data-pending-requests").html(
                        getHTMLRows(data));
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
            var id='id-btn';
            var buttonCode = '<button class="btn btn-small btn-success" id="myButtonId">send</button>';

            htmlCode += '   <tr>\n' +
                '                    <th scope="row">' + (index+1) + '</th>\n' +
                '                    <td>' + data.ssn + '</td>\n' +
                '                    <td>' + data.date + '</td>\n' +
                '                    <td>' + data.requester + '</td>\n' +
                '                    <td>' + data.status + '</td>\n' +
                '                    <td>' + buttonCode + '</td>\n' +
                '                </tr>';
        });
        return htmlCode;
    }

});

