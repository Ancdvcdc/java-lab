package vn.edu.eaut.lab5.util;

import javax.swing.JOptionPane;
import java.awt.Component;

public class MessageUtil {

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Thong bao", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Loi", JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarning(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Canh bao", JOptionPane.WARNING_MESSAGE);
    }

    public static boolean confirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "Xac nhan", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
