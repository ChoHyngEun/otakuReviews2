const errorMessage = document.querySelector(".error-message");
    if (errorMessage !== null) {
        alert(errorMessage.textContent);
    }

document.querySelector('form').addEventListener('submit', function(event) {
  event.preventDefault(); // 기존의 submit 이벤트 동작을 막습니다.
  
  // 서버에 로그인 요청을 보냅니다.
  var xhr = new XMLHttpRequest();
  xhr.open('POST', '/login');
  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
  xhr.onload = function() {
    if (xhr.status === 200) { // 로그인 성공 시
      window.location.href = '/otp-auth';
    } else { // 로그인 실패 시
      var errorMessage = xhr.responseText; // 서버에서 받은 에러 메시지
      var errorElement = document.querySelector('#error-message');
      errorElement.querySelector('p').textContent = errorMessage;
      errorElement.style.display = 'block'; // 에러 메시지 출력
    }
  };
  xhr.send(new FormData(event.target));
});