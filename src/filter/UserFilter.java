package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/login", "/mypagemodify44"})
// 회원가입 규칙
// 아이디: 소문자, 대문자, 숫자를 이용하여 5~20자
// 비밀번호: 소문자, 대문자, 숫자, 정해진 특수문자들을 모두 포함 (~ ! @ # $ % ^ & * ( ) _ - + = [ ] , . / < >)
// 9~20자
// 닉네임: 소문자, 대문자, 한글, 숫자 5~20자
public class UserFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (req.getParameter("id") != null) {
			chain.doFilter(request, response);
			return;
		}
		
		if ("POST".equalsIgnoreCase(req.getMethod())) {

			String joinId = request.getParameter("joinId");
			String joinPassword = request.getParameter("joinPassword");
			String joinNickname = request.getParameter("joinNickname");
			
			if (!joinId.matches("[a-zA-Z0-9]{5,20}")) {
				req.setAttribute("joinIdError", "아이디는 소문자, 대문자, 숫자만을 이용하여 5~20자로 만들어야 합니다.");
			}

			String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#$%^&*()_\\-+=\\[\\],./<>?])"
					+ "[A-Za-z\\d~!@#$%^&*()_\\-+=\\[\\],./<>?]{9,20}$";

			if (!joinPassword.matches(pattern)) {
				req.setAttribute("joinPasswordError", "비밀번호 규칙을 지키지 않았습니다.");
			}

			if (!joinNickname.matches("[a-zA-Z0-9가-힣]{5,20}")) {
				req.setAttribute("joinNicknameError", "닉네임은 소문자, 대문자, 한글, 숫자만을 이용하여 5~20자로 만들어야 합니다.");
			}
		}
		chain.doFilter(request, response);
	}
}
