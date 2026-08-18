package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;

/**
 * Bài 7: Xóa sinh viên khỏi danh sách theo mã sinh viên (?id=...),
 * sau đó quay lại trang danh sách. Chỉ ADMIN mới được phép (xem AdminFilter).
 */
@WebServlet("/student-delete")
public class StudentDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.trim().isEmpty()) {
            StudentStore.deleteById(id);
        }
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
