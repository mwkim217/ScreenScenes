const items = document.querySelectorAll(".item");
const selectedLocationNos = [];
const markersMap = {};

document.querySelector('#form').addEventListener('submit', (e) => {
    if (selectedLocationNos.length !== 4) {
        e.preventDefault(); // 폼 제출을 막음
        alert("영화 촬영지를 4개 선택해주세요.");
        return;
    }
    document.getElementById("selectedLocationNos").value = selectedLocationNos.join(',');
});

items.forEach((item) => {
    item.addEventListener("click", () => {
        const locationNo = item.getAttribute("data-location_no");

        if (selectedLocationNos.includes(locationNo)) {
            // 이미 선택된 촬영지인 경우 선택 해제
            const index = selectedLocationNos.indexOf(locationNo);
            if (index !== -1) {
                selectedLocationNos.splice(index, 1);
            }
            item.classList.remove("selected");
            
            if (markersMap[locationNo]) {
        markersMap[locationNo].setMap(null);
        delete markersMap[locationNo];
    }
            
            
        } else if (selectedLocationNos.length < 4) {
            // 선택되지 않은 촬영지인 경우 선택
          
            selectedLocationNos.push(locationNo);
            item.classList.add("selected");
        }
	if (item.classList.contains("selected")) {
            // 선택된 촬영지의 location_no를 바탕으로 서블릿에 요청
            fetch(`http://192.168.0.56:8080/ScreenSceneP/mapchoice`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ location_no: locationNo }) // location_no를 JSON 형식으로 보냅니다.
            })
            .then(response => response.json())
            .then(data => {
				console.log(data);
                // 응답에서 위도와 경도를 가져와 지도에 마커를 추가합니다.
                data.forEach(location => {
                    addMarkerToMap(location.filminglocationlat, location.filminglocationlng,locationNo);
                });
            })
            .catch(error => console.error('Error:', error));
        }
    });
        const selectedLocationsInput = document.getElementById("selectedLocationNos");
        selectedLocationsInput.value = selectedLocationNos.join(',');

        SubmitButtonState();
    });

function SubmitButtonState() {
    const submitButton = document.querySelector(".nextPage");
        submitButton.removeAttribute("disabled");
}
let map;

function initMap() {
    const center = { lat: 37.5665, lng: 126.9780 }; // 서울의 기본 좌표
    map = new google.maps.Map(document.getElementById("mapContainer"), {
        zoom: 2,
        center: center
    });
    document.getElementById("mapContainer").style.display = "none";
    
}
function addMarkerToMap(lat, lng , locationNo) {
    const marker = new google.maps.Marker({
        position: { lat, lng },
        map: map
    });
     markersMap[locationNo] = marker;
}
window.onload = initMap;


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

const mapContainer = document.getElementById("mapContainer");
const toggleMapBtn = document.getElementById("toggleMap");

toggleMapBtn.addEventListener('click', function() {
    const mapContainer = document.getElementById("mapContainer");
    if (mapContainer.style.display === "none" || !mapContainer.style.display) {
        mapContainer.style.display = "block";
        toggleMapBtn.textContent = "Collapse Map";
    } else {
        mapContainer.style.display = "none";
        toggleMapBtn.textContent = "Expand Map";
    }
});

function redirectToLogout() {
        window.location.href = "http://192.168.0.56:8080/ScreenSceneP/logout";
    }