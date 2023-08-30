<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>Page Title</title>
    <link rel="stylesheet" href="./login/login.css" />
  </head>

  <body>
    <!-- NAVBAR CREATION -->
    <div class="background">
      <!-- LOGIN FORM CREATION -->
      <div class="container">
        <div class="login-item-section">
          <div class="item login">
            <a class="logo"
              ><img
                src="img/Untitled-1_0000_Group-23-copy.png"
                alt=""
                id="logoImg"
            /></a>
            <div class="text-item">
              <h2>Screen Scenes과 함께해보세요!</h2>
              <p>
                새로운 이색여행도, 좋아하는 영화의 촬영지를 직접 보는것도, 영화
                속 주인공같은 인생인증샷을 남기는것도! Screen Scenes과 함께라면
                가능합니다. 로그인하고 영화에 대한 경로를 찾아보고 해보지못했던
                경험을 느껴보세요
              </p>
              <div class="social-icon">
                <a href="#"><i class="bx bxl-facebook"></i></a>
                <a href="#"><i class="bx bxl-twitter"></i></a>
                <a href="#"><i class="bx bxl-youtube"></i></a>
                <a href="#"><i class="bx bxl-instagram"></i></a>
                <a href="#"><i class="bx bxl-linkedin"></i></a>
              </div>
            </div>
          </div>
          <div class="item register">
            <a class="logo"
              ><img
                src="img/Untitled-1_0000_Group-23-copy.png"
                alt=""
                id="logoImg"
            /></a>
            <div class="text-item2">
              <h2>Screen Scenes의 가입은 간단합니다!</h2>
              <p>
                빠르고 쉬운 회원가입으로, Screen Scenes를 즐겨보세요.
                <br /><br />아이디: 소문자, 대문자, 숫자를 이용하여 5~20자
                <br />비밀번호: 소문자, 대문자, 숫자, 정해진 특수문자들을 모두
                포함 <br />(~ ! @ # $ % ^ & * ( ) _ - + = [ ] , . / <>) 9~20자
                <br />닉네임: 소문자, 대문자, 한글, 숫자 5~20자
              </p>
              <div class="social-icon">
                <a href="#"><i class="bx bxl-facebook"></i></a>
                <a href="#"><i class="bx bxl-twitter"></i></a>
                <a href="#"><i class="bx bxl-youtube"></i></a>
                <a href="#"><i class="bx bxl-instagram"></i></a>
                <a href="#"><i class="bx bxl-linkedin"></i></a>
              </div>
            </div>
          </div>
        </div>

        <div class="login-section">
          <div class="form-box login">
            <form action="" method="post">
              <h2>로그인</h2>
              <input type="hidden" name="form_type" value="loginForm" />

              <div class="input-box">
                <span class="icon"><i class="bx bxs-envelope"></i></span>
                <input type="text" name="id" id="inputId" required />
                <label for="">Id</label>
              </div>
              <div class="input-box">
                <span class="icon"><i class="bx bxs-lock-alt"></i></span>
                <input type="password" name="password" required />
                <label for="">password</label>
              </div>
              <div class="remember-password">
                <label for=""
                  ><input
                    type="checkbox"
                    id="rememberCheck"
                    name="remember"
                    value="check"
                  />아이디 기억하기</label
                >
                <a href="#">비밀번호가 기억나지 않으신가요?</a>
              </div>
              <div>
                <p id="login-error">${ loginError }</p>
              </div>
              <button class="btn">Login</button>
              <div class="create-accunt">
                <p>
                  Create A New Account?
                  <a href="#" class="register-link">Sign Up</a>
                </p>
              </div>
            </form>
          </div>
          <div class="form-box register">
            <form method="post">
              <h2>Sign Up</h2>
              <input type="hidden" name="form_type" value="joinForm" />
              <div class="input-box">
                <span class="icon">
                  <i class="bx bxs-user"></i>
                </span>
                <div class="join-input-box">
                  <input
                    type="text"
                    id="joinNickname"
                    name="joinNickname"
                    value="${ inputNickname }"
                    required
                  />
                  <label for=""> Username </label>
                  <p class="joinError" id="joinNicknameError">
                    ${ joinNicknameError }
                  </p>
                </div>
              </div>

              <div class="input-box">
                <span class="icon"><i class="bx bxs-envelope"></i></span>
                <div class="join-input-box">
                  <input
                    type="text"
                    id="joinId"
                    name="joinId"
                    value="${ inputId }"
                    required
                  />
                  <label for="">Id</label>
                  <p class="joinError" id="joinIdError">${ joinIdError }</p>
                </div>
              </div>
              <div class="input-box">
                <span class="icon"><i class="bx bxs-lock-alt"></i></span>
                <div class="join-input-box">
                  <input
                    type="password"
                    id="joinPassword"
                    name="joinPassword"
                    value="${ joinPassword }"
                    required
                  />
                  <label for="">password</label>
                  <p class="joinError" id="joinPasswordError">
                    ${ joinPasswordError }
                  </p>
                </div>
              </div>
              <div class="input-box">
                <span class="icon"
                  ><i class="bx bxs-lock-alt_reenter"></i
                ></span>
                <input
                  type="password"
                  id="joinPasswordRe"
                  name="joinPasswordRe"
                  value="${ joinPasswordRe }"
                  required
                />
                <label for="">re-enter your password</label>
              </div>
              <button class="btn">Sign Up</button>
              <div class="create-accunt">
                <p>
                  Alraedy Have An Account?
                  <a href="#" class="login-link">Login</a>
                </p>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <!-- SIGN UP FORM CREATION -->
    <script src="./login/login.js"></script>
  </body>
</html>
