package vn.edu.eaut.lab9.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab9.model.*;
import vn.edu.eaut.lab9.repository.*;

/**
 * Bai 12: tao du lieu mau khi CSDL con rong, giup demo nhanh khong can nhap tay.
 */
@WebListener
public class DataSeedListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LopHocRepository lopHocRepository = new LopHocRepository();
        SinhVienRepository sinhVienRepository = new SinhVienRepository();
        MonHocRepository monHocRepository = new MonHocRepository();
        DiemRepository diemRepository = new DiemRepository();
        SachRepository sachRepository = new SachRepository();
        SanPhamRepository sanPhamRepository = new SanPhamRepository();

        if (!lopHocRepository.findAll().isEmpty()) {
            return; // Da co du lieu, khong seed lai
        }

        LopHoc lop1 = new LopHoc("IT01", "Cong nghe phan mem K17");
        LopHoc lop2 = new LopHoc("IT02", "He thong thong tin K17");
        lopHocRepository.save(lop1);
        lopHocRepository.save(lop2);

        SinhVien sv1 = new SinhVien("SV001", "Nguyen Van A", "a@example.com", lop1);
        SinhVien sv2 = new SinhVien("SV002", "Tran Thi B", "b@example.com", lop1);
        SinhVien sv3 = new SinhVien("SV003", "Le Van C", "c@example.com", lop2);
        sinhVienRepository.save(sv1);
        sinhVienRepository.save(sv2);
        sinhVienRepository.save(sv3);

        // Bai 7: Mon hoc mau
        MonHoc mh1 = new MonHoc("IT101", "Lap trinh Java", 3);
        MonHoc mh2 = new MonHoc("IT102", "Co so du lieu", 3);
        monHocRepository.save(mh1);
        monHocRepository.save(mh2);

        // Bai 7: Diem mau
        diemRepository.save(new Diem(sv1, mh1, 8.5));
        diemRepository.save(new Diem(sv1, mh2, 7.0));
        diemRepository.save(new Diem(sv2, mh1, 6.0));

        // Bai 13: Sach mau
        sachRepository.save(new Sach("S001", "Clean Code", "Robert C. Martin", 5));
        sachRepository.save(new Sach("S002", "Effective Java", "Joshua Bloch", 3));

        // Bai 13: San pham mau
        sanPhamRepository.save(new SanPham("SP001", "Chuot khong day", 250000.0, 20));
        sanPhamRepository.save(new SanPham("SP002", "Ban phim co", 890000.0, 10));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        vn.edu.eaut.lab9.config.JPAUtil.close();
    }
}

