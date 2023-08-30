package myPage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import object.MyPath;

@WebServlet("/mypage")
public class MyPageServlet extends HttpServlet {
    MyPageDao dao = new MyPageDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	HttpSession session = req.getSession();
	String id = (String) session.getAttribute("loggedUserId");
	
	int userno = dao.getUserNo(id);
	System.out.println(userno);
	List<MyPath> list = dao.getMyPath(userno);

	req.setAttribute("list", list);
	req.getRequestDispatcher("./WEB-INF/myPage/myPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String inputType = req.getParameter("input_type");
	System.out.println(inputType);
	if (inputType != null && inputType.equals("cancel")) {
	    String pathNo = req.getParameter("pathPk");
	    System.out.println("여기까지 오니?");
	    if (pathNo != null) {
		dao.deletePath(pathNo);
		resp.sendRedirect("/ScreenSceneP/mypage");
		return;
	    }
	}
	if (inputType != null && inputType.equals("inputName")) {
	    String pathNo = req.getParameter("pathPk");
	    String pathName = req.getParameter("pathName");

	    dao.updatePathName(pathNo, pathName);
	    resp.sendRedirect("/ScreenSceneP/mypage");
	    return;
	}
	resp.sendRedirect("/ScreenSceneP/mypagemodify");
    }
}