package gmap;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import movie.MovieInformationDAO;

@WebServlet("/hotelfood")
public class SearchHotelFood extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	MovieInformationDAO dao = new MovieInformationDAO();

	String location = req.getParameter("locationName");
	String search = req.getParameter("search");

	Double latitude = dao.selectLocation(location).get("latitude");
	Double longitude = dao.selectLocation(location).get("longitude");

	req.setAttribute("search", search);
	req.setAttribute("latitude", latitude);
	req.setAttribute("longitude", longitude);

	req.getRequestDispatcher("./WEB-INF/hotelFoodPage/hotelFood.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}