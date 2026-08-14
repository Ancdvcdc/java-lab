package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai07MayTinhMini extends JFrame {

    private JTextField txtSo1;
    private JTextField txtSo2;
    private JTextField txtKetQua;
    private JTextArea txtLichSu;

    public Bai07MayTinhMini() {
        setTitle("Bài 7 - Máy Tính Mini");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 5, 5));

        pnlInput.add(new JLabel("Số thứ nhất:"));
        txtSo1 = new JTextField();
        pnlInput.add(txtSo1);

        pnlInput.add(new JLabel("Số thứ hai:"));
        txtSo2 = new JTextField();
        pnlInput.add(txtSo2);

        pnlInput.add(new JLabel("Kết quả:"));
        txtKetQua = new JTextField();
        txtKetQua.setEditable(false);
        pnlInput.add(txtKetQua);

        add(pnlInput, BorderLayout.NORTH);

        JPanel pnlButton = new JPanel(new FlowLayout());

        JButton btnCong = new JButton("Cộng");
        JButton btnTru = new JButton("Trừ");
        JButton btnNhan = new JButton("Nhân");
        JButton btnChia = new JButton("Chia");
        JButton btnClear = new JButton("Clear");

        pnlButton.add(btnCong);
        pnlButton.add(btnTru);
        pnlButton.add(btnNhan);
        pnlButton.add(btnChia);
        pnlButton.add(btnClear);

        add(pnlButton, BorderLayout.CENTER);

        txtLichSu = new JTextArea();
        txtLichSu.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtLichSu);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lịch sử tính toán"));

        add(scrollPane, BorderLayout.SOUTH);

        btnCong.addActionListener(e -> tinhToan("+"));
        btnTru.addActionListener(e -> tinhToan("-"));
        btnNhan.addActionListener(e -> tinhToan("*"));
        btnChia.addActionListener(e -> tinhToan("/"));
        btnClear.addActionListener(e -> lamMoi());
    }

    private void tinhToan(String phepToan) {
        try {
            double a = Double.parseDouble(txtSo1.getText().trim());
            double b = Double.parseDouble(txtSo2.getText().trim());

            double ketQua = 0;

            switch (phepToan) {
                case "+":
                    ketQua = a + b;
                    break;

                case "-":
                    ketQua = a - b;
                    break;

                case "*":
                    ketQua = a * b;
                    break;

                case "/":
                    if (b == 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Không thể chia cho 0!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    ketQua = a / b;
                    break;
            }

            txtKetQua.setText(String.valueOf(ketQua));

            txtLichSu.append(
                    a + " " + phepToan + " " + b + " = " + ketQua + "\n"
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đúng định dạng số!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void lamMoi() {
        txtSo1.setText("");
        txtSo2.setText("");
        txtKetQua.setText("");
        txtSo1.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new Bai07MayTinhMini().setVisible(true)
        );
    }
}

