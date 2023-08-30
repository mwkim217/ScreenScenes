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
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/movie", "/flow", "/selectpath", "/mypage", "/mypagemodify" })
public class NaviFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse resp = (HttpServletResponse) response;

	HttpSession session = req.getSession();
	String isLogin = (String) session.getAttribute("loggedUserId");
	System.out.println(isLogin);
	if (isLogin == null) {
	    resp.sendRedirect("login");
	    return;
	}
	chain.doFilter(request, response);
    }
}
