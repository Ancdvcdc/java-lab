package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab6.model.User;
import vn.edu.eaut.lab6.store.UserStore;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bài 4: đăng nhập bằng Servlet + Session.
 * Cập nhật cho Bài 9: xác thực qua UserStore, lưu thêm "role" vào session để phân quyền.
 * Cập nhật cho Bài 10: lưu thêm "loginTime" để hiển thị trên Dashboard.
 * Tài khoản mẫu: admin/123456 (ADMIN) hoặc user/123456 (USER).
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = UserStore.findByUsernameAndPassword(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("loginTime", LocalDateTime.now().format(FORMAT));
            response.sendRedirect(request.getContextPath() + "/welcome.jsp");
        } else {
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
