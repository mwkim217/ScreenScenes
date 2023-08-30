package path;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import dbutil.DBUtil;
import object.Location;
import object.SelectPath;
import object.ViewLocation;
import object.ViewPath;

public class SelectPathDAO {
	public List<Location> getLocationList(Connection conn, int[] selectedNos) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int location1 = selectedNos[0];
		int location2 = selectedNos[1];
		int location3 = selectedNos[2];
		int location4 = selectedNos[3];
		List<Location> list = new ArrayList<>();

		try {
			String sql = "SELECT * FROM Location WHERE location_no IN (?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, location1);
			stmt.setInt(2, location2);
			stmt.setInt(3, location3);
			stmt.setInt(4, location4);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int location_no = rs.getInt("location_no");
				int movie_no = rs.getInt("movie_no");
				String address = rs.getString("address");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				Blob image = rs.getBlob("image");
				list.add(new Location(location_no, movie_no, address, latitude, longitude, image));
			}
			return list;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
	}

	public ViewPath getViewPathArr(int num, SelectPath path) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();

			List<ViewLocation> viewLocList = new ArrayList<>();
			int userno = path.getUserno();
			List<Location> locationList = candidatePathLocation(conn, path);

			for (int j = 0; j < locationList.size(); j++) {
				Location location = locationList.get(j);

				int locationNo = location.getLocation_no();
				String locationName = location.getAddress();
				String locationImgStr = encodeBlobToStr(location.getImage());
				String posterImgStr = candidateMoviePoster(conn, location.getMovie_no());

				ViewLocation viewLoc1 = new ViewLocation(locationNo, locationName, locationImgStr, posterImgStr);
				viewLocList.add(viewLoc1);
			}
			return new ViewPath(num, userno, viewLocList);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
		return null;

	}

	public List<Location> candidatePathLocation(Connection conn, SelectPath path) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int location1 = path.getLocation1();
		int location2 = path.getLocation2();
		int location3 = path.getLocation3();
		int location4 = path.getLocation4();

		int[] locations = { location1, location2, location3, location4 };

		List<Location> list = new ArrayList<>();

		try {
			for (int i = 0; i < locations.length; i++) {
				String sql = "SELECT * FROM Location WHERE location_no = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, locations[i]);
				rs = stmt.executeQuery();

				while (rs.next()) {
					int location_no = rs.getInt("location_no");
					int movie_no = rs.getInt("movie_no");
					String address = rs.getString("address");
					double latitude = rs.getDouble("latitude");
					double longitude = rs.getDouble("longitude");
					Blob image = rs.getBlob("image");
					list.add(new Location(location_no, movie_no, address, latitude, longitude, image));
				}
			}
			return list;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
	}

	// Location에 해당하는 Movie의 포스터를 가져옴
	public String candidateMoviePoster(Connection conn, int movie_no) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT poster FROM movie WHERE movie_no = ?";
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, movie_no);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Blob poster = rs.getBlob("poster");
				return encodeBlobToStr(poster);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
		return null;
	}

	// 이진 자료(바이트)를 텍스트로 변환(인코딩)
	private String encodeBlobToStr(Blob blob) {
		byte[] imageData = null;
		try {
			int blobLength = (int) blob.length();
			imageData = blob.getBytes(1, blobLength);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String encoded = Base64.getMimeEncoder().encodeToString(imageData);
		return encoded;
	}

	// 결정한 경로를 db에 입력
	public int insertSelectedPath(Connection conn, SelectPath path) throws SQLException {
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "INSERT INTO path (userno, location1, location2, location3, location4) VALUES (?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, path.getUserno());
			stmt.setInt(2, path.getLocation1());
			stmt.setInt(3, path.getLocation2());
			stmt.setInt(4, path.getLocation3());
			stmt.setInt(5, path.getLocation4());
			return stmt.executeUpdate();

		} finally {
			DBUtil.close(stmt);
		}
	}

	// false면 중복이므로 생성금지, true면 생성가능
	public boolean searchDuplPath(Connection conn, SelectPath path) throws SQLException {
		int userno = path.getUserno();
		int location1 = path.getLocation1();
		int location2 = path.getLocation2();
		int location3 = path.getLocation3();
		int location4 = path.getLocation4();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT COUNT(*) AS cnt FROM path WHERE userno = ? AND location1 = ? AND location2 = ? AND  location3 = ? AND  location4 = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userno);
			stmt.setInt(2, location1);
			stmt.setInt(3, location2);
			stmt.setInt(4, location3);
			stmt.setInt(5, location4);
			rs = stmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt("cnt");
				if (count > 0) {
					return false;
				} else {
					return true;
				}
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
		}
		return true;
	}
}
