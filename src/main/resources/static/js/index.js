const loginBtn = document.getElementById('login-btn');
const signupBtn = document.getElementById('signup-btn');
const logoutBtn = document.getElementById('logout-btn');
const mypageBtn = document.getElementById('mypage-btn');

const user = localStorage.getItem('user');

if (user) {
  // 로그인된 경우
  loginBtn.style.display = 'none';
  signupBtn.style.display = 'none';
  logoutBtn.style.display = 'block';
  mypageBtn.style.display = 'block';
} else {
  // 로그인되지 않은 경우
  loginBtn.style.display = 'block';
  signupBtn.style.display = 'block';
  logoutBtn.style.display = 'none';
  mypageBtn.style.display = 'none';
}