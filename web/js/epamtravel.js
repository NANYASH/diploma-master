$(document).ready(function() {

    var x = new Date();
    var d = {
        day: x.getDate() + 1,
        month: (x.getMonth() + 1),
        year: x.getFullYear()
    };
    var D = {};
    for (var n in d) {
        D[n] = (parseInt(d[n], 10) < 10 ) ? ('0'+d[n]) : (d[n]);
    }
    var z = D.year + '-' + D.month + '-' + D.day;

    var departure_date = document.getElementById('departure-date');
    $('#departure-date').attr({
        'min' : z
    });
    $('#arrival-date').attr({
        'min' : z
    });

    $('#departure-date').attr({
        'max' : '2100-01-01'
    });
    $('#arrival-date').attr({
        'max' : '2100-01-01'
    });

    departure_date.addEventListener('change', function () {
        $('#arrival-date').attr({
            'min' : $('#departure-date').val()
        });
    })
});