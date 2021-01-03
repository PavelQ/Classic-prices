const ALLIANCE = "ALIANCE";
const HORDE = "HORDE";
$(function () {

//fractions colors
    const tr = document.querySelectorAll("tbody tr");
    tr.forEach(function (value) {
        if (value.querySelector("td:nth-child(2)").innerText === ALLIANCE) {
            value.classList.add("alliance-row")
        }
        if (value.querySelector("td:nth-child(2)").innerText === HORDE) {
            value.classList.add("horde-row")
        }
    });

    //feedback popup
    $('#exampleModal').on('shown.bs.modal', function (event) {
        $('#label-result').text("");
    });

    $('#sendFeedback').on('click', function(event){
        $.post('/sendFeedback',{
            name: $('#recipient-name').val(),
            email: $('#recipient-email').val(),
            text: $('#message-text').val()
            }).fail(function(){});
        $('#label-result').text("Thank you");
    });

});