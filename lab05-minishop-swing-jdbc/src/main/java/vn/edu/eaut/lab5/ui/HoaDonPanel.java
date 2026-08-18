package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Bài 4: Lập hóa đơn và chi tiết hóa đơn.
 * Chọn khách hàng, chọn sản phẩm, nhập số lượng, thêm dòng chi tiết,
 * tính tổng tiền và lưu hóa đơn cùng chi tiết hóa đơn (transaction trong HoaDonDAL).
 */
public class HoaDonPanel extends JPanel {

    private final HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    private JComboBox<KhachHang> cboKhachHang;
    private JComboBox<SanPham> cboSanPham;
    private JTextField txtSoLuong;
    private JLabel lblTongTien;

    private JTable tblChiTiet;
    private DefaultTableModel chiTietModel;
    private final List<ChiTietHoaDon> chiTietTam = new ArrayList<>();

    private JTable tblHoaDon;
    private DefaultTableModel hoaDonModel;

    /** Callback để MainFrame refresh lại tab Sản phẩm sau khi lưu hóa đơn (đã trừ kho) */
    private Runnable onHoaDonSaved;

    public HoaDonPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                buildChiTietPanel(), buildDanhSachHoaDonPanel());
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        loadComboData();
        loadHoaDonList();
    }

    public void setOnHoaDonSaved(Runnable onHoaDonSaved) {
        this.onHoaDonSaved = onHoaDonSaved;
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lập hóa đơn"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cboKhachHang = new JComboBox<>();
        cboSanPham = new JComboBox<>();
        txtSoLuong = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Khách hàng:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(cboKhachHang, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Sản phẩm:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; panel.add(cboSanPham, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(txtSoLuong, gbc);

        JButton btnThemDong = new JButton("Thêm dòng");
        btnThemDong.addActionListener(e -> themDongChiTiet());
        gbc.gridx = 2; gbc.gridy = 2; panel.add(btnThemDong, gbc);

        return panel;
    }

    private JPanel buildChiTietPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Chi tiết hóa đơn (tạm)"));

        chiTietModel = new DefaultTableModel(
                new Object[]{"Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblChiTiet = new JTable(chiTietModel);
        tblChiTiet.setRowHeight(22);
        panel.add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnXoaDong = new JButton("Xóa dòng đã chọn");
        JButton btnLuuHoaDon = new JButton("Lưu hóa đơn");
        JButton btnLamMoi = new JButton("Làm mới");

        btnXoaDong.addActionListener(e -> xoaDongChiTiet());
        btnLuuHoaDon.addActionListener(e -> luuHoaDon());
        btnLamMoi.addActionListener(e -> lamMoiPhieuTam());

        buttonPanel.add(btnXoaDong);
        buttonPanel.add(btnLuuHoaDon);
        buttonPanel.add(btnLamMoi);

        lblTongTien = new JLabel("Tổng tiền: 0 VND");
        lblTongTien.setFont(lblTongTien.getFont().deriveFont(Font.BOLD, 14f));
        JPanel tongTienPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tongTienPanel.add(lblTongTien);

        southPanel.add(buttonPanel, BorderLayout.WEST);
        southPanel.add(tongTienPanel, BorderLayout.EAST);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildDanhSachHoaDonPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn đã lập"));

        hoaDonModel = new DefaultTableModel(
                new Object[]{"Mã HĐ", "Ngày lập", "Khách hàng", "Tổng tiền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblHoaDon = new JTable(hoaDonModel);
        tblHoaDon.setRowHeight(22);
        panel.add(new JScrollPane(tblHoaDon), BorderLayout.CENTER);

        JButton btnLamMoiDanhSach = new JButton("Làm mới danh sách");
        btnLamMoiDanhSach.addActionListener(e -> loadHoaDonList());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south.add(btnLamMoiDanhSach);
        panel.add(south, BorderLayout.SOUTH);

        return panel;
    }

    private void loadComboData() {
        try {
            cboKhachHang.removeAllItems();
            for (KhachHang kh : khachHangBUS.findAll()) {
                cboKhachHang.addItem(kh);
            }
            cboSanPham.removeAllItems();
            for (SanPham sp : sanPhamBUS.findAll()) {
                cboSanPham.addItem(sp);
            }
        } catch (SQLException e) {
            MessageUtil.showError(this, "Lỗi tải dữ liệu khách hàng / sản phẩm: " + e.getMessage());
        }
    }

    private void loadHoaDonList() {
        try {
            hoaDonModel.setRowCount(0);
            List<HoaDon> list = hoaDonBUS.findAll();
            for (HoaDon hd : list) {
                hoaDonModel.addRow(new Object[]{
                        hd.getMaHd(), hd.getNgayLap(), hd.getTenKh(), hd.getTongTien()
                });
            }
        } catch (SQLException e) {
            MessageUtil.showError(this, "Lỗi tải danh sách hóa đơn: " + e.getMessage());
        }
    }

    private void themDongChiTiet() {
        SanPham sp = (SanPham) cboSanPham.getSelectedItem();
        if (sp == null) {
            MessageUtil.showWarning(this, "Vui lòng chọn sản phẩm!");
            return;
        }
        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            if (soLuong <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            MessageUtil.showWarning(this, "Số lượng phải là số nguyên dương!");
            return;
        }
        if (soLuong > sp.getSoLuong()) {
            MessageUtil.showWarning(this, "Số lượng bán vượt quá tồn kho (còn " + sp.getSoLuong() + ")!");
            return;
        }

        ChiTietHoaDon ct = new ChiTietHoaDon(sp.getMaSp(), sp.getTenSp(), soLuong, sp.getDonGia());
        chiTietTam.add(ct);
        chiTietModel.addRow(new Object[]{
                ct.getMaSp(), ct.getTenSp(), ct.getSoLuong(), ct.getDonGia(), ct.getThanhTien()
        });

        txtSoLuong.setText("");
        capNhatTongTien();
    }

    private void xoaDongChiTiet() {
        int row = tblChiTiet.getSelectedRow();
        if (row < 0) {
            MessageUtil.showWarning(this, "Vui lòng chọn dòng cần xóa!");
            return;
        }
        chiTietTam.remove(row);
        chiTietModel.removeRow(row);
        capNhatTongTien();
    }

    private void capNhatTongTien() {
        BigDecimal tongTien = BigDecimal.ZERO;
        for (ChiTietHoaDon ct : chiTietTam) {
            tongTien = tongTien.add(ct.getThanhTien());
        }
        lblTongTien.setText("Tổng tiền: " + tongTien + " VND");
    }

    private void luuHoaDon() {
        try {
            KhachHang kh = (KhachHang) cboKhachHang.getSelectedItem();
            if (kh == null) {
                MessageUtil.showWarning(this, "Vui lòng chọn khách hàng!");
                return;
            }
            if (chiTietTam.isEmpty()) {
                MessageUtil.showWarning(this, "Vui lòng thêm ít nhất 1 sản phẩm vào hóa đơn!");
                return;
            }
            if (!MessageUtil.confirm(this, "Xác nhận lưu hóa đơn?")) return;

            int maHd = hoaDonBUS.save(kh.getMaKh(), chiTietTam);
            MessageUtil.showInfo(this, "Lưu hóa đơn thành công! Mã hóa đơn: " + maHd);

            lamMoiPhieuTam();
            loadHoaDonList();
            loadComboData(); // refresh lại tồn kho trong combobox sản phẩm

            if (onHoaDonSaved != null) onHoaDonSaved.run();
        } catch (IllegalArgumentException ex) {
            MessageUtil.showWarning(this, ex.getMessage());
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Lỗi lưu hóa đơn: " + ex.getMessage());
        }
    }

    private void lamMoiPhieuTam() {
        chiTietTam.clear();
        chiTietModel.setRowCount(0);
        txtSoLuong.setText("");
        capNhatTongTien();
    }
}
