<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="object.Location"%>

<!DOCTYPE html>
<html>
<head>
<title>Simple Map</title>
<script
	src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCNge2_byHqG4LIuVu1Vg7RUZRYs3CvjYA&callback=initMap"></script>
	
<body>
	<div class="slidermap">
		<div id="map1" style="height: 840px; width: 1200px"></div>
	</div>

	<script>
        var latLngListJson = '<%=new ObjectMapper().writeValueAsString(request.getAttribute("latLngList"))%>';
        var firstLocationList = JSON.parse(latLngListJson);

        function addMarkersToMap(locations, map) {
            locations.forEach(location => {
                var marker = new google.maps.Marker({
                    position: {lat: parseFloat(location.latitude), lng: parseFloat(location.longitude)},
                    map: map
                });
            });
        }

        function addPathToMap(locations, map) {
            var pathCoordinates = locations.map(location => {
                return {lat: parseFloat(location.latitude), lng: parseFloat(location.longitude)};
            });

            var pathLine = new google.maps.Polyline({
                path: pathCoordinates,
                geodesic: true,
                strokeColor: '#FF0000',
                strokeOpacity: 1.0,
                strokeWeight: 2
            });
            pathLine.setMap(map);
        }

        function initMap() {
            var map1 = new google.maps.Map(document.getElementById('map1'), {
                zoom: 10,
                center: {lat: parseFloat(firstLocationList[0].latitude), lng: parseFloat(firstLocationList[0].longitude)}
            });
            addMarkersToMap(firstLocationList, map1);
            addPathToMap(firstLocationList, map1);
        }
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCNge2_byHqG4LIuVu1Vg7RUZRYs3CvjYA&callback=initMap"></script>
</body>
</html>
