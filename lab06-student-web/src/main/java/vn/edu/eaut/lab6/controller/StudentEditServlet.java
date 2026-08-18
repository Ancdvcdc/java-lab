package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;

/**
 * Bài 8: Cập nhật thông tin sinh viên.
 * GET  -> hiển thị form sửa với dữ liệu cũ (không cho sửa mã sinh viên).
 * POST -> lưu thay đổi họ tên, lớp, email.
 */
@WebServlet("/student-edit")
public class StudentEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Student student = id == null ? null : StudentStore.findById(id);

        if (student == null) {
            request.setAttribute("error", "Không tìm thấy sinh viên có mã: " + id);
            request.getRequestDispatcher("/student-list.jsp").forward(request, response);
            return;
        }

        request.setAttribute("student", student);
        request.getRequestDispatcher("/student-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String className = request.getParameter("className");
        String email = request.getParameter("email");

        boolean success = StudentStore.update(id, name, className, email);
        if (!success) {
            request.setAttribute("error", "Cập nhật thất bại, không tìm thấy sinh viên: " + id);
        }
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
