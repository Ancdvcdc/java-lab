package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.Sach;
import vn.edu.eaut.lab9.repository.SachRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/sach")
public class SachController extends HttpServlet {

    private final SachRepository repository = new SachRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            repository.delete(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect(request.getContextPath() + "/sach?thanhCong=Da+xoa+sach");
            return;
        }

        if ("form".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isBlank()) {
                request.setAttribute("sach", repository.findById(Integer.parseInt(idParam)));
            }
            request.getRequestDispatcher("/views/sach/form.jsp").forward(request, response);
            return;
        }

        String keyword = request.getParameter("keyword");
        List<Sach> ds = (keyword == null || keyword.isBlank())
                ? repository.findAll()
                : repository.search(keyword);
        request.setAttribute("dsSach", ds);
        request.setAttribute("keyword", keyword == null ? "" : keyword);
        request.getRequestDispatcher("/views/sach/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        Sach sach = new Sach();
        if (idParam != null && !idParam.isBlank()) {
            sach.setId(Integer.parseInt(idParam));
        }
        sach.setMaSach(request.getParameter("maSach"));
        sach.setTenSach(request.getParameter("tenSach"));
        sach.setTacGia(request.getParameter("tacGia"));
        String slStr = request.getParameter("soLuong");
        sach.setSoLuong(slStr == null || slStr.isBlank() ? 0 : Integer.parseInt(slStr));

        try {
            if (sach.getId() == null) {
                if (repository.existsByMaSach(sach.getMaSach())) {
                    request.setAttribute("loi", "Ma sach da ton tai.");
                    request.setAttribute("sach", sach);
                    request.getRequestDispatcher("/views/sach/form.jsp").forward(request, response);
                    return;
                }
                repository.save(sach);
            } else {
                repository.update(sach);
            }
            response.sendRedirect(request.getContextPath() + "/sach?thanhCong=Luu+thanh+cong");
        } catch (RuntimeException ex) {
            request.setAttribute("loi", "Da xay ra loi khi luu du lieu: " + ex.getMessage());
            request.setAttribute("sach", sach);
            request.getRequestDispatcher("/views/sach/form.jsp").forward(request, response);
        }
    }
}
