<%@page import="com.google.gson.Gson"%>
<%@page import="object.MyPath"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="myPage/myPage.css" />
<title>MyPage</title>
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
<script src="https://unpkg.com/boxicons@2.1.4/dist/boxicons.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0e22ys-P8tLqDUwqH0tcu-OKfeLUm8GQ"></script>
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>

<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
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
		<div class="container">

			<section>
				<div class="profile">
					<img class="fixed-size-image" src="data:image/jpeg;base64,${ loggedUserProfileImg }" />
					<div id="nicknameDiv">
						<div id="nickname">
							<p>${ loggedUserNickname }</p>
						</div>
						<form method="post">
							<button id="NicknameButton">
								<i class='bx bxs-cog' style='color: #ffffff'></i>
							</button>
						</form>
					</div>
				</div>
			</section>
			<section>
				<div class="barDiv">
					<div id="barTextDiv">
						<p>나의경로보기</p>
					</div>
				</div>
				<c:forEach var="path" items="${ list }">
					<div class="myRoot-sc">
						<div class="myRoot">
							<form method="post">
								<div class="cancelBtn">
									<input type="hidden" value="${ path.pathNo }" name="pathPk"> 
									<input type="hidden" value="cancel" name="input_type">
									<button class="button-x-image"></button>
								</div>
							</form>
							<div class="detailRoot" >
								<form action="./mypath" method="post">
									 <input type="hidden" name="pathNo" value="${path.pathNo}" >
            							<button class="rootImg" onclick="setPathValue(${path.pathNo})"></button>
								</form>
								<div class="detailRootLeft">
									<div class="detailRootTitle">
										<p class="detailRootBigText">
											<span id="pathText${path.pathNo}">${path.rootName}</span>
										<form id="pathForm${path.pathNo}" method="post">
											<input type="hidden" value="${path.pathNo}" name="pathPk"> 
											<input type="hidden" value="inputName" name="input_type"> 
											<input type="text" name="pathName" id="pathNameInput${path.pathNo}" style="display: none;" value="${path.rootName}">
											<button class="bx bxs-pencil" data-pathno="${path.pathNo}"></button>
										</form>
										</p>
									</div>
									<div class="detailRootAdressDiv">
										<div class="searchDiv">
											<p class="detailRootAdress">${ path.locationAddress1.address }</p>
											<form action="./hotelfood">
												<input type="hidden" name="locationName" value="${ path.locationAddress1.address }">
											<div class="markDiv">
												<button type="submit" class="hotel" name="search" value="lodging"><img class="markImg" src="img/호텔.png"></button>
												<button type="submit" class="food" name="search" value="restaurant"><img class="markImg" src="img/레스토랑.png"></button>
											</div>
											</form>
										</div>

										<div class="searchDiv">
											<p class="detailRootAdress">${ path.locationAddress2.address }</p>
											<form action="./hotelfood">
												<input type="hidden" name="locationName" value="${ path.locationAddress2.address }">
											<div class="markDiv">
												<button type="submit" class="hotel" name="search" value="lodging"><img class="markImg" src="img/호텔.png"></button>
												<button type="submit" class="food" name="search" value="restaurant"><img class="markImg" src="img/레스토랑.png"></button>
											</div>
											</form>
										</div>
										<div class="searchDiv">
											<p class="detailRootAdress">${ path.locationAddress3.address }</p>
											<form action="./hotelfood">
												<input type="hidden" name="locationName" value="${ path.locationAddress3.address }">
											<div class="markDiv">
												<button type="submit" class="hotel" name="search" value="lodging"><img class="markImg" src="img/호텔.png"></button>
												<button type="submit" class="food" name="search" value="restaurant"><img class="markImg" src="img/레스토랑.png"></button>
											</div>
											</form>
										</div>
										<div class="searchDiv">
											<p class="detailRootAdress">${ path.locationAddress4.address }</p>
											<form action="./hotelfood">
												<input type="hidden" name="locationName" value="${ path.locationAddress4.address }">
											<div class="markDiv">
												<button type="submit" class="hotel" name="search" value="lodging"><img class="markImg" src="img/호텔.png"></button>
												<button type="submit" class="food" name="search" value="restaurant"><img class="markImg" src="img/레스토랑.png"></button>
											</div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
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
	
		<!-- 
    - ionicon link
  -->
	<script type="module"
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
	<script src="jsUtil/header.js"></script>
	<script src="myPage/myPage.js"></script>

</body>
</html>