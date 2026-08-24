package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.SanPham;
import vn.edu.eaut.lab9.repository.SanPhamRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/san-pham")
public class SanPhamController extends HttpServlet {

    private final SanPhamRepository repository = new SanPhamRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            repository.delete(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect(request.getContextPath() + "/san-pham?thanhCong=Da+xoa+san+pham");
            return;
        }

        if ("form".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isBlank()) {
                request.setAttribute("sanPham", repository.findById(Integer.parseInt(idParam)));
            }
            request.getRequestDispatcher("/views/sanpham/form.jsp").forward(request, response);
            return;
        }

        String keyword = request.getParameter("keyword");
        List<SanPham> ds = (keyword == null || keyword.isBlank())
                ? repository.findAll()
                : repository.search(keyword);
        request.setAttribute("dsSanPham", ds);
        request.setAttribute("keyword", keyword == null ? "" : keyword);
        request.getRequestDispatcher("/views/sanpham/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        SanPham sp = new SanPham();
        if (idParam != null && !idParam.isBlank()) {
            sp.setId(Integer.parseInt(idParam));
        }
        sp.setMaSanPham(request.getParameter("maSanPham"));
        sp.setTenSanPham(request.getParameter("tenSanPham"));
        String giaStr = request.getParameter("gia");
        sp.setGia(giaStr == null || giaStr.isBlank() ? 0 : Double.parseDouble(giaStr));
        String slStr = request.getParameter("soLuong");
        sp.setSoLuong(slStr == null || slStr.isBlank() ? 0 : Integer.parseInt(slStr));

        try {
            if (sp.getId() == null) {
                if (repository.existsByMaSanPham(sp.getMaSanPham())) {
                    request.setAttribute("loi", "Ma san pham da ton tai.");
                    request.setAttribute("sanPham", sp);
                    request.getRequestDispatcher("/views/sanpham/form.jsp").forward(request, response);
                    return;
                }
                repository.save(sp);
            } else {
                repository.update(sp);
            }
            response.sendRedirect(request.getContextPath() + "/san-pham?thanhCong=Luu+thanh+cong");
        } catch (RuntimeException ex) {
            request.setAttribute("loi", "Da xay ra loi khi luu du lieu: " + ex.getMessage());
            request.setAttribute("sanPham", sp);
            request.getRequestDispatcher("/views/sanpham/form.jsp").forward(request, response);
        }
    }
}
