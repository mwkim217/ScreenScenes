document.addEventListener('DOMContentLoaded', () => {
// 오류 메시지가 있는지 확인
var joinNicknameError = document.querySelector('#joinNicknameError').textContent.trim();
var joinIdError = document.querySelector('#joinIdError').textContent.trim();
var joinPasswordError = document.querySelector('#joinPasswordError').textContent.trim();

const loginsec = document.querySelector('.login-section')
const loginitemsec = document.querySelector('.login-item-section')
const loginlink = document.querySelector('.login-link')
const registerlink = document.querySelector('.register-link')

// 만약 오류 메시지가 있으면 .login-section에 active 클래스 추가
if (joinNicknameError || joinIdError || joinPasswordError) {
        loginsec.classList.add('active');
		loginitemsec.classList.add('active');
}

registerlink.addEventListener('click', () => {
    loginsec.classList.add('active')
    loginitemsec.classList.add('active')
})

loginlink.addEventListener('click', () => {
    loginsec.classList.remove('active')
    loginitemsec.classList.remove('active')
})

});


window.onload = function() {
    var idValue = getCookie("remember");
    if (idValue) {
        document.getElementById("inputId").value = idValue;
        document.getElementById("rememberCheck").checked = true;
    }

    document.getElementById("loginButton").addEventListener("click", function() {
        if(!document.getElementById("rememberCheck").checked) {
            deleteCookie("remember");
        }
    });
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

