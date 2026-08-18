package vn.edu.eaut.lab5;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.ui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
        // Giao diện gần giống hệ điều hành cho đẹp hơn giao diện Java mặc định
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        DBHelper.testConnection();

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
