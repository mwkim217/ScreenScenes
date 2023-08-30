package crawling;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dbutil.DBUtil;
import gmap.MapChoiceDAO;

@WebServlet("/mapchoice")
public class MapChoiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ObjectMapper objectMapper = new ObjectMapper();
    MapChoiceDAO choice_DAO = new MapChoiceDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    conn = DBUtil.getConnection();

	    String body = choice_DAO.getRequestBody(request);
	    System.out.println(body);
	    JsonNode jsonNode = objectMapper.readTree(body);
	    String locationNoStr = jsonNode.get("location_no").asText().replaceAll("[\\[\\]\"]", "");
	    ;
	    int location_no = Integer.parseInt(locationNoStr);
	    System.out.println(location_no);

	    String sql = "SELECT * FROM location WHERE location_no = ?";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, location_no);
	    rs = pstmt.executeQuery();
	    choice_DAO.sendResponse(rs, objectMapper, response, conn);
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {

	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	    DBUtil.close(conn);

	}

    }

}