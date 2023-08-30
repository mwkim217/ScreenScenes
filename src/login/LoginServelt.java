package login;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import object.User;

@WebServlet("/login")
public class LoginServelt extends HttpServlet {
	LoginDao dao = new LoginDao();
	JoinDao joinDao = new JoinDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getParameter("joinNicknameError"));
		System.out.println(req.getParameter("joinIdError"));
		System.out.println(req.getParameter("joinPasswordError"));
		req.getRequestDispatcher("/WEB-INF/loginPage/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String formType = req.getParameter("form_type");
		
		System.out.println(req.getParameter("joinNicknameError"));
		System.out.println(req.getParameter("joinIdError"));
		System.out.println(req.getParameter("joinPasswordError"));

		if (formType.equals("loginForm")) {
			String id = req.getParameter("id");
			String password = req.getParameter("password");
			boolean remember = false;

			if (req.getParameter("remember") != null) {
				remember = req.getParameter("remember").equals("check");
			}
			if (dao.checkId(id)) {
				if (dao.checkPassword(id, password)) {
					if (remember) {
						Cookie cookie = new Cookie("remember", id);
						resp.addCookie(cookie);
					}
					
					User user = dao.getUserInfo(id);
					String nickname = user.getNickname();
					int userno = user.getUserno();
					Blob profile = user.getProfile();
					
					HttpSession session = req.getSession();
					session.setAttribute("loggedUserId", id);
					session.setAttribute("loggedUserNickname", nickname);
					session.setAttribute("loggedUserNo", userno);
					if (profile != null) {
						InputStream inputStream;
						try {
							inputStream = profile.getBinaryStream();
							byte[] bytes = new byte[(int) profile.length()];
							inputStream.read(bytes);
							String img = Base64.getEncoder().encodeToString(bytes);
							session.setAttribute("loggedUserProfileImg", img);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					dao.updateAccess(userno);
					// 다음페이지로 이동
					resp.sendRedirect("/ScreenSceneP/movie");
				} else {
					req.setAttribute("loginError", "아이디 또는 비밀번호를 틀리셨습니다.");
					req.getRequestDispatcher("/WEB-INF/loginPage/login.jsp").forward(req, resp);
				}
			} else {
				req.setAttribute("loginError", "아이디 또는 비밀번호를 틀리셨습니다.");
				req.getRequestDispatcher("/WEB-INF/loginPage/login.jsp").forward(req, resp);
			}
		} else if (formType.equals("joinForm")) {
			String joinNickname = req.getParameter("joinNickname");
			String joinId = req.getParameter("joinId");
			String joinPassword = req.getParameter("joinPassword");
			String joinPasswordRe = req.getParameter("joinPasswordRe");

			boolean isJoin = true;

			req.setAttribute("inputNickname", joinNickname);
			req.setAttribute("inputId", joinId);
			req.setAttribute("joinPassword", joinPassword);
			req.setAttribute("joinPasswordRe", joinPasswordRe);
			
			if (!joinDao.duplicateNickname(joinNickname)) {
				req.removeAttribute("joinNicknameError");
			} else {
				req.setAttribute("joinNicknameError", "같은 닉네임이 존재합니다.");
				isJoin = false;
			}

			if (!joinDao.duplicateId(joinId)) {
				req.removeAttribute("joinIdError");
			} else {
				req.setAttribute("joinIdError", "같은 이름의 아이디가 존재합니다.");
				isJoin = false;
			}

			if (joinPassword.equals(joinPasswordRe)) {
				req.removeAttribute("joinPasswordError");
			} else {
				req.setAttribute("joinPasswordError", "입력한 비밀번호가 서로 다릅니다.");
				isJoin = false;
			}

			if (isJoin) {
				InputStream img = getServletContext().getResourceAsStream("/img/기본프로필사진.png");
				joinDao.insertId(joinNickname, joinId, joinPassword, img);
			}
			req.getRequestDispatcher("/WEB-INF/loginPage/login.jsp").forward(req, resp);
		}
	}
}
