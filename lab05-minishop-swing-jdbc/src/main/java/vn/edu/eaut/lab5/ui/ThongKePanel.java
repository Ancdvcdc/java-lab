package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.ThongKeBUS;
import vn.edu.eaut.lab5.dal.ThongKeDAL;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Bài 5: Tìm kiếm và thống kê bằng SwingWorker.
 * - Tìm hóa đơn theo ngày.
 * - Tính doanh thu theo khoảng ngày.
 * - Hóa đơn có giá trị cao nhất.
 * - Sản phẩm bán chạy nhất.
 * Tất cả truy vấn thống kê chạy nền bằng SwingWorker để không treo giao diện (EDT).
 */
public class ThongKePanel extends JPanel {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ThongKeBUS thongKeBUS = new ThongKeBUS();
    private final HoaDonBUS hoaDonBUS = new HoaDonBUS();

    private JTextField txtNgayTim;
    private JTextField txtTuNgay;
    private JTextField txtDenNgay;

    private JLabel lblDoanhThu;
    private JLabel lblHoaDonCaoNhat;
    private JLabel lblSanPhamBanChay;

    private JTable tblHoaDonTheoNgay;
    private DefaultTableModel hoaDonTheoNgayModel;

    private JProgressBar progressBar;

    public ThongKePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildResultPanel(), BorderLayout.CENTER);
    }

    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm & Thống kê (định dạng ngày: yyyy-MM-dd)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNgayTim = new JTextField(LocalDate.now().format(DATE_FORMAT));
        txtTuNgay = new JTextField(LocalDate.now().withDayOfMonth(1).format(DATE_FORMAT));
        txtDenNgay = new JTextField(LocalDate.now().format(DATE_FORMAT));

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Tìm hóa đơn theo ngày:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtNgayTim, gbc);
        JButton btnTimHoaDon = new JButton("Tìm hóa đơn");
        btnTimHoaDon.addActionListener(e -> timHoaDonTheoNgay());
        gbc.gridx = 2; gbc.gridy = 0; panel.add(btnTimHoaDon, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Từ ngày:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(txtTuNgay, gbc);
        gbc.gridx = 2; gbc.gridy = 1; panel.add(new JLabel("Đến ngày:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; panel.add(txtDenNgay, gbc);

        JButton btnThongKe = new JButton("Thống kê doanh thu");
        btnThongKe.addActionListener(e -> thongKeDoanhThu());
        gbc.gridx = 4; gbc.gridy = 1; panel.add(btnThongKe, gbc);

        JButton btnHoaDonCaoNhat = new JButton("Hóa đơn cao nhất");
        btnHoaDonCaoNhat.addActionListener(e -> timHoaDonCaoNhat());
        gbc.gridx = 0; gbc.gridy = 2; panel.add(btnHoaDonCaoNhat, gbc);

        JButton btnSpBanChay = new JButton("Sản phẩm bán chạy nhất");
        btnSpBanChay.addActionListener(e -> timSanPhamBanChay());
        gbc.gridx = 1; gbc.gridy = 2; panel.add(btnSpBanChay, gbc);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setStringPainted(true);
        progressBar.setString("Sẵn sàng");
        gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 3;
        panel.add(progressBar, gbc);

        return panel;
    }

    private JPanel buildResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Kết quả thống kê"));

        lblDoanhThu = new JLabel("Doanh thu: (chưa thống kê)");
        lblHoaDonCaoNhat = new JLabel("Hóa đơn cao nhất: (chưa thống kê)");
        lblSanPhamBanChay = new JLabel("Sản phẩm bán chạy nhất: (chưa thống kê)");

        Font font = lblDoanhThu.getFont().deriveFont(Font.PLAIN, 14f);
        lblDoanhThu.setFont(font);
        lblHoaDonCaoNhat.setFont(font);
        lblSanPhamBanChay.setFont(font);

        infoPanel.add(lblDoanhThu);
        infoPanel.add(lblHoaDonCaoNhat);
        infoPanel.add(lblSanPhamBanChay);

        hoaDonTheoNgayModel = new DefaultTableModel(
                new Object[]{"Mã HĐ", "Ngày lập", "Khách hàng", "Tổng tiền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblHoaDonTheoNgay = new JTable(hoaDonTheoNgayModel);
        JScrollPane scrollPane = new JScrollPane(tblHoaDonTheoNgay);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn theo ngày tìm kiếm"));

        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /** Tìm hóa đơn theo ngày - chạy nền bằng SwingWorker vì có thể dữ liệu lớn. */
    private void timHoaDonTheoNgay() {
        LocalDate ngay = parseDate(txtNgayTim.getText());
        if (ngay == null) return;

        setBusy(true, "Đang tìm hóa đơn...");
        new SwingWorker<List<HoaDon>, Void>() {
            @Override
            protected List<HoaDon> doInBackground() throws Exception {
                return hoaDonBUS.findAll().stream()
                        .filter(hd -> hd.getNgayLap().isEqual(ngay))
                        .toList();
            }

            @Override
            protected void done() {
                try {
                    List<HoaDon> list = get();
                    hoaDonTheoNgayModel.setRowCount(0);
                    for (HoaDon hd : list) {
                        hoaDonTheoNgayModel.addRow(new Object[]{
                                hd.getMaHd(), hd.getNgayLap(), hd.getTenKh(), hd.getTongTien()
                        });
                    }
                    if (list.isEmpty()) {
                        MessageUtil.showInfo(ThongKePanel.this, "Không có hóa đơn nào trong ngày " + ngay);
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(ThongKePanel.this, "Lỗi tìm hóa đơn: " + ex.getMessage());
                } finally {
                    setBusy(false, "Sẵn sàng");
                }
            }
        }.execute();
    }

    /** Bài 5 gợi ý: DoanhThuWorker - tính doanh thu theo khoảng ngày bằng SwingWorker. */
    private void thongKeDoanhThu() {
        LocalDate tuNgay = parseDate(txtTuNgay.getText());
        LocalDate denNgay = parseDate(txtDenNgay.getText());
        if (tuNgay == null || denNgay == null) return;

        setBusy(true, "Đang tính doanh thu...");
        new SwingWorker<BigDecimal, Void>() {
            @Override
            protected BigDecimal doInBackground() throws Exception {
                return thongKeBUS.tinhDoanhThu(tuNgay, denNgay);
            }

            @Override
            protected void done() {
                try {
                    BigDecimal doanhThu = get();
                    lblDoanhThu.setText("Doanh thu (" + tuNgay + " → " + denNgay + "): " + doanhThu + " VND");
                } catch (Exception ex) {
                    MessageUtil.showError(ThongKePanel.this, "Lỗi thống kê doanh thu: " + rootMessage(ex));
                } finally {
                    setBusy(false, "Sẵn sàng");
                }
            }
        }.execute();
    }

    /** Tìm hóa đơn có giá trị cao nhất - chạy nền bằng SwingWorker. */
    private void timHoaDonCaoNhat() {
        setBusy(true, "Đang tìm hóa đơn cao nhất...");
        new SwingWorker<HoaDon, Void>() {
            @Override
            protected HoaDon doInBackground() throws Exception {
                return thongKeBUS.hoaDonCaoNhat();
            }

            @Override
            protected void done() {
                try {
                    HoaDon hd = get();
                    if (hd == null) {
                        lblHoaDonCaoNhat.setText("Hóa đơn cao nhất: chưa có hóa đơn nào");
                    } else {
                        lblHoaDonCaoNhat.setText(String.format(
                                "Hóa đơn cao nhất: HĐ #%d - %s - Khách hàng: %s - Tổng tiền: %s VND",
                                hd.getMaHd(), hd.getNgayLap(), hd.getTenKh(), hd.getTongTien()));
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(ThongKePanel.this, "Lỗi tìm hóa đơn cao nhất: " + rootMessage(ex));
                } finally {
                    setBusy(false, "Sẵn sàng");
                }
            }
        }.execute();
    }

    /** Tìm sản phẩm bán chạy nhất - chạy nền bằng SwingWorker. */
    private void timSanPhamBanChay() {
        setBusy(true, "Đang tính sản phẩm bán chạy...");
        new SwingWorker<ThongKeDAL.SanPhamBanChay, Void>() {
            @Override
            protected ThongKeDAL.SanPhamBanChay doInBackground() throws Exception {
                return thongKeBUS.sanPhamBanChayNhat();
            }

            @Override
            protected void done() {
                try {
                    ThongKeDAL.SanPhamBanChay kq = get();
                    if (kq == null) {
                        lblSanPhamBanChay.setText("Sản phẩm bán chạy nhất: chưa có dữ liệu bán hàng");
                    } else {
                        lblSanPhamBanChay.setText("Sản phẩm bán chạy nhất: " + kq.tenSp
                                + " - Đã bán: " + kq.tongSoLuong + " sản phẩm");
                    }
                } catch (Exception ex) {
                    MessageUtil.showError(ThongKePanel.this, "Lỗi tìm sản phẩm bán chạy: " + rootMessage(ex));
                } finally {
                    setBusy(false, "Sẵn sàng");
                }
            }
        }.execute();
    }

    private void setBusy(boolean busy, String text) {
        progressBar.setIndeterminate(busy);
        progressBar.setString(text);
    }

    private LocalDate parseDate(String text) {
        try {
            return LocalDate.parse(text.trim(), DATE_FORMAT);
        } catch (DateTimeParseException ex) {
            MessageUtil.showWarning(this, "Ngày không hợp lệ! Định dạng đúng: yyyy-MM-dd (ví dụ 2026-08-16)");
            return null;
        }
    }

    private String rootMessage(Exception ex) {
        Throwable cause = ex.getCause();
        return cause != null ? cause.getMessage() : ex.getMessage();
    }
}
