package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.util.MessageUtil;
import vn.edu.eaut.lab5.util.PhoneDocumentFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Bài 3: Quản lý khách hàng - CRUD + validate số điện thoại
 * (validate ở BUS và chặn nhập sai trên GUI bằng DocumentFilter).
 */
public class KhachHangPanel extends JPanel {

    private final KhachHangBUS khachHangBUS = new KhachHangBUS();

    private JTextField txtMaKh;
    private JTextField txtTenKh;
    private JTextField txtSdt;
    private JTextField txtDiaChi;
    private JTextField txtTimKiem;

    private JTable table;
    private DefaultTableModel tableModel;

    public KhachHangPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaKh = new JTextField();
        txtMaKh.setEditable(false);
        txtTenKh = new JTextField();
        txtSdt = new JTextField();
        txtDiaChi = new JTextField();
        txtTimKiem = new JTextField();

        // Chặn nhập sai SĐT ngay trên GUI (chỉ số, tối đa 10 ký tự)
        ((AbstractDocument) txtSdt.getDocument()).setDocumentFilter(new PhoneDocumentFilter());

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Mã KH:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtMaKh, gbc);
        gbc.gridx = 2; gbc.gridy = 0; panel.add(new JLabel("Tên KH:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; panel.add(txtTenKh, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(txtSdt, gbc);
        gbc.gridx = 2; gbc.gridy = 1; panel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; panel.add(txtDiaChi, gbc);

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
                new Object[]{"Mã KH", "Tên khách hàng", "SĐT", "Địa chỉ"}, 0) {
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

        btnThem.addActionListener(e -> themKhachHang());
        btnSua.addActionListener(e -> suaKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());
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
            List<KhachHang> list = khachHangBUS.findAll();
            fillTable(list);
        } catch (SQLException e) {
            MessageUtil.showError(this, "Lỗi tải danh sách khách hàng: " + e.getMessage());
        }
    }

    private void fillTable(List<KhachHang> list) {
        tableModel.setRowCount(0);
        for (KhachHang kh : list) {
            tableModel.addRow(new Object[]{
                    kh.getMaKh(), kh.getTenKh(), kh.getSdt(), kh.getDiaChi()
            });
        }
    }

    private void timKiem() {
        try {
            String keyword = txtTimKiem.getText().trim();
            List<KhachHang> list = keyword.isEmpty()
                    ? khachHangBUS.findAll()
                    : khachHangBUS.searchByName(keyword);
            fillTable(list);
        } catch (SQLException e) {
            MessageUtil.showError(this, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    private void themKhachHang() {
        try {
            KhachHang kh = new KhachHang();
            kh.setMaKh(0);
            kh.setTenKh(txtTenKh.getText().trim());
            kh.setSdt(txtSdt.getText().trim());
            kh.setDiaChi(txtDiaChi.getText().trim());

            khachHangBUS.save(kh);
            MessageUtil.showInfo(this, "Thêm khách hàng thành công!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException ex) {
            MessageUtil.showWarning(this, ex.getMessage());
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Lỗi CSDL: " + ex.getMessage());
        }
    }

    private void suaKhachHang() {
        try {
            if (txtMaKh.getText().isEmpty()) {
                MessageUtil.showWarning(this, "Vui lòng chọn khách hàng cần sửa trong bảng!");
                return;
            }
            KhachHang kh = new KhachHang();
            kh.setMaKh(Integer.parseInt(txtMaKh.getText()));
            kh.setTenKh(txtTenKh.getText().trim());
            kh.setSdt(txtSdt.getText().trim());
            kh.setDiaChi(txtDiaChi.getText().trim());

            khachHangBUS.save(kh);
            MessageUtil.showInfo(this, "Cập nhật khách hàng thành công!");
            clearForm();
            loadData();
        } catch (IllegalArgumentException ex) {
            MessageUtil.showWarning(this, ex.getMessage());
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Lỗi CSDL: " + ex.getMessage());
        }
    }

    private void xoaKhachHang() {
        try {
            if (txtMaKh.getText().isEmpty()) {
                MessageUtil.showWarning(this, "Vui lòng chọn khách hàng cần xóa trong bảng!");
                return;
            }
            if (!MessageUtil.confirm(this, "Bạn có chắc muốn xóa khách hàng này?")) return;

            int maKh = Integer.parseInt(txtMaKh.getText());
            khachHangBUS.delete(maKh);
            MessageUtil.showInfo(this, "Xóa khách hàng thành công!");
            clearForm();
            loadData();
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Không thể xóa (có thể khách hàng đã có hóa đơn): " + ex.getMessage());
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        txtMaKh.setText(tableModel.getValueAt(row, 0).toString());
        txtTenKh.setText(tableModel.getValueAt(row, 1).toString());
        txtSdt.setText(tableModel.getValueAt(row, 2).toString());
        Object diaChi = tableModel.getValueAt(row, 3);
        txtDiaChi.setText(diaChi == null ? "" : diaChi.toString());
    }

    private void clearForm() {
        txtMaKh.setText("");
        txtTenKh.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
        table.clearSelection();
    }
}
