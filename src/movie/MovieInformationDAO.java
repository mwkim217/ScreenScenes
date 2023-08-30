package movie;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbutil.DBUtil;
import object.Location;
import object.Movie;
import object.User;

public class MovieInformationDAO {
	// 위도와 경도를 가져오는 메소드
	public Map<String, Double> selectLocation(String location) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM movie.location where address = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, location);
			List<Double> list = new ArrayList<>();
			Map<String, Double> map = new HashMap<>();
			rs = stmt.executeQuery();

			if (rs.next()) {
				map.put("latitude", rs.getDouble("latitude"));
				map.put("longitude", rs.getDouble("longitude"));
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return null;
	}

	// 영화 하나의 포스터을 가져오는 메소드
	public Blob selectOneMovie(int movie_no) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String title = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM movie where movie_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, movie_no);

			rs = stmt.executeQuery();

			if (rs.next()) {
				Blob poster = rs.getBlob("poster");
				return poster;
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

	// 영화 하나의 제목을 가져오는
	public String selectOneTitle(int movie_no) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM movie where movie_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, movie_no);

			rs = stmt.executeQuery();

			if (rs.next()) {
				String title = rs.getString("title");
				return title;
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

	// 영화 객체를 일정 개수 불러와서 리스트로 반환 (무한스크롤용)(일단 24개로 해둠)
	public List<Movie> selectMovieList(int scrollCount) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Movie> list = new ArrayList<>();
		try {
			int offset = scrollCount * 24;

			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM movie LIMIT 24 OFFSET ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, offset);

			rs = stmt.executeQuery();

			while (rs.next()) {
				int movie_no = rs.getInt("movie_no");
				String title = rs.getString("title");
				Blob posterBlob = rs.getBlob("poster");
				list.add(new Movie(movie_no, title, posterBlob));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return null;
	}

	// Movie[] 를 movieno로 변환
	public int[] movieNumber(Movie[] movies) {
		int[] arr = { movies[0].getMovie_no(), movies[1].getMovie_no(), movies[2].getMovie_no() };
		return arr;
	}

	// 유저가 영화를 3개 선택하여 DB에 저장.
	public int insertMovies(Movie[] movies, User user) {
		Connection conn = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		try {
			int[] arr = movieNumber(movies);
			conn = DBUtil.getConnection();

			String sql1 = "DELETE FROM user_choice WHERE userno = ?";
			stmt1 = conn.prepareStatement(sql1);
			stmt1.setInt(1, user.getUserno());
			stmt1.executeUpdate();

			String sql2 = "INSERT INTO user_choice (userno, movie1, movie2, movie3) VALUES (?, ?, ?, ?)";
			stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, user.getUserno());
			stmt2.setInt(2, arr[0]);
			stmt2.setInt(3, arr[1]);
			stmt2.setInt(4, arr[2]);
			return stmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt2);
			DBUtil.close(stmt1);
			DBUtil.close(conn);
		}
		return -1;
	}

	// 선택한 영화 하나에 따른 장소 리스트를 반환
	public List<Location> selectLocationList(int movie) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Location> list = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM location WHERE movie_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, movie);
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return null;
	}

	// 장소 1개 저장/최단거리?랜덤장소 추가 5개 저장 // 지도 로직 필요

	// 저장된 경로를 반환? // 지도 로직 필요

}
