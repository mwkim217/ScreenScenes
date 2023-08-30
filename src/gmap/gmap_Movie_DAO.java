package gmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dbutil.DBUtil;
import object.Location;

public class gmap_Movie_DAO {
    public String getRequestBody(HttpServletRequest request) throws IOException {
	StringBuilder requestBodyBuilder = new StringBuilder();

	try (InputStream requestBodyStream = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(requestBodyStream, "UTF-8"));) {
	    String line;
	    while ((line = reader.readLine()) != null) {
		requestBodyBuilder.append(line);
	    }
	}

	return requestBodyBuilder.toString();
    }

    public void sendResponse(ResultSet rs, ObjectMapper objectMapper, HttpServletResponse response, Connection conn)
	    throws IOException, SQLException {
	// Jackson ObjectMapper를 사용하여 JSON 생성
	ArrayNode jsonArray = objectMapper.createArrayNode();

	while (rs.next()) {
	    ObjectNode jsonObject = objectMapper.createObjectNode();

	    jsonObject.put("title", rs.getString("title"));
	    jsonObject.put("count", getCountMovie(conn));

	    // poster 이미지를 Base64 형식으로 변환해서 전달
	    byte[] posterData = rs.getBytes("poster");
	    if (posterData != null) {
		String posterBase64 = Base64.getEncoder().encodeToString(posterData);
		jsonObject.put("poster", posterBase64);
	    } else {
		jsonObject.putNull("poster");
	    }

	    jsonArray.add(jsonObject);
	}

	// JSON 형식으로 응답 바디를 구성합니다
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	PrintWriter out = response.getWriter();
	out.print(jsonArray.toString());

    }

    public int getCountMovie(Connection conn) throws SQLException {

	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String countSql = "SELECT count(*) as number FROM movie.movie";
	pstmt = conn.prepareStatement(countSql);
	rs = pstmt.executeQuery();

	if (rs.next()) {
	    int count = rs.getInt("number");
	    return count;
	}

	return 0;

    }

    public synchronized static int insertIntoMovie(String title, InputStream posterinputStream, Connection conn) {
	PreparedStatement pstmt = null;

	try {

	    String sql = "INSERT INTO movie (title,poster) VALUES (?,?)";

	    pstmt = conn.prepareStatement(sql);

	    pstmt.setString(1, title);
	    pstmt.setBlob(2, posterinputStream);

	    int result = pstmt.executeUpdate();

	    return result;
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(pstmt);
	}
	return 0;

    }

    public synchronized static int insertIntoLocation(int movie_no, String address, Double latitude, Double longitude,
	    InputStream inputStream, Connection conn) throws SQLIntegrityConstraintViolationException {
	PreparedStatement pstmt = null;

	try {
	    conn.setAutoCommit(false);

	    String sql = "INSERT INTO location (movie_no,address,latitude,longitude,image) VALUES (?,?,?,?,?)";

	    pstmt = conn.prepareStatement(sql);

	    pstmt.setInt(1, movie_no);
	    pstmt.setString(2, address);
	    pstmt.setDouble(3, latitude);
	    pstmt.setDouble(4, longitude);
	    pstmt.setBlob(5, inputStream);
	    int result = pstmt.executeUpdate();

	    conn.commit();
	    return result;

	} catch (SQLException e) {
	    if (conn != null) {
		try {
		    conn.rollback(); // 롤백
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
	    }
	    e.printStackTrace();
	} finally {
	    if (conn != null) {
		try {
		    conn.setAutoCommit(true);
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	    DBUtil.close(pstmt);
	}
	return 0;

    }

    public static int getMovie_noWithTitle(String title, Connection conn) {
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {

	    String sql = "SELECT movie_no FROM movie WHERE title = ?";

	    pstmt = conn.prepareStatement(sql);

	    pstmt.setString(1, title);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		int movie_no = rs.getInt("movie_no");
		return movie_no;
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	}
	return -1;
    }

    public static boolean isDup(String title) {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {

	    conn = DBUtil.getConnection();
	    String sql = "SELECT * FROM movie WHERE title = ?";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, title);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		return true;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	    DBUtil.close(conn);
	}
	return false;

    }

    public List<Location> getLatLngMovieNoList() {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	List<Location> list = new ArrayList<Location>();
	try {
	    conn = DBUtil.getConnection();

	    String sql = "SELECT latitude,longitude,movie_no,location_no FROM location";

	    pstmt = conn.prepareStatement(sql);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {

		String latitude = rs.getString("latitude");
		String longitude = rs.getString("longitude");

		Double lat = Double.valueOf(latitude);
		Double lng = Double.valueOf(longitude);
		int movie_no = rs.getInt("movie_no");
		int location_no = rs.getInt("location_no");

		list.add(new Location(lat, lng, movie_no, location_no));

	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	    DBUtil.close(conn);
	}
	return list;
    }

    public List<Location> getDupLocation() {
	List<Location> list = getLatLngMovieNoList();
	List<Location> dup_list = new ArrayList<Location>();

	for (int i = 0; i < list.size(); i++) {
	    for (int j = 0; j < list.size(); j++) {

		if (list.get(i).equals(list.get(j))) {
		    if (!dup_list.contains(list.get(i))) {
			dup_list.add(list.get(i));
		    }
		}

	    }
	}
	return dup_list;

    }

    public void deleteLocationAndMovie() {
	List<Location> dup_list = getDupLocation();

	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = DBUtil.getConnection();

	    conn.setAutoCommit(false);

	    String sql = "DELETE FROM location WHERE location_no = ?";
	    pstmt = conn.prepareStatement(sql);

	    for (Location item : dup_list) {
		int location_no = item.getLocation_no();
		pstmt.setInt(1, location_no);
		pstmt.executeUpdate();
	    }

	    conn.commit();

	} catch (SQLException e) {
	    e.printStackTrace();

	    if (conn != null) {
		try {
		    conn.rollback();
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
	    }

	} finally {
	    DBUtil.close(pstmt);
	    DBUtil.close(conn);
	}

    }

    public static int getCountByTitle(Connection conn, String title) {

	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {

	    String sql = "SELECT movie.title,movie.movie_no,count(*) as count \r\n" + "FROM location\r\n"
		    + "JOIN movie\r\n" + "ON location.movie_no = movie.movie_no\r\n" + "where movie.title=? \r\n"
		    + "GROUP BY movie.movie_no";

	    pstmt = conn.prepareStatement(sql);

	    pstmt.setString(1, title);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		int count = rs.getInt("count");
		return count;
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	}
	return 0;

    }

}
