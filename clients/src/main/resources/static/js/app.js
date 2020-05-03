"use strict";

$(document).ready(function() {

    $("#refresh-btn").click( function(){
        var rowStart =false;

        $.ajax({
            url: "http://localhost:10055/networkMap",

            success: function( data ) {
                    $("#nodes").html(getNodesBlock(data))
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
        // $.ajax({
        //     url: "http://localhost:10055/networkMap"
        // }).then(function(data) {
        //     $('#nodes').append(getNodesBlock(data));
        // }).catch( function(error){
        //     console.log('error'+ error);
        // });
    });

    function getNodesBlock(data){
        var code ='';
        var row = 0;

        $.each(data,function(index, data) {
            console.log(data);
            row = index +1;

            if (row%3 == 1) {
                code += '<div class="row col-sm-12 mt-5">';
            }

            code += '<div class="col-sm-4">' +
                '        <div class="card" style="width: 18rem;">\n' +
                '        <div class="card-header node-header">' +
                data.nodeName +
                '</div>\n' +
                '        <div class="card-body">\n' +
                '            <div class="container container-fluid">\n' +
                '                <div class="row float-right">\n' +
                '                    <span><img class="flag-icon" src="images/' +
                data.nodeCountry +
                '.png"></span>\n' +
                '                </div><br>\n' +
                '                <div class="row">\n' +
                '                    <span class="node-details-key">Location:</span>\n' +
                '                    <span class="node-details-value">' +
                                data.nodeLocation +
                '</span>\n' +
                '                </div>\n' +
                '                <div class="row">\n' +
                '                    <span class="node-details-key">Address:</span>\n' +
                '                    <span class="node-details-value">' +
                                data.nodeAddress +
                '</span>\n' +
                '                </div>\n' +
                '                <div class="row">\n' +
                '                    <span class="node-details-key">Identity:</span>\n' +
                '                    <span class="node-details-value">' +
                data.nodeSerial +
                '</span>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </div>' +
                '</div>';

            if (row%3 == 0) {
                code += '</div>';
            }
        });
        return code;
    }
})

