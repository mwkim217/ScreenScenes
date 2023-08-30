package myPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import object.Location;

@WebServlet("/mypath")
public class MyPathServlet extends HttpServlet {
    MyPageDao dao = new MyPageDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	System.out.println("요청왔음");

	String pathNoStr = req.getParameter("pathNo");

	int pathNo = Integer.parseInt(pathNoStr);
	System.out.println(pathNo);

	List<Location> locationList = dao.getOnePathLocationList(pathNo);
	List<Location> latLngList = new ArrayList<>();

	for (Location elem : locationList) {
	    latLngList.add(new Location(elem.getLatitude(), elem.getLongitude()));
	}

	System.out.println(latLngList);

	req.setAttribute("latLngList", latLngList);
	req.getRequestDispatcher("/WEB-INF/myPage/onePathMap.jsp").forward(req, resp);
    }
}