$(document).ready(function() {
	var changeNicknameError = $("#errorData").data("hasChangeNicknameError");
	var failCheckPassword = $("#errorData").data("hasFailCheckPassword");

	if (changeNicknameError || failCheckPassword) {
		$("#inputChangeNickname").show();
	}

	$("#NicknameButton").click(function(event) {
		if ($("#inputChangeNickname").is(":visible")) {
			$("#inputChangeNickname").hide();
		} else {
			$("#inputChangeNickname").show();
			$("#inputChangePassword").hide();
		}
	});
});


$(document).ready(function() {
	var failCheckPasswordChange = $("#errorData").data("hasFailCheckPasswordChange");
	var passwordInputError = $("#errorData").data("hasPasswordInputError");

	if (failCheckPasswordChange || passwordInputError) {
		$("#inputChangePassword").show();
	}

	$("#passwordButton").click(function(event) {
		if ($("#inputChangePassword").is(":visible")) {
			$("#inputChangePassword").hide();
		} else {
			$("#inputChangeNickname").hide();
			$("#inputChangePassword").show();
		}
	});
});

document.getElementById('photoset-Button').addEventListener('click', function(e) {
	e.preventDefault(); // 기본 버튼 동작 중지
	document.getElementById('fileInput').click(); // 파일 입력 필드 트리거
});

// 선택한 파일이 있다면 폼을 자동으로 제출
document.getElementById('fileInput').addEventListener('change', function() {
	if (this.files.length > 0) {
		this.form.submit();
	}
});

document.getElementById('fileInput').addEventListener('change', function() {
    if (this.files.length > 0) {
        var reader = new FileReader();

        reader.onload = function(e) {
            // 미리보기 이미지를 로컬에서 표시
			// 현재의 타임스탬프를 추가하여 캐시 방지
            var newSrc = e.target.result + "#" + new Date().getTime();
            document.querySelector('.fixed-size-image').src = newSrc;
        };
        reader.readAsDataURL(this.files[0]);
    }
});









const textField1 = document.getElementById('textField1');
const textField2 = document.getElementById('textField2');
const textField3 = document.getElementById('textField3');
const passworditem = document.getElementById('passworditem');

const activateButton1 = document.getElementById('activateButton1');
const activateButton2 = document.getElementById('activateButton2');
const updateButton = document.getElementById('updateButton');

// 데이터베이스에서 들고온 값 이곳에...
textField1.value = "닉네임은열자리입니다";
textField2.value = "제공되는비밀번호열자";
textField3.value = "오타방지확인비번열자";

activateButton1.addEventListener('click', () => {
    textField2.style.display = 'inline';

    textField1.disabled = false;
    textField2.disabled = false;
    textField3.disabled = true;
    updateButton.classList.remove('hidden');
    activateButton1.style.display = 'none';
    activateButton2.style.display = 'none';
    passworditem.style.display = 'none';
});

activateButton2.addEventListener('click', () => {
    textField2.style.display = 'inline';
    textField3.style.display = 'inline';
    textField1.disabled = true;
    textField2.disabled = false;
    textField3.disabled = false;
    updateButton.classList.remove('hidden');
    activateButton1.style.display = 'none';
    activateButton2.style.display = 'none';
});

updateButton.addEventListener('click', () => {
    const newText1 = textField1.value;


    // Here you can update the content as needed, e.g. sending data to a server.

    textField1.disabled = true;
    textField2.disabled = true;
    textField3.disabled = true;
    updateButton.classList.add('hidden');
    activateButton1.style.display = 'inline';
    activateButton2.style.display = 'inline';
    textField2.style.display = 'none';
    textField3.style.display = 'none';
    passworditem.style.display = 'inline';
});
