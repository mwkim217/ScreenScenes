package movie;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import dbutil.DBUtil;

@WebServlet("/moviechoice")
public class MovieChoiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ObjectMapper objectMapper = new ObjectMapper();
    MovieChoiceDAO choice_DAO = new MovieChoiceDAO();

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
	    JsonNode jsonNode = objectMapper.readTree(body);

	    int moviesListChildrenLength = jsonNode.get("length").asInt();
	    System.out.println(moviesListChildrenLength);
	    ArrayNode loadedMovieIdsNode = (ArrayNode) jsonNode.get("loadedMovieIds");
	    List<Integer> loadedMovieIds = new ArrayList<>();
	    for (JsonNode idNode : loadedMovieIdsNode) {
		loadedMovieIds.add(idNode.asInt());
	    }

	    String sql;
	    if (loadedMovieIds.isEmpty()) {
		sql = "SELECT * FROM movie ORDER BY RAND() LIMIT ?, 12";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, moviesListChildrenLength);
	    } else {
		sql = "SELECT * FROM movie WHERE movie_no NOT IN ("
			+ String.join(",", Collections.nCopies(loadedMovieIds.size(), "?"))
			+ ") ORDER BY RAND() LIMIT 12";
		pstmt = conn.prepareStatement(sql);
		for (int i = 0; i < loadedMovieIds.size(); i++) {
		    pstmt.setInt(i + 1, loadedMovieIds.get(i));
		}

	    }
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
