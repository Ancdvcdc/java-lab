package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;

/** Bai 13: Module San pham - chuyen tu List trong bo nho sang luu tru bang JPA. */
@Entity
@Table(name = "san_pham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_san_pham", nullable = false, unique = true, length = 20)
    private String maSanPham;

    @Column(name = "ten_san_pham", nullable = false, length = 150)
    private String tenSanPham;

    private Double gia;

    @Column(name = "so_luong")
    private Integer soLuong;

    public SanPham() {
    }

    public SanPham(String maSanPham, String tenSanPham, Double gia, Integer soLuong) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
