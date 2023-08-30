package login;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import dbutil.DBUtil;
import object.User;

public class LoginDao {
	public boolean checkId(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return true;
	}

	public boolean checkPassword(String id, String password) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT password FROM user WHERE id = ?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				if (rs.getString("password").equals(password)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return true;
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

	public User getUserInfo(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		User user = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				int userno = rs.getInt("userno");
				String nickname = rs.getString("nickname");
				Blob profile = rs.getBlob("profile");

				user = new User(userno, id, nickname, profile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return user;
	}

	public void updateAccess(int userno) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("UPDATE user SET access = ? WHERE userno = ?");
			LocalDateTime now = LocalDateTime.now();
			Timestamp timestamp = Timestamp.valueOf(now);
			stmt.setTimestamp(1, timestamp);
			stmt.setInt(2, userno);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}
}
