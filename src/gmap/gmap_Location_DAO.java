package gmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dbutil.DBUtil;
import object.Location;

public class gmap_Location_DAO {

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

    public List<Location> getLatLongList() {
	List<Location> entireDestinationList = new ArrayList<>();

	Connection JdbcConn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    JdbcConn = DBUtil.getConnection();
	    String sql = "SELECT latitude,longitude FROM movie.location;";

	    pstmt = JdbcConn.prepareStatement(sql);
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		double destinationLatitude = rs.getDouble("latitude");
		double destinationLongitude = rs.getDouble("longitude");

		entireDestinationList.add(new Location(destinationLatitude, destinationLongitude));

	    }

	    return entireDestinationList;
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(pstmt);
	    DBUtil.close(JdbcConn);

	}
	return entireDestinationList;
    }

}
