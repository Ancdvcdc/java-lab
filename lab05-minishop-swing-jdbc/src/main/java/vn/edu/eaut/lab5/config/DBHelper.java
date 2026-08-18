package vn.edu.eaut.lab5.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lớp hỗ trợ kết nối CSDL MySQL bằng JDBC.
 * Sinh viên chỉnh lại USER / PASSWORD cho đúng với MySQL đang cài trên máy.
 */
public class DBHelper {

    private static final String URL =
            "jdbc:mysql://localhost:3306/minishop_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // TODO: đổi lại mật khẩu MySQL của bạn

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Ket noi CSDL thanh cong!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Ket noi CSDL that bai: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
