package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Bài 2: Quản lý sản phẩm - CRUD + tìm kiếm theo tên.
 */
public class SanPhamPanel extends JPanel {

    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    private JTextField txtMaSp;
    private JTextField txtTenSp;
    private JTextField txtDonGia;
    private JTextField txtSoLuong;
    private JTextField txtTimKiem;

    private JTable table;
    private DefaultTableModel tableModel;

    public SanPhamPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaSp = new JTextField();
        txtMaSp.setEditable(false);
        txtTenSp = new JTextField();
        txtDonGia = new JTextField();
        txtSoLuong = new JTextField();
        txtTimKiem = new JTextField();

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Mã SP:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtMaSp, gbc);
        gbc.gridx = 2; gbc.gridy = 0; panel.add(new JLabel("Tên SP:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; panel.add(txtTenSp, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(txtDonGia, gbc);
        gbc.gridx = 2; gbc.gridy = 1; panel.add(new JLabel("Số lượng:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; panel.add(txtSoLuong, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Tìm theo tên:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(txtTimKiem, gbc);
        gbc.gridwidth = 1;

        JButton btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.addActionListener(e -> timKiem());
        gbc.gridx = 3; gbc.gridy = 2; panel.add(btnTimKiem, gbc);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        tableModel = new DefaultTableModel(
                new Object[]{"Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });
        return new JScrollPane(table);
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        btnThem.addActionListener(e -> themSanPham());
        btnSua.addActionListener(e -> suaSanPham());
        btnXoa.addActionListener(e -> xoaSanPham());
        btnLamMoi.addActionListener(e -> {
            clearForm();
            loadData();
        });

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLamMoi);
        return panel;
    }

    public void loadData() {
        try {
            List<SanPham> list = sanPhamBUS.findAll();
            fillTable(list);
        } catch (SQLException e) {
            MessageUtil.showError(this, "Lỗi tải danh sách sản phẩm: " + e.getMessage());
        }
    }

    private void fillTable(List<SanPham> list) {
        tableModel.setRowCount(0);
        for (SanPham sp : list) {
            tableModel.addRow(new Object[]{
                    sp.getMaSp(), sp.getTenSp(), sp.getDonGia(), sp.getSoLuong()
            });
        }
    }

    private void timKiem() {
        try {
            String keyword = txtTimKiem.getText().trim();
            List<SanPham> list = keyword.isEmpty()
                    ? sanPhamBUS.findAll()
                    : sanPhamBUS.searchByName(keyword);
            fillTable(list);
        } catch (SQLException e) {
            MessageUtil.showError(this, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    private void themSanPham() {
        try {
            SanPham sp = new SanPham();
            sp.setMaSp(0); // 0 => insert
            sp.setTenSp(txtTenSp.getText().trim());
            sp.setDonGia(parseBigDecimal(txtDonGia.getText()));
            sp.setSoLuong(parseInt(txtSoLuong.getText()));

            sanPhamBUS.save(sp);
            MessageUtil.showInfo(this, "Thêm sản phẩm thành công!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException ex) {
            MessageUtil.showWarning(this, ex.getMessage());
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Lỗi CSDL: " + ex.getMessage());
        }
    }

    private void suaSanPham() {
        try {
            if (txtMaSp.getText().isEmpty()) {
                MessageUtil.showWarning(this, "Vui lòng chọn sản phẩm cần sửa trong bảng!");
                return;
            }
            SanPham sp = new SanPham();
            sp.setMaSp(Integer.parseInt(txtMaSp.getText()));
            sp.setTenSp(txtTenSp.getText().trim());
            sp.setDonGia(parseBigDecimal(txtDonGia.getText()));
            sp.setSoLuong(parseInt(txtSoLuong.getText()));

            sanPhamBUS.save(sp);
            MessageUtil.showInfo(this, "Cập nhật sản phẩm thành công!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException ex) {
            MessageUtil.showWarning(this, ex.getMessage());
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Lỗi CSDL: " + ex.getMessage());
        }
    }

    private void xoaSanPham() {
        try {
            if (txtMaSp.getText().isEmpty()) {
                MessageUtil.showWarning(this, "Vui lòng chọn sản phẩm cần xóa trong bảng!");
                return;
            }
            if (!MessageUtil.confirm(this, "Bạn có chắc muốn xóa sản phẩm này?")) return;

            int maSp = Integer.parseInt(txtMaSp.getText());
            sanPhamBUS.delete(maSp);
            MessageUtil.showInfo(this, "Xóa sản phẩm thành công!");
            clearForm();
            loadData();
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Không thể xóa (có thể sản phẩm đã có trong hóa đơn): " + ex.getMessage());
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        txtMaSp.setText(tableModel.getValueAt(row, 0).toString());
        txtTenSp.setText(tableModel.getValueAt(row, 1).toString());
        txtDonGia.setText(tableModel.getValueAt(row, 2).toString());
        txtSoLuong.setText(tableModel.getValueAt(row, 3).toString());
    }

    private void clearForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        table.clearSelection();
    }

    private BigDecimal parseBigDecimal(String text) {
        try {
            return new BigDecimal(text.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Đơn giá không hợp lệ");
        }
    }

    private int parseInt(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Số lượng không hợp lệ");
        }
    }
}
