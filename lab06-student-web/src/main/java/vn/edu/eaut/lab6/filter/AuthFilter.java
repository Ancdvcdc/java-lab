package vn.edu.eaut.lab6.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Bài 5: kiểm tra người dùng đã đăng nhập hay chưa trước khi vào các trang cần quyền.
 * Nếu chưa đăng nhập, chuyển về login.jsp.
 */
@WebFilter(urlPatterns = {
        "/students", "/student-form.jsp", "/student-edit", "/student-edit.jsp",
        "/student-delete", "/welcome.jsp", "/dashboard"
})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("AuthFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("username") != null;

        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        System.out.println("AuthFilter destroyed");
    }
}
