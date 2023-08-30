package path;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dbutil.DBUtil;
import object.SelectPath;

@WebServlet("/savepath")
public class SavePathServlet extends HttpServlet {
    SelectPathDAO dao = new SelectPathDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	System.out.println("서블릿 연결 성공");

	String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

	// Gson을 사용하여 requestBody 객체를 SelectPath 객체로 변환
	Gson gson = new Gson();
	SelectPath selectPath = gson.fromJson(requestBody, SelectPath.class); // JSON 객체를 문자열로 변환
	System.out.println(selectPath);

	Connection conn = null;
	try {
	    conn = DBUtil.getConnection();
	    boolean searchresult = dao.searchDuplPath(conn, selectPath);
	    if (searchresult) {
		int result = dao.insertSelectedPath(conn, selectPath);
		System.out.println(result);
		System.out.println("경로 저장 완료");
		resp.setContentType("application/json");
		resp.getWriter().write("{\"status\":\"success\"}");
	    } else {
		System.out.println("이미 저장된 경로임");
		resp.setContentType("application/json");
		resp.getWriter().write("{\"status\":\"false\"}");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    DBUtil.close(conn);
	}
    }
}
