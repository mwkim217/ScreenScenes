package myPageModify;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import myPage.MyPageDao;

@WebServlet("/mypagemodify")
public class MyPageModifyServlet extends HttpServlet {
	MyPageDao dao = new MyPageDao();
	MyPageModifyDao modifyDao = new MyPageModifyDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/myPageModify/myPageModify.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-store");
		String form = req.getParameter("form_type");
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("loggedUserId");
		boolean isCheck = true;

		if (form != null) {

			if (form.equals("changeNicknameForm")) {
				String changeNickname = req.getParameter("changeNickname");
				String checkPassword = req.getParameter("passwordCheck");
				String nowNickname = modifyDao.getNickname(id);

				if (!modifyDao.duplicateNickname(changeNickname)) {
					req.removeAttribute("changeNicknameError");
				} else if (nowNickname.equals(changeNickname)) {
					req.setAttribute("changeNicknameError", "현재 닉네임과 동일한 닉네입입니다");
					System.out.println("여기로오니?");
					isCheck = false;
				} else {
					req.setAttribute("changeNickname", changeNickname);
					req.setAttribute("changeNicknameError", "같은 닉네임이 존재합니다.");
					isCheck = false;
				}

				if (modifyDao.checkPassword(id, checkPassword)) {
					req.removeAttribute("failCheckPassword");
				} else {
					req.setAttribute("failCheckPassword", "비밀번호를 틀렸습니다. 다시 확인해주세요.");
					isCheck = false;
				}

				if (isCheck) {
					modifyDao.updateUserNickname(nowNickname, changeNickname);
					session.setAttribute("loggedUserNickname", changeNickname);
				}
			}

			if (form.equals("changePasswordForm")) {
				String passwordNow = req.getParameter("passwordNow");
				String passwordChange = req.getParameter("passwordChange");
				String passwordChangeRe = req.getParameter("passwordChangeRe");

				if (modifyDao.checkPassword(id, passwordNow)) {
					req.removeAttribute("failCheckPasswordChange");
				} else {
					req.setAttribute("failCheckPasswordChange", "비밀번호를 틀렸습니다. 다시 확인해주세요.");
					isCheck = false;
				}

				if (passwordChange.equals(passwordChangeRe)) {
					req.removeAttribute("passwordInputError");
				} else {
					req.setAttribute("passwordInputError", "두 비밀번호 입력이 다릅니다. 다시 확인해주세요.");
					isCheck = false;
				}

				if (isCheck) {
					modifyDao.updateUserPassword(passwordNow, passwordChange);
				}
			}
		}

		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
			if (isMultipart) {
			    DiskFileItemFactory factory = new DiskFileItemFactory();
			    ServletFileUpload upload = new ServletFileUpload(factory);

			    try {
			        List<FileItem> items = upload.parseRequest(req);

			        for (FileItem item : items) {
			            if (!item.isFormField()) {
			                // InputStream을 바이트 배열로 바로 변환
			                byte[] imageData = IOUtils.toByteArray(item.getInputStream());

			                // 바이트 배열을 DAO에 저장
			                dao.uploadImg(id, new ByteArrayInputStream(imageData));

			                // 바이트 배열을 Base64로 인코딩
			                String encodedImage = Base64.getEncoder().encodeToString(imageData);

			                // 세션에 Data URI 저장
			                session.setAttribute("loggedUserProfileImg", encodedImage);
			            }
			        }
			        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			    } catch (FileUploadException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
			req.getRequestDispatcher("/WEB-INF/myPageModify/myPageModify.jsp").forward(req, resp);
		}
	}
}
