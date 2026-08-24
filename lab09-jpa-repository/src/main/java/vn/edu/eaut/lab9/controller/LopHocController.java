package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.LopHoc;
import vn.edu.eaut.lab9.repository.LopHocRepository;
import vn.edu.eaut.lab9.repository.SinhVienRepository;

import java.io.IOException;

@WebServlet("/lop-hoc")
public class LopHocController extends HttpServlet {

    private final LopHocRepository repository = new LopHocRepository();
    private final SinhVienRepository sinhVienRepository = new SinhVienRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            // Chan xoa neu lop van con sinh vien, tranh mat du lieu ngoai y muon
            // va tranh loi ConstraintViolationException neu sinh vien trong lop da co diem.
            int soSinhVien = sinhVienRepository.findByLopHoc(id).size();
            if (soSinhVien > 0) {
                response.sendRedirect(request.getContextPath()
                        + "/lop-hoc?loi=Khong+the+xoa+lop+dang+co+" + soSinhVien
                        + "+sinh+vien.+Hay+chuyen+sinh+vien+sang+lop+khac+truoc.");
                return;
            }
            try {
                repository.delete(id);
                response.sendRedirect(request.getContextPath() + "/lop-hoc?thanhCong=Da+xoa+lop");
            } catch (RuntimeException ex) {
                response.sendRedirect(request.getContextPath()
                        + "/lop-hoc?loi=Khong+the+xoa+lop+nay+do+loi+du+lieu");
            }
            return;
        }

        if ("form".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isBlank()) {
                request.setAttribute("lopHoc", repository.findById(Integer.parseInt(idParam)));
            }
            request.getRequestDispatcher("/views/lophoc/form.jsp").forward(request, response);
            return;
        }

        if ("xemSinhVien".equals(action)) {
            Integer lopId = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("lopHoc", repository.findById(lopId));
            request.setAttribute("dsSinhVien", sinhVienRepository.findByLopHoc(lopId));
            request.getRequestDispatcher("/views/lophoc/sinhvien-theo-lop.jsp")
                    .forward(request, response);
            return;
        }

        request.setAttribute("dsLopHoc", repository.findAll());
        request.getRequestDispatcher("/views/lophoc/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        LopHoc lop = new LopHoc();
        if (idParam != null && !idParam.isBlank()) {
            lop.setId(Integer.parseInt(idParam));
        }
        lop.setMaLop(request.getParameter("maLop"));
        lop.setTenLop(request.getParameter("tenLop"));

        try {
            if (lop.getId() == null) {
                if (repository.existsByMaLop(lop.getMaLop())) {
                    request.setAttribute("loi", "Ma lop da ton tai.");
                    request.setAttribute("lopHoc", lop);
                    request.getRequestDispatcher("/views/lophoc/form.jsp").forward(request, response);
                    return;
                }
                repository.save(lop);
            } else {
                repository.update(lop);
            }
            response.sendRedirect(request.getContextPath() + "/lop-hoc?thanhCong=Luu+thanh+cong");
        } catch (RuntimeException ex) {
            request.setAttribute("loi", "Da xay ra loi khi luu du lieu: " + ex.getMessage());
            request.setAttribute("lopHoc", lop);
            request.getRequestDispatcher("/views/lophoc/form.jsp").forward(request, response);
        }
    }
}
