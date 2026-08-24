package vn.edu.eaut.lab9.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mon_hoc")
public class MonHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_mon_hoc", nullable = false, unique = true, length = 20)
    private String maMonHoc;

    @Column(name = "ten_mon_hoc", nullable = false, length = 100)
    private String tenMonHoc;

    @Column(name = "so_tin_chi")
    private Integer soTinChi;

    public MonHoc() {
    }

    public MonHoc(String maMonHoc, String tenMonHoc, Integer soTinChi) {
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.soTinChi = soTinChi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public Integer getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(Integer soTinChi) {
        this.soTinChi = soTinChi;
    }
}
