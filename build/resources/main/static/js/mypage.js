document.addEventListener('DOMContentLoaded', function() {
    var changePasswordBtn = document.getElementById('change-password-btn');
    var submitPasswordBtn = document.getElementById('submit-password-btn');
    var newPasswordField = document.getElementById('new_pwd-field');
    var errorMessage = document.getElementById('error_message');
    var currentPasswordField = document.getElementById('cur_pwd');

    changePasswordBtn.addEventListener('click', function() {
          if (currentPasswordField.value === currentPasswordField.dataset.password) {
            currentPasswordField.style.display = 'none';
            newPasswordField.style.display = 'block';
            submitPasswordBtn.style.display = 'inline-block';
            changePasswordBtn.style.display = 'none';
        } else {
            errorMessage.style.display = 'block';
            errorMessage.textContent = '현재 비밀번호가 일치하지 않습니다.'
            }
    });

    submitPasswordBtn.addEventListener('click', function(event) {
        event.preventDefault();
        var newPassword = document.getElementById('new_pwd').value;
        var confirmPassword = document.getElementById('con_pwd').value;

        if (newPassword !== confirmPassword) {
            errorMessage.style.display = 'block';
            errorMessage.textContent = '새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.';
        } else {
            document.getElementById('ch_pwd_f').submit();
        }
    });
});
/*
document.addEventListener('DOMContentLoaded', function() {
    var changePasswordBtn = document.getElementById('change-password-btn');
    var submitPasswordBtn = document.getElementById('submit-password-btn');
    var newPasswordField = document.getElementById('new_pwd-field');
    var errorMessage = document.getElementById('error-message');
    var currentPassword = document.getElementById('cur_pwd').value;

    changePasswordBtn.addEventListener('click', function() {
        newPasswordField.style.display = 'block';
        submitPasswordBtn.style.display = 'inline-block';
        changePasswordBtn.style.display = 'none';
    });

    submitPasswordBtn.addEventListener('click', function(event) {
        event.preventDefault();
        var new_password = document.getElementById('new_pwd').value;
        var confirm_password = document.getElementById('con_pwd').value;

        if (currentPassword !== document.getElementById('cur_pwd').value) {
            errorMessage.style.display = 'block';
            errorMessage.textContent = '현재 비밀번호가 일치하지 않습니다.';
        } else if (new_password !== confirm_password) {
            errorMessage.style.display = 'block';
            errorMessage.textContent = '새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.';
        } else {
            errorMessage.style.display = 'none';
            alert('새 비밀번호는 ' + new_password + ' 입니다.');
        }
    });
});*/
