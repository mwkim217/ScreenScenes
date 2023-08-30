package gmap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import object.Distance;
import object.Location;

/*
영화 5개

가보고싶은 여행지 4개

경로 3개 

30개
4개 
경로 1번 (가보고싶은 여행지 4개를 포함한 촬영지4개로 구성된 경로)
경로 2번 (전체에서 가보고싶은 여행지 4개를 제외한 것 중에서 기준이 랜덤으로 잡힌다 기준에서 가까운곳 4개로 구성된경로)
경로 3번 (전체에서 1번+2번 제외한 것기준이 랜덤으로 잡힌다 기준에서 가까운곳 4개로 구성된경로)


*/

public class DistanceCalculator implements Runnable {
	private Location destination;
	private Location originLocation;
	private String API_KEY;
	private List<Distance> distances = new ArrayList();

	public DistanceCalculator(Location originLocation, Location destination, String API_KEY) {
		this.destination = destination;
		this.originLocation = originLocation;
		this.API_KEY = API_KEY;
	}

	public List<Distance> getDistances() {
		return distances;
	}

	@Override
	public void run() {
		try {

			ObjectMapper mapper = new ObjectMapper();

			String destinationLatitude = String.valueOf(destination.getLatitude());
			String destinationLongitude = String.valueOf(destination.getLongitude());
			String originLatitude = String.valueOf(originLocation.getLatitude());
			String originLongitude = String.valueOf(originLocation.getLongitude());

			if (!destination.equals(originLocation)) {

				URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + originLatitude
						+ "%2C" + originLongitude + "&destinations=" + destinationLatitude + "%2C"
						+ destinationLongitude + "&key=" + API_KEY);

				HttpURLConnection Httpconn = (HttpURLConnection) url.openConnection();
				Httpconn.setRequestMethod("GET");

				int responseCode = Httpconn.getResponseCode();
				String responseMessage = Httpconn.getResponseMessage();

				System.out.println("구글 googleapis 응답 코드:	" + responseCode);
				System.out.println("구글 googleapis 응답 메시지:	" + responseMessage);

				BufferedReader in = new BufferedReader(new InputStreamReader(Httpconn.getInputStream()));
				String inputLine;
				StringBuilder content = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}

				String jsonString = content.toString();
				JsonNode rootNode = mapper.readTree(jsonString);

				JsonNode rowsNode = rootNode.path("rows");

				for (JsonNode row : rowsNode) {
					JsonNode elementsNode = row.path("elements");
					for (JsonNode element : elementsNode) {
						String elementStatus = element.path("status").asText();

//                  Test.counter.incrementAndGet();
						if (elementStatus.equals("OK")) {

							JsonNode distanceNode = element.path("distance");

							int distanceValue = distanceNode.path("value").asInt(); // distance의 value 값을 가져옵니다.

//                     System.out.println("Response Content: " + content.toString());
//                     System.out.println(distanceValue);

							if (distanceValue >= 100) {

								distances.add(new Distance(distanceValue, destination, originLocation));
							}

							System.out.println(distances.size());

						}
					}
				}
				in.close();
				Httpconn.disconnect();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Distance> distanceCalculate(Location originLocation, List<Location> entireSelectedList) {

		int cores = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(cores * 2);

		String API_KEY = "AIzaSyCNge2_byHqG4LIuVu1Vg7RUZRYs3CvjYA";

		List<Distance> allDistances = new ArrayList<>();
		List<DistanceCalculator> workers = new ArrayList<>();

		for (Location destination : entireSelectedList) {
			DistanceCalculator worker = new DistanceCalculator(originLocation, destination, API_KEY);
			workers.add(worker);
			executor.execute(worker);
		}

		executor.shutdown();

		while (!executor.isTerminated()) {
		}

		for (DistanceCalculator worker : workers) {
			allDistances.addAll(worker.getDistances());
		}

		System.out.println("allDistances의 크기:	" + allDistances.size());

//      거리 오름차순으로 정렬
		Collections.sort(allDistances, new Comparator<Distance>() {
			@Override
			public int compare(Distance D1, Distance D2) {
				return Integer.compare(D1.getDistance(), D2.getDistance());
			}
		});

		return allDistances;

	}

	public static List<Location> getFirstLocation(List<Location> firstLocation) {
//      가고싶은 여행지 4곳!!
//      List<gmap_Location> firstLocation = new ArrayList<gmap_Location>();

		return firstLocation;
	}

	public static List<Location> getSecondLocation(List<Location> firstLocation, List<Location> entireSelectedList) {
//      전체 관광지 몇십개
		entireSelectedList.removeAll(firstLocation);
		Random random = new Random();
		// 0부터 entireSelectedList.size() - 1 사이의 랜덤한 정수를 얻습니다.
		int randomIndex = random.nextInt(entireSelectedList.size() - 1);
		Location randomLocation = entireSelectedList.get(randomIndex);
		List<Distance> resultList = distanceCalculate(randomLocation, entireSelectedList);
		System.out.println("두번째 resultList의 크기: " + resultList.size());
//		int count = 0;
//		for(int i=0;i<resultList.size();i++) {
//			if(resultList.get(i).getDistance()>=100) {
//				count++;
//			}
//		}
//		System.out.println("거리가 100이상인 경우의 수: "+count);

		List<Location> secondLocation = new ArrayList<Location>();
		secondLocation.add(randomLocation);
		for (int i = 0; i < 3; i++) {
			Distance distance = resultList.get(i);
			for (int j = 0; j < entireSelectedList.size(); j++) {
				if (entireSelectedList.get(j).equals(distance.getDestination())) {
					secondLocation.add(entireSelectedList.get(j));
				}
			}
		}
		return secondLocation;
	}

	public static List<Location> getThirdLocation(List<Location> firstLocation, List<Location> secondLocation,
			List<Location> entireSelectedList) {

//      전체 관광지-가고싶은곳 4곳-랜덤 4곳

		entireSelectedList.removeAll(firstLocation);
		entireSelectedList.removeAll(secondLocation);

		Random random = new Random();

		int randomIndex = random.nextInt(entireSelectedList.size() - 1);

		Location randomLocation = entireSelectedList.get(randomIndex);

		List<Distance> resultList = distanceCalculate(randomLocation, entireSelectedList);
		System.out.println("세번째 resultList의 크기: " + resultList.size());

//		int count = 0;
//		for(int i=0;i<resultList.size();i++) {
//			if(resultList.get(i).getDistance()>=100) {
//				count++;
//			}
//		}
//		System.out.println("거리가 100이상인 경우의 수: "+count);

		List<Location> thirdLocation = new ArrayList<Location>();

		thirdLocation.add(randomLocation);

		for (int i = 0; i < 3 && i < resultList.size(); i++) {
			Distance distance = resultList.get(i);

			for (int j = 0; j < entireSelectedList.size(); j++) {

				if (entireSelectedList.get(j).equals(distance.getDestination())) {
					thirdLocation.add(entireSelectedList.get(j));

				}

			}

		}

		return thirdLocation;
	}

}