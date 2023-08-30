package movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dbutil.DBUtil;

public class MovieChoiceDAO {
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
			jsonObject.put("movieNumber", rs.getInt("movie_no"));

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

	public int getCountMovie(Connection conn) throws SQLException   {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String countSql = "SELECT count(*) as number FROM movie.movie";
			pstmt = conn.prepareStatement(countSql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int count = rs.getInt("number");
				return count;
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			
		}
		
		return 0;

	} 

}
