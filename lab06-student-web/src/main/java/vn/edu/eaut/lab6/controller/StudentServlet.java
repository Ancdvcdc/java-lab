package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.io.IOException;
import java.util.List;

/**
 * Bài 2 + 3: nhận dữ liệu từ form, lưu vào StudentStore, hiển thị bằng JSP/JSTL.
 * Bài 6: hỗ trợ tìm kiếm sinh viên theo tên qua tham số "keyword".
 */
@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("keyword");

        List<Student> list;
        if (keyword != null && !keyword.trim().isEmpty()) {
            list = StudentStore.searchByName(keyword);
            request.setAttribute("keyword", keyword);
        } else {
            list = StudentStore.findAll();
        }

        request.setAttribute("students", list);
        request.getRequestDispatcher("/student-list.jsp").forward(request, response);
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

        if (id == null || id.trim().isEmpty()) {
            request.setAttribute("error", "Mã sinh viên không được để trống");
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }
        if (StudentStore.findById(id) != null) {
            request.setAttribute("error", "Mã sinh viên '" + id + "' đã tồn tại");
            request.getRequestDispatcher("/student-form.jsp").forward(request, response);
            return;
        }

        Student student = new Student(id, name, className, email);
        StudentStore.add(student);
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
