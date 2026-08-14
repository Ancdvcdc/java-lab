package vn.edu.eaut.lab3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Bai08QuanLySinhVien extends JFrame {

    private JTextField txtMaSV;
    private JTextField txtHoTen;
    private JTextField txtDiemTB;

    private JTable table;
    private DefaultTableModel model;

    private ArrayList<Student> dsSinhVien = new ArrayList<>();

    public Bai08QuanLySinhVien() {

        setTitle("Bài 8 - Quản Lý Sinh Viên");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 5, 5));

        pnlInput.add(new JLabel("Mã SV:"));
        txtMaSV = new JTextField();
        pnlInput.add(txtMaSV);

        pnlInput.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField();
        pnlInput.add(txtHoTen);

        pnlInput.add(new JLabel("Điểm TB:"));
        txtDiemTB = new JTextField();
        pnlInput.add(txtDiemTB);

        add(pnlInput, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("Mã SV");
        model.addColumn("Họ Tên");
        model.addColumn("Điểm TB");
        model.addColumn("Xếp Loại");

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pnlButton = new JPanel();

        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnLamMoi);

        add(pnlButton, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> themSinhVien());
        btnSua.addActionListener(e -> suaSinhVien());
        btnXoa.addActionListener(e -> xoaSinhVien());
        btnLamMoi.addActionListener(e -> lamMoi());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();

            if (row >= 0) {
                txtMaSV.setText(model.getValueAt(row, 0).toString());
                txtHoTen.setText(model.getValueAt(row, 1).toString());
                txtDiemTB.setText(model.getValueAt(row, 2).toString());
            }
        });
    }

    private void themSinhVien() {
        try {
            String maSV = txtMaSV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            double diemTB = Double.parseDouble(txtDiemTB.getText().trim());

            Student sv = new Student(maSV, hoTen, diemTB);
            dsSinhVien.add(sv);

            model.addRow(new Object[]{
                    sv.getMaSV(),
                    sv.getHoTen(),
                    sv.getDiemTB(),
                    sv.getXepLoai()
            });

            lamMoi();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Điểm trung bình phải là số!"
            );
        }
    }

    private void suaSinhVien() {
        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn sinh viên cần sửa!");
            return;
        }

        try {
            String maSV = txtMaSV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            double diemTB = Double.parseDouble(txtDiemTB.getText().trim());

            Student sv = dsSinhVien.get(row);

            sv.setMaSV(maSV);
            sv.setHoTen(hoTen);
            sv.setDiemTB(diemTB);

            model.setValueAt(maSV, row, 0);
            model.setValueAt(hoTen, row, 1);
            model.setValueAt(diemTB, row, 2);
            model.setValueAt(sv.getXepLoai(), row, 3);

            lamMoi();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Điểm trung bình phải là số!"
            );
        }
    }

    private void xoaSinhVien() {
        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sinh viên cần xóa!"
            );
            return;
        }

        dsSinhVien.remove(row);
        model.removeRow(row);

        lamMoi();
    }

    private void lamMoi() {
        txtMaSV.setText("");
        txtHoTen.setText("");
        txtDiemTB.setText("");
        txtMaSV.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new Bai08QuanLySinhVien().setVisible(true)
        );
    }
}
