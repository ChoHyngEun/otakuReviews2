const errorMessage = document.querySelector(".error-message");
    if (errorMessage !== null) {
        alert(errorMessage.textContent);
    }
function checkOtp() {
  $.get("/generate-code", function(data) {
    var code = prompt("OTP 번호를 입력하세요.");
    if (code != null && code.length == 6 && !isNaN(code)) {
      $.post("/otp-auth", {otpCode: code}, function() {
        location.reload();
      });
    } else {
      alert("유효한 OTP 번호를 입력하세요.");
    }
  });
}
