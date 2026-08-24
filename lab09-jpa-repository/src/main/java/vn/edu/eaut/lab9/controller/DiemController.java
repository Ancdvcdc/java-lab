package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.Diem;
import vn.edu.eaut.lab9.model.MonHoc;
import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.repository.DiemRepository;
import vn.edu.eaut.lab9.repository.MonHocRepository;
import vn.edu.eaut.lab9.repository.SinhVienRepository;

import java.io.IOException;

@WebServlet("/diem")
public class DiemController extends HttpServlet {

    private final DiemRepository repository = new DiemRepository();
    private final SinhVienRepository sinhVienRepository = new SinhVienRepository();
    private final MonHocRepository monHocRepository = new MonHocRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            repository.delete(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect(request.getContextPath() + "/diem?thanhCong=Da+xoa+diem");
            return;
        }

        if ("form".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isBlank()) {
                request.setAttribute("diem", repository.findById(Integer.parseInt(idParam)));
            }
            request.setAttribute("danhSachSV", sinhVienRepository.findAll());
            request.setAttribute("danhSachMon", monHocRepository.findAll());
            request.getRequestDispatcher("/views/diem/form.jsp").forward(request, response);
            return;
        }

        if ("bangDiem".equals(action)) {
            Integer svId = Integer.parseInt(request.getParameter("svId"));
            SinhVien sv = sinhVienRepository.findById(svId);
            request.setAttribute("sinhVien", sv);
            request.setAttribute("dsDiem", repository.findBySinhVien(svId));
            Double dtb = repository.diemTrungBinh(svId);
            request.setAttribute("diemTrungBinh", dtb);
            request.setAttribute("xepLoai", dtb == null ? "Chua co diem" : Diem.xepLoai(dtb));
            request.getRequestDispatcher("/views/diem/bang-diem.jsp").forward(request, response);
            return;
        }

        request.setAttribute("dsDiem", repository.findAll());
        request.getRequestDispatcher("/views/diem/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        Diem diem = new Diem();
        if (idParam != null && !idParam.isBlank()) {
            diem.setId(Integer.parseInt(idParam));
        }

        SinhVien sv = new SinhVien();
        sv.setId(Integer.parseInt(request.getParameter("sinhVienId")));
        diem.setSinhVien(sv);

        MonHoc mh = new MonHoc();
        mh.setId(Integer.parseInt(request.getParameter("monHocId")));
        diem.setMonHoc(mh);

        diem.setDiemSo(Double.parseDouble(request.getParameter("diemSo")));

        try {
            if (diem.getId() == null) {
                if (repository.existsBySinhVienAndMonHoc(sv.getId(), mh.getId())) {
                    request.setAttribute("loi", "Sinh vien nay da co diem cho mon hoc nay.");
                    request.setAttribute("diem", diem);
                    request.setAttribute("danhSachSV", sinhVienRepository.findAll());
                    request.setAttribute("danhSachMon", monHocRepository.findAll());
                    request.getRequestDispatcher("/views/diem/form.jsp").forward(request, response);
                    return;
                }
                repository.save(diem);
            } else {
                repository.update(diem);
            }
            response.sendRedirect(request.getContextPath() + "/diem?thanhCong=Luu+diem+thanh+cong");
        } catch (RuntimeException ex) {
            request.setAttribute("loi", "Da xay ra loi khi luu du lieu: " + ex.getMessage());
            request.setAttribute("diem", diem);
            request.setAttribute("danhSachSV", sinhVienRepository.findAll());
            request.setAttribute("danhSachMon", monHocRepository.findAll());
            request.getRequestDispatcher("/views/diem/form.jsp").forward(request, response);
        }
    }
}
