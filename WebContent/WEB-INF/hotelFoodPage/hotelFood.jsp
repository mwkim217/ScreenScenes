<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Hotel Map</title>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCNge2_byHqG4LIuVu1Vg7RUZRYs3CvjYA&libraries=places"></script>
</head>
<body>
    <div id="map" style="height: 500px;"></div>

    <script>
        const apiKey = 'AIzaSyCNge2_byHqG4LIuVu1Vg7RUZRYs3CvjYA';
        const latitude = <%= request.getAttribute("latitude") %>; // 경도 값으로 대체
        const longitude = <%= request.getAttribute("longitude") %>; // 위도 값으로 대체
        const center = { lat: latitude, lng: longitude };

        const map = new google.maps.Map(document.getElementById('map'), {
            center: center,
            zoom: 15,
        });

        const service = new google.maps.places.PlacesService(map);
        const request = {
            location: center,
            radius: 1000, // 검색 반경 (미터 단위)
            type: '<%= request.getAttribute("search") %>', // lodging은 호텔 등의 숙박 시설을 나타냅니다.
        };

        service.nearbySearch(request, (results, status) => {
            if (status === google.maps.places.PlacesServiceStatus.OK) {
                for (const result of results) {
                    const marker = new google.maps.Marker({
                        position: result.geometry.location,
                        map: map,
                        title: result.name,
                    });
                }
            }
        });
    </script>
</body>
</html>
