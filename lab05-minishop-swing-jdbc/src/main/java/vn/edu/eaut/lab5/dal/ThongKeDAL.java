package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.HoaDon;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class ThongKeDAL {

    /** Tính tổng doanh thu trong khoảng ngày [tuNgay, denNgay] */
    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        String sql = "SELECT COALESCE(SUM(tong_tien), 0) AS doanh_thu " +
                "FROM hoa_don WHERE ngay_lap BETWEEN ? AND ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(tuNgay));
            ps.setDate(2, Date.valueOf(denNgay));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("doanh_thu");
                }
            }
        }
        return BigDecimal.ZERO;
    }

    /** Lấy hóa đơn có giá trị cao nhất */
    public HoaDon hoaDonCaoNhat() throws SQLException {
        String sql = "SELECT hd.ma_hd, hd.ngay_lap, hd.ma_kh, hd.tong_tien, kh.ten_kh " +
                "FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh = kh.ma_kh " +
                "ORDER BY hd.tong_tien DESC LIMIT 1";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHd(rs.getInt("ma_hd"));
                hd.setNgayLap(rs.getDate("ngay_lap").toLocalDate());
                hd.setMaKh(rs.getInt("ma_kh"));
                hd.setTenKh(rs.getString("ten_kh"));
                hd.setTongTien(rs.getBigDecimal("tong_tien"));
                return hd;
            }
        }
        return null;
    }

    /** Kết quả sản phẩm bán chạy nhất: tên sản phẩm + tổng số lượng bán */
    public static class SanPhamBanChay {
        public String tenSp;
        public int tongSoLuong;
    }

    public SanPhamBanChay sanPhamBanChayNhat() throws SQLException {
        String sql = "SELECT sp.ten_sp, SUM(ct.so_luong) AS tong_so_luong " +
                "FROM chi_tiet_hoa_don ct JOIN san_pham sp ON ct.ma_sp = sp.ma_sp " +
                "GROUP BY sp.ma_sp, sp.ten_sp " +
                "ORDER BY tong_so_luong DESC LIMIT 1";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                SanPhamBanChay kq = new SanPhamBanChay();
                kq.tenSp = rs.getString("ten_sp");
                kq.tongSoLuong = rs.getInt("tong_so_luong");
                return kq;
            }
        }
        return null;
    }
}
