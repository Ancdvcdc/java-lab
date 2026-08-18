package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.ThongKeDAL;
import vn.edu.eaut.lab5.model.HoaDon;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThongKeBUS {

    private final ThongKeDAL thongKeDAL = new ThongKeDAL();

    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        if (tuNgay == null || denNgay == null) {
            throw new IllegalArgumentException("Vui long chon khoang ngay hop le");
        }
        if (tuNgay.isAfter(denNgay)) {
            throw new IllegalArgumentException("Tu ngay khong duoc lon hon Den ngay");
        }
        return thongKeDAL.tinhDoanhThu(tuNgay, denNgay);
    }

    public HoaDon hoaDonCaoNhat() throws SQLException {
        return thongKeDAL.hoaDonCaoNhat();
    }

    public ThongKeDAL.SanPhamBanChay sanPhamBanChayNhat() throws SQLException {
        return thongKeDAL.sanPhamBanChayNhat();
    }
}
