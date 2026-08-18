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
 * Bài 9: Phân quyền ADMIN / USER.
 * - ADMIN được thêm/sửa/xóa sinh viên.
 * - USER chỉ được xem danh sách; nếu cố truy cập chức năng quản trị -> chuyển về 403.jsp.
 *
 * Filter này chạy SAU AuthFilter (đã đảm bảo người dùng đăng nhập), chỉ kiểm tra
 * thêm điều kiện role == ADMIN cho các thao tác ghi dữ liệu.
 */
@WebFilter(urlPatterns = {
        "/students", "/student-form.jsp", "/student-edit", "/student-edit.jsp", "/student-delete"
})
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("AdminFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request,
                          ServletResponse response,
                          FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String path = uri.substring(req.getContextPath().length());
        String method = req.getMethod();

        // Chỉ những thao tác GHI dữ liệu mới cần quyền ADMIN:
        // - /student-form.jsp, /student-edit(.jsp), /student-delete: luôn cần ADMIN
        // - /students: chỉ cần ADMIN khi là POST (thêm sinh viên); GET (xem danh sách) cho phép mọi role
        boolean needsAdmin =
                path.equals("/student-form.jsp")
                || path.equals("/student-edit")
                || path.equals("/student-edit.jsp")
                || path.equals("/student-delete")
                || (path.equals("/students") && "POST".equalsIgnoreCase(method));

        if (needsAdmin) {
            HttpSession session = req.getSession(false);
            String role = session != null ? (String) session.getAttribute("role") : null;
            if (!"ADMIN".equals(role)) {
                resp.sendRedirect(req.getContextPath() + "/403.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("AdminFilter destroyed");
    }
}
