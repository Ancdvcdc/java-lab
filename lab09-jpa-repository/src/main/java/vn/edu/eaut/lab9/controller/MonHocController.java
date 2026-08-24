package vn.edu.eaut.lab9.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab9.model.MonHoc;
import vn.edu.eaut.lab9.repository.DiemRepository;
import vn.edu.eaut.lab9.repository.MonHocRepository;

import java.io.IOException;

@WebServlet("/mon-hoc")
public class MonHocController extends HttpServlet {

    private final MonHocRepository repository = new MonHocRepository();
    private final DiemRepository diemRepository = new DiemRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            // Chan xoa neu mon hoc da co diem, tranh loi ConstraintViolationException (fk_diem_monhoc)
            long soDiem = diemRepository.countByMonHoc(id);
            if (soDiem > 0) {
                response.sendRedirect(request.getContextPath()
                        + "/mon-hoc?loi=Khong+the+xoa+mon+hoc+da+co+" + soDiem
                        + "+diem.+Hay+xoa+diem+lien+quan+truoc.");
                return;
            }
            try {
                repository.delete(id);
                response.sendRedirect(request.getContextPath() + "/mon-hoc?thanhCong=Da+xoa+mon+hoc");
            } catch (RuntimeException ex) {
                response.sendRedirect(request.getContextPath()
                        + "/mon-hoc?loi=Khong+the+xoa+mon+hoc+nay+do+loi+du+lieu");
            }
            return;
        }

        if ("form".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isBlank()) {
                request.setAttribute("monHoc", repository.findById(Integer.parseInt(idParam)));
            }
            request.getRequestDispatcher("/views/monhoc/form.jsp").forward(request, response);
            return;
        }

        request.setAttribute("dsMonHoc", repository.findAll());
        request.getRequestDispatcher("/views/monhoc/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        MonHoc mh = new MonHoc();
        if (idParam != null && !idParam.isBlank()) {
            mh.setId(Integer.parseInt(idParam));
        }
        mh.setMaMonHoc(request.getParameter("maMonHoc"));
        mh.setTenMonHoc(request.getParameter("tenMonHoc"));
        String soTinChiStr = request.getParameter("soTinChi");
        mh.setSoTinChi(soTinChiStr == null || soTinChiStr.isBlank() ? null : Integer.parseInt(soTinChiStr));

        try {
            if (mh.getId() == null) {
                if (repository.existsByMaMonHoc(mh.getMaMonHoc())) {
                    request.setAttribute("loi", "Ma mon hoc da ton tai.");
                    request.setAttribute("monHoc", mh);
                    request.getRequestDispatcher("/views/monhoc/form.jsp").forward(request, response);
                    return;
                }
                repository.save(mh);
            } else {
                repository.update(mh);
            }
            response.sendRedirect(request.getContextPath() + "/mon-hoc?thanhCong=Luu+thanh+cong");
        } catch (RuntimeException ex) {
            request.setAttribute("loi", "Da xay ra loi khi luu du lieu: " + ex.getMessage());
            request.setAttribute("monHoc", mh);
            request.getRequestDispatcher("/views/monhoc/form.jsp").forward(request, response);
        }
    }
}
