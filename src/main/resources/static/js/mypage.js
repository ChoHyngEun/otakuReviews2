document.addEventListener('DOMContentLoaded', function() {
    var changePasswordBtn = document.getElementById('change-password-btn');
    var submitPasswordBtn = document.getElementById('submit-password-btn');
    var newPasswordField = document.getElementById('new_pwd-field');
    var errorMessage = document.getElementById('error-message');

    changePasswordBtn.addEventListener('click', function() {
        newPasswordField.style.display = 'block';
        submitPasswordBtn.style.display = 'inline-block';
        changePasswordBtn.style.display = 'none';
    });

    var form = document.getElementById('ch_pwd_f');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        // 비밀번호 변경 요청 전, 입력값 유효성 검사
        var currentPwdField = document.getElementById('cur_pwd');
        var newPwdField = document.getElementById('new_pwd');
        var confirmPwdField = document.getElementById('con_pwd');
        if (!currentPwdField.value.trim() || !newPwdField.value.trim() || !confirmPwdField.value.trim()) {
            errorMessage.innerText = '모든 항목을 입력해주세요.';
            errorMessage.style.display = 'block';
            return;
        }
        if (newPwdField.value !== confirmPwdField.value) {
            errorMessage.innerText = '새 비밀번호와 확인 비밀번호가 일치하지 않습니다.';
            errorMessage.style.display = 'block';
            return;
        }

        var xhr = new XMLHttpRequest();
        xhr.open('POST', form.action);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function() {
            if (xhr.status === 200) {
                window.location.href = xhr.responseURL;
            } else {
                errorMessage.innerText = xhr.responseText;
                errorMessage.style.display = 'block';
            }
        };
        xhr.send(new FormData(form));
    });
});