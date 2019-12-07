const ALLIANCE = "ALIANCE";
const HORDE = "HORDE";
$(function () {
    const tr = document.querySelectorAll("tbody tr");
    tr.forEach(function (value) {
        if (value.querySelector("td:nth-child(2)").innerText === ALLIANCE) {
            value.classList.add("alliance-row")
        }
        if (value.querySelector("td:nth-child(2)").innerText === HORDE) {
            value.classList.add("horde-row")
        }
    });
});