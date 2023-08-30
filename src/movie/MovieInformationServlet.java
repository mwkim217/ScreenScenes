package movie;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import object.ImgLocationObject;
import object.Location;

@WebServlet("/flow")
public class MovieInformationServlet extends HttpServlet {
    MovieInformationDAO movieDao = new MovieInformationDAO(); // movieDao 객체 생성

    private void MoviesPoster(HttpServletRequest req, List<Integer> movieNumbers) {
	List<String> encodedPosters = new ArrayList<>();
	Map<String, String> postersMap = new HashMap<>();

	for (Integer movieNumber : movieNumbers) {
	    Blob posterBlob = movieDao.selectOneMovie(movieNumber);
	    String title = movieDao.selectOneTitle(movieNumber);
	    if (posterBlob != null) {
		try {
		    byte[] posterBytes = posterBlob.getBytes(1, (int) posterBlob.length());
		    String encodedPoster = Base64.getEncoder().encodeToString(posterBytes);
		    postersMap.put(title, encodedPoster);
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	}
	req.setAttribute("movieNumber", movieNumbers);
	req.setAttribute("postersMap", postersMap);
    }

    // 각 영화당 촬영지,이미지
    private void MoviesAddress(HttpServletRequest req, List<Integer> movieNumbers) throws SQLException, IOException {
	Map<String, List<ImgLocationObject>> movieLocationsMap = new HashMap<>();

	for (Integer movieNumber : movieNumbers) {
	    List<ImgLocationObject> locationsList = new ArrayList<>();
	    List<Location> locations = movieDao.selectLocationList(movieNumber);
	    String title = null;

	    for (Location location : locations) {
		title = movieDao.selectOneTitle(movieNumber);
		List<String> singleImageData = new ArrayList<>();
		List<String> singleAddressData = new ArrayList<>();
		List<Integer> location_no = new ArrayList<>();

		Blob blob = location.getImage();
		InputStream inputStream = blob.getBinaryStream();
		byte[] bytes = new byte[(int) blob.length()];
		inputStream.read(bytes);

		singleImageData.add(Base64.getEncoder().encodeToString(bytes));
		singleAddressData.add(location.getAddress());
		location_no.add(location.getLocation_no());

		locationsList.add(new ImgLocationObject(singleImageData, singleAddressData, location_no));
	    }
//			System.out.println("Movie Number: " + movieNumber + ", Locations: " + locationsList);
	    movieLocationsMap.put(title, locationsList);
	}

	req.setAttribute("movieLocations", movieLocationsMap);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String movieNumbersJson = req.getParameter("movieNumbers");

	if (movieNumbersJson != null) {
	    HttpSession session = req.getSession();
	    session.setAttribute("movieNumbers", movieNumbersJson);
	} else {
	    HttpSession session = req.getSession();
	    movieNumbersJson = (String) session.getAttribute("movieNumbers");
	}
	ObjectMapper mapper = new ObjectMapper();

	try {
	    JsonNode rootNode = mapper.readTree(movieNumbersJson);
	    JsonNode movieNumbersNode = rootNode.get("movieNumbers");
	    List<Integer> movieNumbers = new ArrayList<>();

	    if (movieNumbersNode.isArray()) {
		for (JsonNode movieNumberNode : movieNumbersNode) {
		    movieNumbers.add(movieNumberNode.asInt());
		}
	    }
	    System.out.println(movieNumbers);
	    MoviesPoster(req, movieNumbers); // 이 부분을 추가합니다.
	    MoviesAddress(req, movieNumbers);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	req.getRequestDispatcher("./WEB-INF/filmingLocationPage/filmingLocation.jsp").forward(req, resp);
    }
}