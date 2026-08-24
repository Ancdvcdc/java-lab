package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;

/** Bai 13: Module Sach - chuyen tu List trong bo nho sang luu tru bang JPA. */
@Entity
@Table(name = "sach")
public class Sach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_sach", nullable = false, unique = true, length = 20)
    private String maSach;

    @Column(name = "ten_sach", nullable = false, length = 150)
    private String tenSach;

    @Column(name = "tac_gia", length = 100)
    private String tacGia;

    @Column(name = "so_luong")
    private Integer soLuong;

    public Sach() {
    }

    public Sach(String maSach, String tenSach, String tacGia, Integer soLuong) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.soLuong = soLuong;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
