package myPage;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.DBUtil;
import object.Location;
import object.MyPath;

public class MyPageDao {

    public int getUserNo(String id) {
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	int no = -1;
	try {
	    conn = DBUtil.getConnection();
	    stmt = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
	    stmt.setString(1, id);
	    rs = stmt.executeQuery();

	    if (rs.next()) {
		no = rs.getInt("userno");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(stmt);
	    DBUtil.close(conn);
	}
	return no;
    }

    public String getNickname(String id) {
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	String nickname = "";
	try {
	    conn = DBUtil.getConnection();
	    stmt = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
	    stmt.setString(1, id);
	    rs = stmt.executeQuery();
	    if (rs.next()) {
		nickname = rs.getString("nickname");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(stmt);
	    DBUtil.close(conn);
	}
	return nickname;
    }

    public List<MyPath> getMyPath(int userno) {
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	List<MyPath> list = new ArrayList<>();

	try {
	    conn = DBUtil.getConnection();
	    stmt = conn.prepareStatement("SELECT *\r\n" + "FROM path\r\n" + "WHERE userno = ?");
	    stmt.setInt(1, userno);
	    rs = stmt.executeQuery();

	    while (rs.next()) {
		int pathNo = rs.getInt("path_no");
		int location1 = rs.getInt("location1");
		int location2 = rs.getInt("location2");
		int location3 = rs.getInt("location3");
		int location4 = rs.getInt("location4");
		String rootName = rs.getString("pathName");

		Location locationAddress1 = getLocationInfo(location1, conn);
		Location locationAddress2 = getLocationInfo(location2, conn);
		Location locationAddress3 = getLocationInfo(location3, conn);
		Location locationAddress4 = getLocationInfo(location4, conn);

		list.add(new MyPath(pathNo, locationAddress1, locationAddress2, locationAddress3, locationAddress4,
			rootName));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(stmt);
	    DBUtil.close(conn);
	}
	return list;
    }

    public Location getLocationInfo(int location, Connection conn) {
	PreparedStatement stmt = null;
	ResultSet rs = null;

	Location locationInfo = null;

	try {
	    stmt = conn.prepareStatement(
		    "SELECT *\r\n" + "						FROM location\r\n"
			    + "						WHERE location_no = ?");
	    stmt.setInt(1, location);
	    rs = stmt.executeQuery();

	    if (rs.next()) {
		String address = rs.getString("address");
		double latitude = rs.getDouble("latitude");
		double longitude = rs.getDouble("longitude");

		locationInfo = new Location(location, address, latitude, longitude);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(stmt);
	}
	return locationInfo;
    }

    public void deletePath(String pathNo) {
	Connection conn = null;
	PreparedStatement stmt = null;

	try {
	    conn = DBUtil.getConnection();
	    stmt = conn.prepareStatement("DELETE FROM path WHERE path_no = ?");
	    stmt.setInt(1, Integer.parseInt(pathNo));
	    stmt.executeUpdate();

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(stmt);
	    DBUtil.close(conn);
	}
    }

    public void uploadImg(String id, InputStream fileContent) {
	Connection conn = null;
	PreparedStatement stmt = null;

	try {
	    conn = DBUtil.getConnection();
	    stmt = conn.prepareStatement("UPDATE user SET profile = ? WHERE id = ?");
	    stmt.setBlob(1, fileContent);
	    stmt.setString(2, id);
	    stmt.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(stmt);
	    DBUtil.close(conn);
	}
    }

    public Blob getProfile(String id) {
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	Blob profile = null;
	try {
	    conn = DBUtil.getConnection();
	    stmt = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
	    stmt.setString(1, id);
	    rs = stmt.executeQuery();
	    if (rs.next()) {
		profile = rs.getBlob("profile");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(stmt);
	    DBUtil.close(conn);
	}
	return profile;
    }

    public void updatePathName(String pathNo, String pathName) {
	Connection conn = null;
	PreparedStatement stmt = null;

	try {
	    conn = DBUtil.getConnection();
	    stmt = conn.prepareStatement("UPDATE path SET pathName = ? WHERE path_no = ?");
	    stmt.setString(1, pathName);
	    stmt.setInt(2, Integer.parseInt(pathNo));
	    stmt.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(stmt);
	    DBUtil.close(conn);
	}
    }

    public List<Location> getOnePathLocationList(int pathNo) {
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	List<Location> list = new ArrayList<>();
	try {
	    conn = DBUtil.getConnection();
	    stmt = conn.prepareStatement("SELECT *\r\n" + "FROM path\r\n" + "WHERE path_no = ?");
	    stmt.setInt(1, pathNo);
	    rs = stmt.executeQuery();

	    while (rs.next()) {
		int pathNoParse = rs.getInt("path_no");
		int location1 = rs.getInt("location1");
		int location2 = rs.getInt("location2");
		int location3 = rs.getInt("location3");
		int location4 = rs.getInt("location4");
		String rootName = rs.getString("pathName");

		Location locationAddress1 = getLocationInfo(location1, conn);
		Location locationAddress2 = getLocationInfo(location2, conn);
		Location locationAddress3 = getLocationInfo(location3, conn);
		Location locationAddress4 = getLocationInfo(location4, conn);

		list.add(locationAddress1);
		list.add(locationAddress2);
		list.add(locationAddress3);
		list.add(locationAddress4);
		return list;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(rs);
	    DBUtil.close(stmt);
	    DBUtil.close(conn);
	}
	return null;
    }
}
