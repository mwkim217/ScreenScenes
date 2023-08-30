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


function changeColor(linkId) {
    const links = document.querySelectorAll('.group-link');
    links.forEach((link) => {
        if (link.id === linkId) {
            link.classList.add('clicked');
        } else {
            link.classList.remove('clicked');
        }
    });
}


const indexsec1 = document.querySelector('.index1')
const indexsec2 = document.querySelector('.index2')
const indexsec3 = document.querySelector('.index3')
const indexlink1 = document.querySelector('#group-1')
const indexlink2 = document.querySelector('#group-2')
const indexlink3 = document.querySelector('#group-3')

indexlink1.addEventListener('click', () => {
    indexsec2.classList.remove('active');
    indexsec3.classList.remove('active');
    indexsec1.classList.add('active');
})

indexlink2.addEventListener('click', () => {
    indexsec1.classList.remove('active');
    indexsec3.classList.remove('active');
    indexsec2.classList.add('active');
})
indexlink3.addEventListener('click', () => {
    indexsec1.classList.remove('active');
    indexsec2.classList.remove('active');
    indexsec3.classList.add('active');
})

function showGroup(groupNumber) {
    const sliderElement = document.querySelector(".slider");

    // 선택된 그룹에 따라 슬라이드 위치 결정
    switch (groupNumber) {
        case 1:
            sliderElement.style.transform = "translateX(0%)";
            break;
        case 2:
            sliderElement.style.transform = "translateX(-100%)";
            break;
        case 3:
            sliderElement.style.transform = "translateX(-200%)";
            break;
    }
}

// 페이지 로딩 완료 시 indexsec1에 'active' 클래스 추가
window.addEventListener('DOMContentLoaded', () => {
    indexsec1.classList.add('active');
});

function redirectToLogout() {
        window.location.href = "http://192.168.0.56:8080/ScreenSceneP/logout";
    }