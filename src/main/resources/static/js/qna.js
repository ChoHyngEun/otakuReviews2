document.addEventListener('DOMContentLoaded', function() {
    var changeBtn = document.getElementById('new_que');
    var submitBtn = document.getElementById('submit_btn');
    var qnaForm = document.getElementById('form');

    changeBtn.addEventListener('click', function() {
        qnaForm.style.display = 'block';
    });

    submitBtn.addEventListener('click', function(event) {
        event.preventDefault();
        qnaForm.style.display = 'none';
        document.getElementById('a').submit();
    });

});

