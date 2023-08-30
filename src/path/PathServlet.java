package path;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbutil.DBUtil;
import gmap.DistanceCalculator;
import movie.MovieInformationDAO;
import object.Location;
import object.SelectPath;
import object.ViewPath;

@WebServlet("/selectpath")
public class PathServlet extends HttpServlet {
    SelectPathDAO selectPathDao = new SelectPathDAO();
    MovieInformationDAO movieDao = new MovieInformationDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String selectedLocationNos = req.getParameter("selectedLocationNos");
    String[] movieNumber = req.getParameterValues("movieNumber");
    
    if (selectedLocationNos == null || movieNumber == null) {
    	resp.sendRedirect("http://192.168.0.56:8080/ScreenSceneP/movie");
    	return;
    }
    
	doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	Connection conn = null;

	int[] selectedNos = new int[4];

	String selectedLocationNos = req.getParameter("selectedLocationNos");

	System.out.println("getParameter로 받아오 지역:" + selectedLocationNos);

	if (selectedLocationNos != null && !selectedLocationNos.isEmpty()) {
	    String[] numbersString = selectedLocationNos.split(",");
//			selectedNos = new int[numbersString.length];
	    System.out.println("numbersString" + numbersString);

	    for (int i = 0; i < numbersString.length; i++) {
//				selectedNos[i] = Integer.parseInt(numbersString[i].trim());

		String number = numbersString[i].replaceAll("[\\[\\]]", "").trim();
		selectedNos[i] = Integer.parseInt(number);

	    }

	}

	System.out.println(selectedNos[0]);
	System.out.println(selectedNos[1]);
	System.out.println(selectedNos[2]);
	System.out.println(selectedNos[3]);

	// 선택된 영화 번호

	int[] selectedMovies = new int[5];

	String[] movieNumber = req.getParameterValues("movieNumber");

//   System.out.println(movieNumber.length);

	for (int i = 0; i < movieNumber.length; i++) {
//       System.out.println(movieNumber[i]);
	}

	for (int i = 0; i < movieNumber.length; i++) {
	    selectedMovies[i] = Integer.parseInt(movieNumber[i].trim());

//       System.out.println(selectedMovies[i]);
	}

	System.out.println("0번 째 영화넘버: " + selectedMovies[0]);
	System.out.println("1번 째 영화넘버: " + selectedMovies[1]);
	System.out.println("2번 째 영화넘버: " + selectedMovies[2]);
	System.out.println("3번 째 영화넘버: " + selectedMovies[3]);
	System.out.println("4번 째 영화넘버: " + selectedMovies[4]);

	try {
	    conn = DBUtil.getConnection();

	    List<Location> selectedLocation = selectPathDao.getLocationList(conn, selectedNos);
	    List<Location> entireSelectedList = new ArrayList<Location>();

	    for (int i = 0; i < selectedMovies.length; i++) {

		List<Location> entireSelectedListPerMovie = movieDao.selectLocationList(selectedMovies[i]);
		entireSelectedList.addAll(entireSelectedListPerMovie);

	    }

	    List<Location> firstLocationList = DistanceCalculator.getFirstLocation(selectedLocation);

	    List<Location> secondLocationList = DistanceCalculator.getSecondLocation(firstLocationList,
		    entireSelectedList);

	    List<Location> thirdLocationList = DistanceCalculator.getThirdLocation(firstLocationList,
		    secondLocationList, entireSelectedList);

	    System.out.println("firstLocationList의 크기" + firstLocationList.size());
	    for (int i = 0; i < firstLocationList.size(); i++) {

		System.out.println("firstLocationList의 위도: " + firstLocationList.get(i).getLatitude());
		System.out.println("firstLocationList의 경도: " + firstLocationList.get(i).getLongitude());
	    }
	    System.out.println("secondLocationList의 크기" + firstLocationList.size());
	    for (int i = 0; i < secondLocationList.size(); i++) {

		System.out.println("secondLocationList의 위도: " + secondLocationList.get(i).getLatitude());
		System.out.println("secondLocationList의 경도: " + secondLocationList.get(i).getLongitude());
	    }
	    System.out.println("thirdLocationList의 크기" + firstLocationList.size());
	    for (int i = 0; i < thirdLocationList.size(); i++) {

		System.out.println("thirdLocationList의 위도: " + thirdLocationList.get(i).getLatitude());
		System.out.println("thirdLocationList의 경도: " + thirdLocationList.get(i).getLongitude());
	    }

	    Location[] firstlatAndLngs = new Location[4];
	    for (int i = 0; i < firstLocationList.size(); i++) {
		firstlatAndLngs[i] = new Location(firstLocationList.get(i).getLatitude(),
			firstLocationList.get(i).getLongitude());

	    }
	    Location[] secondlatAndLngs = new Location[4];
	    for (int i = 0; i < secondLocationList.size(); i++) {
		secondlatAndLngs[i] = new Location(secondLocationList.get(i).getLatitude(),
			secondLocationList.get(i).getLongitude());

	    }
	    Location[] thirdlatAndLngs = new Location[4];
	    for (int i = 0; i < thirdLocationList.size(); i++) {
		thirdlatAndLngs[i] = new Location(thirdLocationList.get(i).getLatitude(),
			thirdLocationList.get(i).getLongitude());

	    }

	    req.setAttribute("firstlatAndLngs", firstlatAndLngs);
	    req.setAttribute("secondlatAndLngs", secondlatAndLngs);
	    req.setAttribute("thirdlatAndLngs", thirdlatAndLngs);
	    
	    HttpSession session = req.getSession();
	    int userno = (int) session.getAttribute("loggedUserNo");

	    SelectPath path1 = new SelectPath(userno, firstLocationList.get(0).getLocation_no(),
		    firstLocationList.get(1).getLocation_no(), firstLocationList.get(2).getLocation_no(),
		    firstLocationList.get(3).getLocation_no(), null);

	    SelectPath path2 = new SelectPath(userno, secondLocationList.get(0).getLocation_no(),
		    secondLocationList.get(1).getLocation_no(), secondLocationList.get(2).getLocation_no(),
		    secondLocationList.get(3).getLocation_no(), null);

	    SelectPath path3 = new SelectPath(userno, thirdLocationList.get(0).getLocation_no(),
		    thirdLocationList.get(1).getLocation_no(), thirdLocationList.get(2).getLocation_no(),
		    thirdLocationList.get(3).getLocation_no(), null);

	    ViewPath viewPath1 = selectPathDao.getViewPathArr(1, path1);
	    ViewPath viewPath2 = selectPathDao.getViewPathArr(2, path2);
	    ViewPath viewPath3 = selectPathDao.getViewPathArr(3, path3);

	    req.setAttribute("path1", path1);
	    req.setAttribute("path2", path2);
	    req.setAttribute("path3", path3);

	    req.setAttribute("viewPath1", viewPath1);
	    req.setAttribute("viewPath2", viewPath2);
	    req.setAttribute("viewPath3", viewPath3);

	    req.getRequestDispatcher("/WEB-INF/selectPathPage/selectPath.jsp").forward(req, resp);

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(conn);
	}

    }

}