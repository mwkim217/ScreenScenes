<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>Page Title</title>
    <link rel="stylesheet" type="text/css" href="filmingLocation/filmingLocation.css"/>
    <script src="https://unpkg.com/boxicons@2.1.4/dist/boxicons.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCNge2_byHqG4LIuVu1Vg7RUZRYs3CvjYA"></script>
  </head>

  <body id="top">
    <!-- 
    - #HEADER
  -->
    <header class="header" data-header>
      <div class="container">
        <div class="overlay" data-overlay></div>

        <a href="./" class="logo">
          <img
            src="img/Untitled-1_0000_Group-3-copy.png"
            alt="Filmlane logo"
            class="logoimg"
          />
        </a>
        <div class="header-actions">
          <div class="user-info">
            <p class="navbar-link">
              <img class="fixed-size-navi-image" src="data:image/jpeg;base64,${ loggedUserProfileImg }" />
            </p>
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
        <section class="movie-view">
          <div class="movie-view-container">
            <c:forEach var="entry" items="${postersMap}">
              <div class="movie-container">
                <div class="poster-container">
                  <img
                    src="data:image/jpeg;base64,${entry.value}"
                    class="moviePoster"
                  />
                </div>
                <div class="movies-list-name">
                  <p>${entry.key}</p>
                </div>
                <div class="locations-container">
                  <!-- 문자열로 키를 변환하여 사용 -->
                  <div class="movie-ticket">
                    <c:forEach
                      var="locationObject"
                      items="${movieLocations[entry.key]}"
                    >
                      <c:forEach
                        var="imgData"
                        items="${locationObject.imageData}"
                        varStatus="status"
                      >
                        <div
                          class="item"
                          data-location_no="${locationObject.location_no}"
                        >
                          <img
                            src="data:image/jpeg;base64,${imgData}"
                            class="movieimg"
                          />
                          <div class="description">
                            <p class="address">
                              ${locationObject.addressData[status.index]}
                            </p>
                          </div>
                        </div>
                      </c:forEach>
                    </c:forEach>
                  </div>
                </div>
              </div>
            </c:forEach>
            <form action="./selectpath" method="POST" id="form">
              <!--  <input type="hidden" id="movieNumber" name ="movieNumber"/> -->
              <c:forEach var="entry" items="${movieNumber}">
                <input type="hidden" name="movieNumber" value="${entry}" />
              </c:forEach>
              <input
                type="hidden"
                id="selectedLocationNos"
                name="selectedLocationNos"
              />
              <input
                type="submit"
                class="btn btn-primary nextPage"
                value="경로 확인하기"
                disabled="true"
              />
            </form>
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
            <a href="./index.html" class="logo"> </a>

            <ul class="footer-list">
              <li><a href="#" class="navbar-link">영화선택</a></li>

              <li><a href="#" class="navbar-link">경로확인</a></li>
            </ul>
          </div>

          <div class="divider"></div>

          <div class="quicklink-wrapper">
            <ul class="quicklink-list">
              <li><a href="#" class="quicklink-link"></a></li>

              <li><a href="#" class="quicklink-link"></a></li>

              <li><a href="#" class="quicklink-link"></a></li>

              <li><a href="#" class="quicklink-link"></a></li>
            </ul>

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
    <div id="mapContainer" class="map-collapsed"></div>
    <button id="toggleMap">Expand Map</button>

    <script src="filmingLocation/filmingLocation.js"></script>

    <script
      type="module"
      src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"
    ></script>
    <script
      nomodule
      src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"
    ></script>
  </body>
</html>