package vn.edu.eaut.lab3;

import javax.swing.*;
import java.awt.*;

public class Bai06LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cboRole;
    private JCheckBox chkShowPassword;
    private JButton btnLogin;

    public Bai06LoginForm() {
        setTitle("Bài 6 - Form Đăng Nhập");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Tài khoản:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Mật khẩu:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        add(new JLabel("Vai trò:"));
        cboRole = new JComboBox<>(new String[]{"Admin", "User"});
        add(cboRole);

        add(new JLabel(""));
        chkShowPassword = new JCheckBox("Hiển thị mật khẩu");
        add(chkShowPassword);

        add(new JLabel(""));
        btnLogin = new JButton("Đăng nhập");
        add(btnLogin);

        chkShowPassword.addActionListener(e -> {
            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('*');
            }
        });

        btnLogin.addActionListener(e -> dangNhap());
    }

    private void dangNhap() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = (String) cboRole.getSelectedItem();

        boolean admin =
                username.equals("admin")
                        && password.equals("123456")
                        && role.equals("Admin");

        boolean user =
                username.equals("user")
                        && password.equals("123456")
                        && role.equals("User");

        if (admin || user) {
            JOptionPane.showMessageDialog(
                    this,
                    "Đăng nhập thành công!\nChào mừng " + username
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Sai tài khoản, mật khẩu hoặc vai trò!",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new Bai06LoginForm().setVisible(true)
        );
    }
}
