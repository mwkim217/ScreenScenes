package dbutil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBUtil {
	// 키=밸류 형태의 properties 파일을 객체 형태로 새용할 수 있는 클래스
	private static final Properties PROPS = new Properties();
	private static DataSource dataSource;

	static {
		// 최초로 이 클래스를 사용하고자 하면 클래스 로드 작업이 일어남.(JVM이 수행)
		// 로드 작업 중에는 static 블럭 안의 문장도 수행해줌
		// 적어도 한번은 꼭 실행됨
		
		// 드라이버 로드
		try {
			PROPS.load(DBUtil.class.getClassLoader().getResourceAsStream("dbutil/mysql.properties"));
//			Class.forName(PROPS.getProperty("jdbc.DRIVER"));
			BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName(PROPS.getProperty("jdbc.DRIVER"));
			ds.setUrl(PROPS.getProperty("jdbc.URL"));
			ds.setUsername(PROPS.getProperty("jdbc.USER"));
			ds.setPassword(PROPS.getProperty("jdbc.PASSWARD"));
			
			ds.setInitialSize(8);
			ds.setMaxTotal(25);
			
			dataSource = ds;
		} 
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
//		return DriverManager.getConnection(PROPS.getProperty("jdbc.URL"), PROPS.getProperty("jdbc.USER"),
//				PROPS.getProperty("jdbc.PASSWARD"));
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
		}
	}
}
