<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>ScreenScenes - MovieCollections</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
    />
    <!-- 
    - custom css link
  -->
    <link rel="stylesheet" type="text/css" href="movie/movie.css" />

    <!-- 
    - google font link
  -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap"
      rel="stylesheet"
    />
  </head>

  <body id="top">
    <!-- 
    - #HEADER
  -->
    <header class="header" data-header>
      <div class="container">
        <div class="overlay" data-overlay></div>
        <a href="./" class="logo">
          <img src="img/Untitled-1_0000_Group-3-copy.png" class="logoimg"
        /></a>
        <div class="header-actions">
          <div class="user-info">
            <img
              class="fixed-size-navi-image"
              src="data:image/jpeg;base64,${ loggedUserProfileImg }"
            />
            <p class="navbar-link">${ loggedUserNickname }</p>
          </div>
          <button class="btn btn-primary" onclick="redirectToLogout()">로그아웃</button>
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
            <li>
              <a href="#" class="navbar-social-link">
                <ion-icon name="logo-twitter"></ion-icon>
              </a>
            </li>
            <li>
              <a href="#" class="navbar-social-link">
                <ion-icon name="logo-facebook"></ion-icon>
              </a>
            </li>
            <li>
              <a href="#" class="navbar-social-link">
                <ion-icon name="logo-pinterest"></ion-icon>
              </a>
            </li>
            <li>
              <a href="#" class="navbar-social-link">
                <ion-icon name="logo-instagram"></ion-icon>
              </a>
            </li>
            <li>
              <a href="#" class="navbar-social-link">
                <ion-icon name="logo-youtube"></ion-icon>
              </a>
            </li>
          </ul>
        </nav>
      </div>
    </header>

    <main>
      <article>
        <!-- 
        - #HERO
      -->
        <form
          id="movieSelectionForm"
          action="http://192.168.0.56:8080/ScreenSceneP/flow"
          method="POST"
        >
          <input
            type="hidden"
            id="selectedMovies"
            name="movieNumbers"
            value=""
          />
          <section class="choose">
            <div class="choose-container">
              <div class="choose-content"></div>
              <button class="choose-confirm-btn" type="submit">
                <i class="fa-regular fa-circle-check"></i>
              </button>
            </div>
          </section>
        </form>

        <section>
          <!-- <section class="top-rated"> -->
          <div class="container">
            <ul class="movies-list"></ul>
          </div>
        </section>

        <!-- 
        - #CTA
      -->

        <section class="cta">
          <div class="cta-container"></div>
        </section>
      </article>
    </main>

    <!-- 
    - #FOOTER
  -->

    <footer class="footer">
      <div class="footer-top">
        <div class="footer-container">
          <div class="footer-brand-wrapper">
            <a href="./index.html" class="logo">
              <img
                src="img/Untitled-1_0000_Group-3-copy.png"
                alt="Filmlane logo"
              />
            </a>

            <ul class="footer-list">
              <li><a href="#" class="navbar-link">영화선택</a></li>
              <li><a href="#" class="navbar-link">경로확인</a></li>
            </ul>
          </div>

          <div class="divider"></div>

          <ul class="social-list">
            <li>
              <a href="#" class="social-link">
                <ion-icon name="logo-facebook"></ion-icon>
              </a>
            </li>

            <li>
              <a href="#" class="social-link">
                <ion-icon name="logo-twitter"></ion-icon>
              </a>
            </li>

            <li>
              <a href="#" class="social-link">
                <ion-icon name="logo-pinterest"></ion-icon>
              </a>
            </li>

            <li>
              <a href="#" class="social-link">
                <ion-icon name="logo-linkedin"></ion-icon>
              </a>
            </li>
          </ul>
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

    <a href="#top" class="go-top" data-go-top>
      <ion-icon name="chevron-up"></ion-icon>
    </a>

    <!-- 
    - custom js link
  -->
    <script src="./movie/movie.js"></script>

    <!-- 
    - ionicon link
  -->

    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
  </body>
</html>
