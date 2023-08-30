let isAnimating = false;
let targetScrollTop = 0;
let lastScrollTime = 0;
const SCROLL_DELAY = 100;

function getCurrentSection() {
    const sections = document.querySelectorAll('section');
    for (let section of sections) {
        const sectionTop = section.offsetTop;
        const sectionBottom = sectionTop + section.offsetHeight;
        if (window.scrollY >= sectionTop && window.scrollY < sectionBottom) {
            return section;
        }
    }
    return null;
}

function handleWheel(event) {
    const currentTime = new Date().getTime();

    if (isAnimating || (currentTime - lastScrollTime) < SCROLL_DELAY) {
        event.preventDefault();
        return;
    }

    lastScrollTime = currentTime;

    event.preventDefault();
    
    const currentSection = getCurrentSection();
    if (!currentSection) return;

    let nextSection;
    if (event.deltaY > 0) {
        nextSection = currentSection.nextElementSibling;
    } else {
        nextSection = currentSection.previousElementSibling;
    }

    if (nextSection && nextSection.tagName === 'SECTION') {
        targetScrollTop = nextSection.offsetTop;
        startSmoothScrollAnimation();
    }
}


// 부드러운 스크롤 애니메이션 시작 함수
function startSmoothScrollAnimation() {
    if (isAnimating) {
        return; // 이미 애니메이션 동작 중이면 무시합니다.
    }

    isAnimating = true;

    smoothScrollTo(targetScrollTop, () => {
        isAnimating = false; // 애니메이션 종료 후 플래그를 해제합니다.
    });
}

// 부드러운 스크롤 애니메이션 함수
function smoothScrollTo(targetPosition, onComplete) {
    const startPosition = window.scrollY;
    const duration = 500; // 애니메이션 기간 (밀리초)
    const startTime = performance.now();

    const scrollStep = (timestamp) => {
        const progress = Math.min((timestamp - startTime) / duration, 1);
        const easedProgress = easeInOutQuad(progress);
        const distanceToScroll = targetPosition - startPosition;
        const currentPosition = startPosition + distanceToScroll * easedProgress;

        window.scrollTo(0, currentPosition);

        if (progress < 1) {
            requestAnimationFrame(scrollStep);
        } else {
            onComplete(); // 애니메이션 종료 후 콜백 함수 호출
        }
    };

    requestAnimationFrame(scrollStep);
}

// 이 함수는 Quad ease-in-out 함수를 적용하여 애니메이션을 자연스럽게 만듭니다.
function easeInOutQuad(t) {
    return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
}

// 스크롤 휠 이벤트를 등록합니다.
window.addEventListener("wheel", handleWheel);