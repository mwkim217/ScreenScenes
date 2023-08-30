<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css"
	href="myPage modify/myPage modify.css" />
<title>MyPage</title>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css'
	rel='stylesheet'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body id="top">
	<!-- 
    - #HEADER
  -->

	<header class="header" data-header>
		<div class="container">
			<div class="overlay" data-overlay></div>

			<a href="./" class="logo">  <img
				src="img/Untitled-1_0000_Group-3-copy.png" alt="Filmlane logo"
				class="logoimg" />
				</a>
			<div class="header-actions">
				<div class="user-info">
					<img class="fixed-size-navi-image" src="data:image/jpeg;base64,${ loggedUserProfileImg }" />
					<p class="navbar-link">${ loggedUserNickname }</p>
				</div>
				<form action="logout" method="get">
					<button class="btn btn-primary">로그아웃</button>
				</form>
			</div>

			<button class="menu-open-btn" data-menu-open-btn>
				<ion-icon name="reorder-two"></ion-icon>
			</button>

			<nav class="navbar" data-navbar>
				<div class="navbar-top">
					<a href="./" class="logo"> </a>

					<button class="menu-close-btn" data-menu-close-btn>
						<ion-icon name="close-outline"></ion-icon>
					</button>
				</div>

				<ul class="navbar-list">
					<li><a href="./movie" class="navbar-link">영화선택</a></li>
					<li><a href="./mypage" class="navbar-link">경로확인</a></li>
				</ul>

				<ul class="navbar-social-list">
					<li><a href="#" class="navbar-social-link"> <ion-icon
								name="logo-twitter"></ion-icon>
					</a></li>
					<li><a href="#" class="navbar-social-link"> <ion-icon
								name="logo-facebook"></ion-icon>
					</a></li>
					<li><a href="#" class="navbar-social-link"> <ion-icon
								name="logo-pinterest"></ion-icon>
					</a></li>
					<li><a href="#" class="navbar-social-link"> <ion-icon
								name="logo-instagram"></ion-icon>
					</a></li>
					<li><a href="#" class="navbar-social-link"> <ion-icon
								name="logo-youtube"></ion-icon>
					</a></li>
				</ul>
			</nav>
		</div>
	</header>

	<main>
<div id="errorData" style="display: none;"
			data-has-change-nickname-error="${ not empty changeNicknameError ? 'true' : 'false' }"
			data-has-fail-check-password="${not empty failCheckPassword ? 'true' : 'false'}"
			data-has-fail-check-password-change="${not empty failCheckPasswordChange ? 'true' : 'false'}"
			data-has-password-input-error="${not empty passwordInputError ? 'true' : 'false'}">
		</div>
		<div class="container">
			<section>
				<div class="profile">
			<div class="profile">
					<form method="post" enctype="multipart/form-data">
						<input type="hidden" value="profilImg" name="form_type"> <input
							type="file" name="uploaded_file" id="fileInput"
							style="display: none;">
						<button id="photoset-Button">
							<i class='bx bxs-cog' style='color: #ffffff'></i>
						</button>
					</form>
					<img class="fixed-size-image" src="data:image/jpeg3;base64,${ loggedUserProfileImg }" />
					<div class="textitemDiv">
						<div id="nickname">
							<p>닉네임변경</p>
						</div>
						<button id="NicknameButton">
							<i class='bx bxs-pencil' style='color: #ffffff'></i>
						</button>
					</div>
					<div class="textitemDiv">
						<div id="password">
							<p>비밀번호변경</p>
						</div>
						<button id="passwordButton">
							<i class='bx bxs-pencil' style='color: #ffffff'></i>
						</button>
					</div>
					<div id="inputChangeNickname" style="display: none;">
						<form method="post">
							<input type="hidden" name="form_type" value="changeNicknameForm">
							<p>닉네임변경</p>
							<input type="text" id="changeNickname" name="changeNickname">
							<p>비밀번호확인</p>
							<input type="password" id="passwordCheck" name="passwordCheck">
							<input type="submit" id="ChangeNicknameBtn">
						</form>
					</div>

					<div id="inputChangePassword" style="display: none;">
						<form method="post">
							<input type="hidden" name="form_type" value="changePasswordForm">
							<p>현재 비밀번호 입력</p>
							<input type="password" id="passwordNow" name="passwordNow">
							<p>변경할 비밀번호 입력</p>
							<input type="password" id="passwordChange" name="passwordChange">
							<p>변경할 비밀번호 입력 확인</p>
							<input type="password" id="passwordChangeRe"
								name="passwordChangeRe"> <input type="submit"
								id="ChangePasswordBtn">
						</form>
					</div>
				</div>
			</section>
		</div>
	</main>


	<!-- 
    - #FOOTER
  -->

	<footer class="footer">
		<div class="footer-top">
			<div class="footer-container">
				<div class="footer-brand-wrapper">
					<a href="./index.html" class="logo"> <img
						src="img/Untitled-1_0000_Group-3-copy.png" alt="Filmlane logo" />
					</a>

					<ul class="footer-list">
						<li><a href="#" class="navbar-link">영화선택</a></li>
						<li><a href="#" class="navbar-link">경로확인</a></li>
					</ul>
				</div>

				<div class="divider"></div>



				<ul class="social-list">
					<li><a href="#" class="social-link"> <ion-icon
								name="logo-facebook"></ion-icon>
					</a></li>

					<li><a href="#" class="social-link"> <ion-icon
								name="logo-twitter"></ion-icon>
					</a></li>

					<li><a href="#" class="social-link"> <ion-icon
								name="logo-pinterest"></ion-icon>
					</a></li>

					<li><a href="#" class="social-link"> <ion-icon
								name="logo-linkedin"></ion-icon>
					</a></li>
				</ul>
			</div>
		</div>
		</div>

		<div class="footer-bottom">
			<div class="container">
				<p class="copyright">
					&copy; 2022 <a href="#">codewithsadee</a>. All Rights Reserved
				</p>

			</div>
		</div>
	</footer>
	<!-- 
    - #GO TO TOP
  -->

	<a href="#top" class="go-top" data-go-top> <ion-icon
			name="chevron-up"></ion-icon>
	</a>
	<script src="jsUtil/header.js"></script>
	<script src="myPage modify/myPage modify.js"></script>
	
	<!-- 
    - ionicon link
  -->
	<script type="module"
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</body>
</html>