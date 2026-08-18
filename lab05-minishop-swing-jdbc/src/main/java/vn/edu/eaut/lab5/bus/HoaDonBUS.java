package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.HoaDonDAL;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.sql.SQLException;
import java.util.List;

public class HoaDonBUS {

    private final HoaDonDAL hoaDonDAL = new HoaDonDAL();

    public int save(int maKh, List<ChiTietHoaDon> chiTietList) throws SQLException {
        validate(maKh, chiTietList);
        return hoaDonDAL.insertHoaDon(maKh, chiTietList);
    }

    public List<HoaDon> findAll() throws SQLException {
        return hoaDonDAL.findAll();
    }

    public List<ChiTietHoaDon> findChiTietByMaHd(int maHd) throws SQLException {
        return hoaDonDAL.findChiTietByMaHd(maHd);
    }

    private void validate(int maKh, List<ChiTietHoaDon> chiTietList) {
        if (maKh <= 0) {
            throw new IllegalArgumentException("Vui long chon khach hang");
        }
        if (chiTietList == null || chiTietList.isEmpty()) {
            throw new IllegalArgumentException("Hoa don phai co it nhat 1 san pham");
        }
        for (ChiTietHoaDon ct : chiTietList) {
            if (ct.getSoLuong() <= 0) {
                throw new IllegalArgumentException("So luong san pham '" + ct.getTenSp() + "' phai lon hon 0");
            }
        }
    }
}
