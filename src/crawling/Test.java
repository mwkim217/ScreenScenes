package crawling;

import java.io.File;

public class Test {


	public static void main(String[] args) {
		
//		 try {
//	            GeoApiContext context = new GeoApiContext.Builder()
//	                    .apiKey("AIzaSyA0e22ys-P8tLqDUwqH0tcu-OKfeLUm8GQ") // 여기에 발급받은 API 키를 입력하세요
//	                    .build();
//
//	            GeocodingResult[] results = GeocodingApi.geocode(context, "1300 Ocean Blvd, Isle of Palms, SC 29451, USA").await();
//
//	            if (results != null && results.length > 0) {
//	                System.out.println("Latitude: " + results[0].geometry.location.lat);
//	                System.out.println("Longitude: " + results[0].geometry.location.lng);
//	            } else {
//	                System.out.println("No results found.");
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }

//		Connection conn = null;
//		
//		try {
//			conn = DBUtil.getConnection();
//			String title = "The Magicians";
//			
//			int count = gmap_Movie_DAO.getCountByTitle(conn, title);	
//			
//			System.out.println(count);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		MovieMapsScraper.executeMovieMapsScraper();
//		System.out.println(System.getProperty("user.dir"));
//		File logFile = new File(System.getProperty("user.dir"), "src/log/log.txt");
//		System.out.println(logFile.exists());
	}

}
