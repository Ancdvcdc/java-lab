package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Bai 6: Entity LopHoc, quan he 1 - nhieu voi SinhVien (mot lop co nhieu sinh vien).
 */
@Entity
@Table(name = "lop_hoc")
public class LopHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_lop", nullable = false, unique = true, length = 20)
    private String maLop;

    @Column(name = "ten_lop", nullable = false, length = 100)
    private String tenLop;

    // Chi cascade PERSIST/MERGE, KHONG cascade REMOVE: xoa 1 lop khong duoc phep
    // vo tinh xoa luon toan bo sinh vien trong lop (du lieu quan trong, phai xu ly co chu dich).
    @OneToMany(mappedBy = "lopHoc", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private List<SinhVien> danhSachSinhVien = new ArrayList<>();

    public LopHoc() {
    }

    public LopHoc(String maLop, String tenLop) {
        this.maLop = maLop;
        this.tenLop = tenLop;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public List<SinhVien> getDanhSachSinhVien() {
        return danhSachSinhVien;
    }

    public void setDanhSachSinhVien(List<SinhVien> danhSachSinhVien) {
        this.danhSachSinhVien = danhSachSinhVien;
    }

    @Override
    public String toString() {
        return maLop + " - " + tenLop;
    }
}
