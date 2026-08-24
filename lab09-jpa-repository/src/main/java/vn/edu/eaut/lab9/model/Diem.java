package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;

/**
 * Bai 7: Diem gan voi 1 SinhVien va 1 MonHoc. Diem theo thang 10.
 */
@Entity
@Table(name = "diem")
public class Diem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "diem_so")
    private Double diemSo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sinh_vien_id", nullable = false)
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mon_hoc_id", nullable = false)
    private MonHoc monHoc;

    public Diem() {
    }

    public Diem(SinhVien sinhVien, MonHoc monHoc, Double diemSo) {
        this.sinhVien = sinhVien;
        this.monHoc = monHoc;
        this.diemSo = diemSo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDiemSo() {
        return diemSo;
    }

    public void setDiemSo(Double diemSo) {
        this.diemSo = diemSo;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    /** Xep loai theo diem so (thang 10), dung chung o Service/JSP. */
    public static String xepLoai(double diem) {
        if (diem >= 8.5) return "Gioi";
        if (diem >= 7.0) return "Kha";
        if (diem >= 5.5) return "Trung binh kha";
        if (diem >= 4.0) return "Trung binh";
        return "Yeu";
    }
}
