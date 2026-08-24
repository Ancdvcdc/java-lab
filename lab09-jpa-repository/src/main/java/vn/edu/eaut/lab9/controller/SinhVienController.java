package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.LopHoc;
import vn.edu.eaut.lab9.model.MonHoc;
import vn.edu.eaut.lab9.model.SinhVien;
import vn.edu.eaut.lab9.repository.LopHocRepository;
import vn.edu.eaut.lab9.repository.MonHocRepository;
import vn.edu.eaut.lab9.repository.SinhVienRepository;
import vn.edu.eaut.lab9.service.SinhVienService;
import vn.edu.eaut.lab9.service.ValidationException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/sinh-vien")
public class SinhVienController extends HttpServlet {

    private static final int PAGE_SIZE = 5;

    private final SinhVienRepository repository = new SinhVienRepository();
    private final LopHocRepository lopHocRepository = new LopHocRepository();
    private final MonHocRepository monHocRepository = new MonHocRepository();
    private final SinhVienService service = new SinhVienService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            try {
                service.xoa(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect(request.getContextPath() + "/sinh-vien?thanhCong=Da+xoa+sinh+vien");
            } catch (RuntimeException ex) {
                response.sendRedirect(request.getContextPath()
                        + "/sinh-vien?loi=Khong+the+xoa+sinh+vien+nay+do+loi+du+lieu");
            }
            return;
        }

        if ("form".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isBlank()) {
                request.setAttribute("sinhVien", repository.findById(Integer.parseInt(idParam)));
            }
            request.setAttribute("danhSachLop", lopHocRepository.findAll());
            request.setAttribute("danhSachMon", monHocRepository.findAll());
            request.getRequestDispatcher("/views/sinhvien/form.jsp").forward(request, response);
            return;
        }

        // Danh sach + tim kiem + phan trang (Bai 9)
        String keyword = request.getParameter("keyword");
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException ignored) {
        }
        if (page < 1) page = 1;

        List<SinhVien> danhSach;
        long tongSo;
        if (keyword == null || keyword.isBlank()) {
            keyword = "";
            danhSach = repository.search("", page, PAGE_SIZE);
            tongSo = repository.countSearch("");
        } else {
            danhSach = repository.search(keyword, page, PAGE_SIZE);
            tongSo = repository.countSearch(keyword);
        }
        int tongSoTrang = (int) Math.max(1, Math.ceil((double) tongSo / PAGE_SIZE));

        request.setAttribute("dsSinhVien", danhSach);
        request.setAttribute("keyword", keyword);
        request.setAttribute("page", page);
        request.setAttribute("tongSoTrang", tongSoTrang);
        request.getRequestDispatcher("/views/sinhvien/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        SinhVien sv = new SinhVien();
        if (idParam != null && !idParam.isBlank()) {
            sv.setId(Integer.parseInt(idParam));
        }
        sv.setMaSinhVien(request.getParameter("maSinhVien"));
        sv.setHoTen(request.getParameter("hoTen"));
        sv.setEmail(request.getParameter("email"));

        String ngaySinhStr = request.getParameter("ngaySinh");
        if (ngaySinhStr != null && !ngaySinhStr.isBlank()) {
            sv.setNgaySinh(LocalDate.parse(ngaySinhStr));
        }

        String lopHocId = request.getParameter("lopHocId");
        if (lopHocId != null && !lopHocId.isBlank()) {
            LopHoc lop = new LopHoc();
            lop.setId(Integer.parseInt(lopHocId));
            sv.setLopHoc(lop);
        }

        try {
            if (sv.getId() == null) {
                // Bai 11: neu nguoi dung tick "khoi tao diem mac dinh", dung transaction gop
                boolean khoiTaoDiem = "on".equals(request.getParameter("khoiTaoDiem"));
                if (khoiTaoDiem) {
                    List<MonHoc> danhSachMon = monHocRepository.findAll();
                    service.themMoiVaKhoiTaoDiem(sv, danhSachMon);
                    response.sendRedirect(request.getContextPath()
                            + "/sinh-vien?thanhCong=Them+sinh+vien+va+khoi+tao+diem+thanh+cong");
                } else {
                    service.themMoi(sv);
                    response.sendRedirect(request.getContextPath()
                            + "/sinh-vien?thanhCong=Them+sinh+vien+thanh+cong");
                }
            } else {
                service.capNhat(sv);
                response.sendRedirect(request.getContextPath()
                        + "/sinh-vien?thanhCong=Cap+nhat+thanh+cong");
            }
        } catch (ValidationException ex) {
            // Bai 10: hien thi loi ro rang, khong lam mat du lieu da nhap
            request.setAttribute("loi", ex.getMessage());
            request.setAttribute("sinhVien", sv);
            request.setAttribute("danhSachLop", lopHocRepository.findAll());
            request.setAttribute("danhSachMon", monHocRepository.findAll());
            request.getRequestDispatcher("/views/sinhvien/form.jsp").forward(request, response);
        } catch (RuntimeException ex) {
            // Transaction da tu rollback trong Repository/Service, chi hien thi thong bao loi
            request.setAttribute("loi", "Da xay ra loi khi luu du lieu, toan bo thao tac da duoc rollback: "
                    + ex.getMessage());
            request.setAttribute("sinhVien", sv);
            request.setAttribute("danhSachLop", lopHocRepository.findAll());
            request.setAttribute("danhSachMon", monHocRepository.findAll());
            request.getRequestDispatcher("/views/sinhvien/form.jsp").forward(request, response);
        }
    }
}
