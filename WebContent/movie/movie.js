"use strict";

/**
 * navbar variables
 */

const navOpenBtn = document.querySelector("[data-menu-open-btn]");
const navCloseBtn = document.querySelector("[data-menu-close-btn]");
const navbar = document.querySelector("[data-navbar]");
const overlay = document.querySelector("[data-overlay]");

const navElemArr = [navOpenBtn, navCloseBtn, overlay];

for (let i = 0; i < navElemArr.length; i++) {
  navElemArr[i].addEventListener("click", function () {
    navbar.classList.toggle("active");
    overlay.classList.toggle("active");
    document.body.classList.toggle("active");
  });
}

/**
 * header sticky
 */

const header = document.querySelector("[data-header]");

window.addEventListener("scroll", function () {
  window.scrollY >= 10
    ? header.classList.add("active")
    : header.classList.remove("active");
});

/**
 * go top
 */

const goTopBtn = document.querySelector("[data-go-top]");

window.addEventListener("scroll", function () {
  window.scrollY >= 500
    ? goTopBtn.classList.add("active")
    : goTopBtn.classList.remove("active");
});

const moviesNumber = [];
function createMovieCard(title, posterImage, movienumber) {
  const li = document.createElement("li");
  li.innerHTML = `
  <div class="movie-card">
  <figure class="card-banner">
  <img class="card-banner img" src="data:image/jpeg;base64,${posterImage}" alt="${title} movie poster">
  </figure>
  <div class="title-wrapper">
  <h3 class="card-title">${title}</h3>
  </div>
  <div class="card-meta">
  </div>
  <input type = "hidden" id ="movieNumber" value='${movienumber}'/>
  </div>
  `;
  const movieCard = li.querySelector(".movie-card");
  const movieNumber = li.querySelector("#movieNumber");

  movieCard.addEventListener("mouseenter", () => {
    if (movieCard.style.opacity !== "0.5") {
      movieCard.style.transform = "scale(1.1)";
    }
  });

  movieCard.addEventListener("mouseleave", () => {
    movieCard.style.transform = "scale(1)";
  });

  movieCard.addEventListener("click", () => {
    const movieNumberValue = movieNumber.value;
    const index = moviesNumber.indexOf(movieNumberValue);
    const chooseContent = document.querySelector(".choose-content");

    if (moviesNumber.length < 5) {
      if (index === -1) {
        moviesNumber.push(movieNumberValue);
        console.log(moviesNumber);
        movieCard.style.opacity = "0.5";
        const movieNameElem = document.createElement("div");
        movieNameElem.classList.add("selected-movie-name");
        movieNameElem.innerText =
          title.length > 15 ? title.substring(0, 12) + "..." : title;
        movieNameElem.dataset.movieNumber = movieNumberValue;
        // Add delete button inside the label
        const deleteButton = document.createElement("button");
        deleteButton.innerHTML = '<i class="fa-solid fa-eraser"></i>';
        deleteButton.addEventListener("click", (event) => {
          event.stopPropagation();

          const idx = moviesNumber.indexOf(movieNumberValue);
          if (idx !== -1) {
            moviesNumber.splice(idx, 1);
            console.log(moviesNumber);
            movieCard.style.opacity = "1";
            chooseContent.removeChild(movieNameElem);
            updateConfirmButtonState();
          }
        });
        movieNameElem.appendChild(deleteButton);

        // Label click scrolls to the associated movie card
        movieNameElem.addEventListener("click", () => {
          movieCard.scrollIntoView({ behavior: "smooth", block: "center" });
        });

        chooseContent.appendChild(movieNameElem);
      } else {
        moviesNumber.splice(index, 1);
        console.log(moviesNumber);
        movieCard.style.opacity = "1";
        const movieToRemove = document.querySelector(
          `.selected-movie-name[data-movie-number="${movieNumberValue}"]`
        );
        if (movieToRemove) {
          chooseContent.removeChild(movieToRemove);
        }
      }
    } else {
      if (index !== -1) {
        moviesNumber.splice(index, 1);
        movieCard.style.opacity = "1";
        const movieToRemove = document.querySelector(
          `.selected-movie-name[data-movie-number="${movieNumberValue}"]`
        );
        if (movieToRemove) {
          chooseContent.removeChild(movieToRemove);
        }
      } else {
        updateConfirmButtonState();
        alert("최대 5개의 영화를 선택할 수 있습니다.");
      }
    }
    updateConfirmButtonState();
  });

  return li;
}

function updateConfirmButtonState() {
  const confirmButton = document.querySelector(".choose-confirm-btn");
  if (moviesNumber.length < 5) {
    confirmButton.classList.add("disabled");
  } else {
    confirmButton.classList.remove("disabled");
  }
}
const confirmButton = document.querySelector(".choose-confirm-btn");
confirmButton.addEventListener("click", () => {
  confirmMovies();
  
});

function confirmMovies() {
 document.getElementById('selectedMovies').value = JSON.stringify( {movieNumbers : moviesNumber});
 document.getElementById('movieSelectionForm').submit();
}

window.addEventListener("scroll", () => {
  if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
    loadMoreMovies();
  }
});

let loadedMovieIds = [];
function loadMoreMovies() {
  let totalMovies;
  const moviesList = document.querySelector(".movies-list");

  const requestOptions = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      length: moviesList.children.length,
      loadedMovieIds: loadedMovieIds,
    }),
  };

  fetch("http://192.168.0.56:8080/ScreenSceneP/moviechoice", requestOptions)
    .then((response) => {
      if (!response.ok) {
        console.error(
          "Error fetching movies:",
          response.status,
          response.statusText
        );
        return;
      }
      return response.json();
    })
    .then((data) => {
      console.log(data);
      data.forEach(function (movieInfo) {
        if (!loadedMovieIds.includes(movieInfo.movieNumber)) {
          loadedMovieIds.push(movieInfo.movieNumber);
          const movieCard = createMovieCard(
            movieInfo.title,
            movieInfo.poster,
            movieInfo.movieNumber
          );
          totalMovies = movieInfo.count;
          moviesList.appendChild(movieCard);
        }
      });
    })
    .catch((error) => {
      console.error("Error fetching movies:", error);
    });
}

document.addEventListener("DOMContentLoaded", function () {
  loadMoreMovies();
  updateConfirmButtonState();
});

function redirectToLogout() {
        window.location.href = "http://192.168.0.56:8080/ScreenSceneP/logout";
    }
