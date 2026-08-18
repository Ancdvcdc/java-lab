package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAL {

    /**
     * Lưu hóa đơn + chi tiết hóa đơn trong 1 transaction.
     * Đồng thời trừ số lượng tồn kho tương ứng.
     */
    public int insertHoaDon(int maKh, List<ChiTietHoaDon> chiTietList) throws SQLException {
        String sqlHoaDon = "INSERT INTO hoa_don(ngay_lap, ma_kh, tong_tien) VALUES (?, ?, ?)";
        String sqlChiTiet =
                "INSERT INTO chi_tiet_hoa_don(ma_hd, ma_sp, so_luong, don_gia, thanh_tien) " +
                "VALUES (?, ?, ?, ?, ?)";
        String sqlTruKho = "UPDATE san_pham SET so_luong = so_luong - ? WHERE ma_sp = ?";

        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
            conn.setAutoCommit(false);

            BigDecimal tongTien = BigDecimal.ZERO;
            for (ChiTietHoaDon ct : chiTietList) {
                tongTien = tongTien.add(ct.getThanhTien());
            }

            int maHd;
            try (PreparedStatement ps = conn.prepareStatement(sqlHoaDon, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDate(1, Date.valueOf(LocalDate.now()));
                ps.setInt(2, maKh);
                ps.setBigDecimal(3, tongTien);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        maHd = rs.getInt(1);
                    } else {
                        throw new SQLException("Khong lay duoc ma hoa don");
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlChiTiet)) {
                for (ChiTietHoaDon ct : chiTietList) {
                    ps.setInt(1, maHd);
                    ps.setInt(2, ct.getMaSp());
                    ps.setInt(3, ct.getSoLuong());
                    ps.setBigDecimal(4, ct.getDonGia());
                    ps.setBigDecimal(5, ct.getThanhTien());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlTruKho)) {
                for (ChiTietHoaDon ct : chiTietList) {
                    ps.setInt(1, ct.getSoLuong());
                    ps.setInt(2, ct.getMaSp());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            return maHd;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public List<HoaDon> findAll() throws SQLException {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.ma_hd, hd.ngay_lap, hd.ma_kh, hd.tong_tien, kh.ten_kh " +
                "FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh = kh.ma_kh " +
                "ORDER BY hd.ma_hd DESC";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public List<ChiTietHoaDon> findChiTietByMaHd(int maHd) throws SQLException {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT ct.ma_hd, ct.ma_sp, sp.ten_sp, ct.so_luong, ct.don_gia, ct.thanh_tien " +
                "FROM chi_tiet_hoa_don ct JOIN san_pham sp ON ct.ma_sp = sp.ma_sp " +
                "WHERE ct.ma_hd = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHd);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDon ct = new ChiTietHoaDon();
                    ct.setMaHd(rs.getInt("ma_hd"));
                    ct.setMaSp(rs.getInt("ma_sp"));
                    ct.setTenSp(rs.getString("ten_sp"));
                    ct.setDonGia(rs.getBigDecimal("don_gia"));
                    ct.setSoLuong(rs.getInt("so_luong"));
                    ct.setThanhTien(rs.getBigDecimal("thanh_tien"));
                    list.add(ct);
                }
            }
        }
        return list;
    }

    public List<HoaDon> findByNgay(LocalDate ngay) throws SQLException {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.ma_hd, hd.ngay_lap, hd.ma_kh, hd.tong_tien, kh.ten_kh " +
                "FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh = kh.ma_kh " +
                "WHERE hd.ngay_lap = ? ORDER BY hd.ma_hd DESC";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(ngay));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    private HoaDon mapRow(ResultSet rs) throws SQLException {
        HoaDon hd = new HoaDon();
        hd.setMaHd(rs.getInt("ma_hd"));
        hd.setNgayLap(rs.getDate("ngay_lap").toLocalDate());
        hd.setMaKh(rs.getInt("ma_kh"));
        hd.setTenKh(rs.getString("ten_kh"));
        hd.setTongTien(rs.getBigDecimal("tong_tien"));
        return hd;
    }
}
