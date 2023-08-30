package crawling;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import dbutil.DBUtil;
import gmap.gmap_Movie_DAO;
import log.ElementNotFoundException;
import log.ExceptionHandler;
import log.GeocodingException;
import log.Write;
import object.Count;
import object.MovieTitleAndURL;

public class MovieMapsScraper implements Runnable {

	private static final String BASE_URL = "https://moviemaps.org/movies/";
	private MovieTitleAndURL titleAndURL;

	public MovieMapsScraper(MovieTitleAndURL titleAndURL, Connection conn) {
		this.titleAndURL = titleAndURL;
		this.conn = conn;
	}

	private static GeoApiContext instance;

	public static synchronized GeoApiContext getInstance() {
		if (instance == null) {
			instance = new GeoApiContext.Builder().apiKey("AIzaSyA0e22ys-P8tLqDUwqH0tcu-OKfeLUm8GQ").build();
		}
		return instance;
	}

	Connection conn = null;

	public static List<MovieTitleAndURL> getMovieTitleAndURLList() {
		try {
			List<MovieTitleAndURL> MovieTitleAndURLList = new ArrayList<>();

			org.jsoup.Connection BASE_URLConnection = Jsoup.connect(BASE_URL);

			Response BASE_URLResponse = BASE_URLConnection.execute();
			int BASE_URLStatusCode = BASE_URLResponse.statusCode();

			if (BASE_URLStatusCode == 403) {
				System.out.println("BASE_URLStatusCode:	" + BASE_URLStatusCode);
			} else if (BASE_URLStatusCode == 200) {
				System.out.println("BASE_URLStatusCode:	" + BASE_URLStatusCode);
				System.out.println(BASE_URL + "으로 이동 완료");
				Document document = BASE_URLResponse.parse();

				Elements movieLinks = document.select("a[href^='/movies/']");


				Count.setEntireSize(movieLinks.size());

				for (Element link : movieLinks) {

					String title = link.text();

					String hrefValue = link.attr("href");
					String movieId = hrefValue.split("/")[2];

					String movieUrl = BASE_URL + movieId;

					if (!gmap_Movie_DAO.isDup(title)) {
						MovieTitleAndURLList.add(new MovieTitleAndURL(title, movieUrl));

					} else {
						Count.incrementDupCount();
					}

				}
				return MovieTitleAndURLList;
			} else {
				System.out.println("BASE_URLStatusCode:	" + BASE_URLStatusCode);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void run() {

		String title = titleAndURL.getTitle();
		String movieUrl = titleAndURL.getURL();

		try {

			org.jsoup.Connection movieUrlConnection = Jsoup.connect(movieUrl);
			Response movieUrlResponse = movieUrlConnection.execute();
			int movieUrlStatusCode = movieUrlResponse.statusCode();

			if (movieUrlStatusCode == 403) {
				System.out.println("movieUrlStatusCode:	" + movieUrlStatusCode);
			} else if (movieUrlStatusCode == 200) {
				System.out.println("movieUrlStatusCode:	" + movieUrlStatusCode);
				Document document = movieUrlResponse.parse();

				if (hasMoreSix(document, title)) {

					if(activateInsertMovie(movieUrl, document, title, conn)) {
						activateInsertLocation(movieUrl, document, title, conn);
						
					}


				}

			} else {
				System.out.println("movieUrlStatusCode: " + movieUrlStatusCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ApiException e) {
			e.printStackTrace();
		}finally {

			DBUtil.close(conn);

		}

	}

	public static void executeMovieMapsScraper() {

		Connection conn = null;

		try {

			int cores = Runtime.getRuntime().availableProcessors();
			ExecutorService executor = Executors.newFixedThreadPool(cores * 2);

			List<MovieTitleAndURL> MovieTitleAndURLList = getMovieTitleAndURLList();


			for (MovieTitleAndURL titleAndURL : MovieTitleAndURLList) {
				conn = DBUtil.getConnection();

				MovieMapsScraper worker = new MovieMapsScraper(titleAndURL, conn);
				executor.execute(worker);

			}

			executor.shutdown();

			while (!executor.isTerminated()) {
			}
			
			
			Write.WriteLogResult();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	영화 한개에 대해서 검증하는 메서드 
	public static boolean hasMoreSix(Document document, String title) throws IOException {

		Elements articles = document.select("article");

		int count = 0;

		for (Element article : articles) {

			Element h4Link = article.select("h4 > a[href^='/locations/']").first();

			if (h4Link != null) {

				Elements tinyThumbnailFigureTags = article.select("figure.tiny.thumbnail");

				if (!tinyThumbnailFigureTags.isEmpty()) {
					count++;

				}
			}
		}

		if (count >= 6) {
			return true;
		}
		System.out.println(title + " 은 이미지를 가지고 있는 장소의 갯수가 6개 미만입니다.");
		Count.incrementlessCount();
		return false;
	}

// 영화 하나를 삽입하는  메서드
	public static boolean activateInsertMovie(String movieUrl, Document document, String title, Connection conn)
			throws IOException, InterruptedException {
		
		try {
		Count.incrementtryInsertMovieCount();
		
		

		Element aElement = document.select("figure.small.poster.gallery a").first();
		if (aElement == null) {

			throw new ElementNotFoundException(
					movieUrl + "\nElement aElement = document.select(\"figure.small.poster.gallery a\").first();");
		}

		String hrefposter = aElement.attr("href");

		String posterUrl = "https://moviemaps.org" + hrefposter;

		org.jsoup.Connection posterUrlConnection = Jsoup.connect(posterUrl);
		Response posterUrlResponse = posterUrlConnection.execute();
		int posterUrlStatusCode = posterUrlResponse.statusCode();

		if (posterUrlStatusCode == 200) {
			System.out.println("posterUrlStatusCode:	" + posterUrlStatusCode);

			Document posterdocument = posterUrlResponse.parse();

			Element posterElement = posterdocument.select("figure img").first();
			if (posterElement == null) {

				throw new ElementNotFoundException(
						posterUrl + "\nElement posterElement = posterdocument.select(\"figure img\").first();");
			}

			String posterimgUrl = posterElement.attr("abs:src");

			URL posterimgURL = new URL(posterimgUrl);

			URLConnection posterimgURLConnection = posterimgURL.openConnection();

			if (posterimgURLConnection instanceof HttpURLConnection) {
				HttpURLConnection posterimgURLHttpConnection = (HttpURLConnection) posterimgURLConnection;
				int posterimgURLStatusCode = posterimgURLHttpConnection.getResponseCode();
				System.out.println("posterimgURLStatusCode:	" + posterimgURLStatusCode);

				if (posterimgURLStatusCode != 200) {
					System.out.println("posterimgURLStatusCode:	" + posterimgURLStatusCode);
					System.out.println("Error message:	" + posterimgURLHttpConnection.getResponseMessage());
				}
				InputStream posterinputStream = posterimgURLConnection.getInputStream();

				gmap_Movie_DAO.insertIntoMovie(title, posterinputStream, conn);

				posterinputStream.close();

				System.out.println(title + " movie 테이블 추가 성공");

				Count.incrementInsertMovieCount();
				return true;
			}

		} else {
			System.out.println("posterUrlStatusCode:	" + posterUrlStatusCode);
		}
		}catch (ElementNotFoundException e) {
			ExceptionHandler.ElementNotFoundExceptionToFile(e);
			e.printStackTrace();
		}
		return false;
	}

//  한개의 영화에 딸린 장소들을 삽입하는 메서드 

	public static void activateInsertLocation(String movieUrl, Document document, String title, Connection conn)
			throws IOException, InterruptedException, ApiException{
			
			
		
		

		int movie_no = gmap_Movie_DAO.getMovie_noWithTitle(title, conn);
		Elements articles = document.select("article");


		for(int i=1;i<=articles.size();i++) {
			try {
			Element article = articles.get(i-1);
			if(article == null) {
				throw new ElementNotFoundException(
						movieUrl + "\nElement article = articles.get(i);\n"
								+ i + "번째 article IN "+articles.size());
				
			}
			int count = gmap_Movie_DAO.getCountByTitle(conn, title);

			if (count < 30) {

				Count.incrementTryInsertLocationCount();
				System.out.println(title + " location 테이블 추가 시도");
				Element h4Link = article.select("h4 > a[href^='/locations/']").first();
				if (h4Link == null) {
					throw new ElementNotFoundException(
							movieUrl + "\nElement h4Link = article.select(\"h4 > a[href^='/locations/']\").first();\n"
									+ i +  "번째 article IN "+articles.size());

				}
				Element tinyThumbnailInFigureTag = article.selectFirst("figure.tiny.thumbnail");

				if (tinyThumbnailInFigureTag == null) {
					throw new ElementNotFoundException(movieUrl
							+ "\nElement tinyThumbnailInFigureTag = article.selectFirst(\"figure.tiny.thumbnail\");\n"
							+ i +  "번째 article IN "+articles.size());

				}

				Element a_tagInTinythumbnail = tinyThumbnailInFigureTag.selectFirst("a");

				if (a_tagInTinythumbnail == null) {

					throw new ElementNotFoundException(
							movieUrl + "\nElement a_tagInTinythumbnail = tinyThumbnailInFigureTag.selectFirst(\"a\");"
									+ "\n" + i +  "번째 article IN "+articles.size());

				}

				String imagehrefInA_tag = a_tagInTinythumbnail.attr("href");
				String placeImageUrl = "https://moviemaps.org" + imagehrefInA_tag;

				org.jsoup.Connection placeImageUrlConnection = Jsoup.connect(placeImageUrl);

				Response placeImageresponse = placeImageUrlConnection.execute();

				int placeImageStatusCode = placeImageresponse.statusCode();

				if (placeImageStatusCode == 200) {
					System.out.println("placeImageStatusCode:	" + placeImageStatusCode);
					Document placeImagedocument = placeImageresponse.parse();
					Element movieLink = placeImagedocument.selectFirst("section#description a[href^=/locations/]");

					if (movieLink == null) {
						throw new ElementNotFoundException(placeImageUrl + "\nElement movieLink = placeImagedocument\n"
								+ "									.selectFirst(\"section#description a[href^=/locations/]\");"
								+ "\n" + i +  "번째 article IN "+articles.size());

					}
					String placeDetailhref = movieLink.attr("href");
					String placeDetailUrl = "https://moviemaps.org" + placeDetailhref;

					org.jsoup.Connection placeDetailUrlConnection = Jsoup.connect(placeDetailUrl);
					Response placeDetailUrlresponse = placeDetailUrlConnection.execute();

					int placeDetailUrlStatusCode = placeDetailUrlresponse.statusCode();
					if (placeDetailUrlStatusCode == 200) {
						System.out.println("placeDetailUrlStatusCode:	" + placeDetailUrlStatusCode);
						Document placeDetaildocument = placeDetailUrlresponse.parse();
						Element addressElement = placeDetaildocument.select("address").first();

						if (addressElement == null) {
							throw new ElementNotFoundException(placeDetailUrl
									+ "\nElement addressElement = placeDetaildocument.select(\"address\").first();"
									+ "\n" + i +  "번째 article IN "+articles.size());

						}
						String address = addressElement.text();

						GeoApiContext context = getInstance();

						GeocodingResult[] results = GeocodingApi.geocode(context, address).await();

						if (results == null || results.length == 0) {

							throw new GeocodingException(placeDetailUrl
									+ "\nGeocodingResult[] results = GeocodingApi.geocode(context, address).await();"
									+ "\n" + i +  "번째 article IN "+articles.size());
						}
						System.out.println("Latitude: " + results[0].geometry.location.lat);
						System.out.println("Longitude: " + results[0].geometry.location.lng);
						Double lat = results[0].geometry.location.lat;
						Double lng = results[0].geometry.location.lng;

						Element imgElement = placeImagedocument.select("figure img").first();

						if (imgElement == null) {
							throw new ElementNotFoundException(placeImageUrl
									+ "\nElement imgElement = placeImagedocument.select(\"figure img\").first();" + "\n"
									+ i +  "번째 article IN "+articles.size());

						}
						String imgUrl = imgElement.attr("abs:src");
						URL imgURL = new URL(imgUrl);

						URLConnection imgUrlConnection = imgURL.openConnection();

						if (imgUrlConnection instanceof HttpURLConnection) {
							HttpURLConnection imgUrlHttpConnection = (HttpURLConnection) imgUrlConnection;
							int imgUrlStatusCode = imgUrlHttpConnection.getResponseCode();
							System.out.println("imgUrlStatusCode:	" + imgUrlStatusCode);

							if (imgUrlStatusCode != 200) {

								System.out.println("imgUrlStatusCode:	" + imgUrlStatusCode);
								System.out.println("Error message:	" + imgUrlHttpConnection.getResponseMessage());
							}

						}

						InputStream inputStream = imgUrlConnection.getInputStream();

						try {
							int result = gmap_Movie_DAO.insertIntoLocation(movie_no, address, lat, lng, inputStream,
									conn);
						} catch (SQLIntegrityConstraintViolationException e) {
							System.out.println(movie_no);
							e.printStackTrace();
						}

						System.out.println(title + " location 테이블 추가 성공");

						Count.incrementLocationInsertCount();

						inputStream.close();
					} else {
						System.out.println("placeDetailUrlStatusCode:	" + placeDetailUrlStatusCode);
					}
				} else {
					System.out.println("placeImageStatusCode:	" + placeImageStatusCode);
				}

			} else {
				return;
			}
			
			}catch (ElementNotFoundException e) {
				ExceptionHandler.ElementNotFoundExceptionToFile(e);
				e.printStackTrace();
			} catch (GeocodingException e) {
				ExceptionHandler.GeocodingExceptionToFile(e);
				e.printStackTrace();
			}
			
		}
	}
}
