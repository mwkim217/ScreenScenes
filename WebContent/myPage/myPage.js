document.addEventListener('DOMContentLoaded', function() {
    
    // .bxs-pencil 버튼에 대한 이벤트 리스너 추가
    document.querySelectorAll('.bxs-pencil').forEach(function(button) {
        button.addEventListener('click', function(event) {
            event.preventDefault();

            localStorage.setItem('scrollPosition', window.scrollY);

            const pathNo = event.currentTarget.getAttribute('data-pathno');
            const pathInput = document.getElementById('pathNameInput' + pathNo);

            if (pathInput.style.display === 'inline-block') {
                document.getElementById('pathForm' + pathNo).submit();
            } else {
                // 만약 'pathText' 요소가 존재하지 않는다면, 아래 줄은 오류를 발생시킬 것입니다.
                // 이 부분의 존재 여부를 확인하거나, try-catch로 오류 처리를 추가할 수 있습니다.
                document.getElementById('pathText' + pathNo).style.display = 'none';
                pathInput.style.display = 'inline-block';
                pathInput.focus();
            }
        });
    });

    // .button-x-image 버튼에 대한 이벤트 리스너 추가
    document.querySelectorAll('.button-x-image').forEach(function(button) {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            
            localStorage.setItem('scrollPosition', window.scrollY);
            
            const form = button.closest('form');
            if (form) {
                form.submit();
            }
        });
    });

    // 다른 버튼들에 대한 이벤트 리스너 추가
    document.querySelectorAll('button:not(.bxs-pencil):not(.button-x-image)').forEach(function(button) {
        button.addEventListener('click', function() {
            localStorage.removeItem('scrollPosition');
        });
    });

});

// 페이지의 모든 리소스가 로드된 후에 실행됩니다.
window.addEventListener('load', function() {
    const savedScrollPosition = localStorage.getItem('scrollPosition');

    if (savedScrollPosition) {
        window.scrollTo(0, parseInt(savedScrollPosition, 10));
    }
});

function redirectToLogout() {
    window.location.href = "http://localhost:8080/ScreenSceneP/logout";
}