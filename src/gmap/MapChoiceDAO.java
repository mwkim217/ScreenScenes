package gmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MapChoiceDAO {
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

       jsonObject.put("filminglocationlat", rs.getDouble("latitude"));
       jsonObject.put("filminglocationlng", rs.getDouble("longitude"));

       jsonArray.add(jsonObject);
   }

   // JSON 형식으로 응답 바디를 구성합니다
   response.setContentType("application/json");
   response.setCharacterEncoding("UTF-8");
   PrintWriter out = response.getWriter();
   out.print(jsonArray.toString());

    }

}