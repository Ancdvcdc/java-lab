package vn.edu.eaut.lab6.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

/**
 * Bài 12: Khởi tạo dữ liệu mẫu bằng Listener.
 * Khi ứng dụng khởi động, tạo ít nhất 5 sinh viên mẫu và lưu vào StudentStore
 * (đồng thời lưu số lượng ban đầu vào ServletContext).
 * Khi ứng dụng dừng, ghi log số lượng sinh viên hiện có.
 */
@WebListener
public class DataInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        StudentStore.add(new Student("SV001", "Nguyen Van An", "DCCNTT12", "an@example.com"));
        StudentStore.add(new Student("SV002", "Tran Thi Binh", "DCCNTT12", "binh@example.com"));
        StudentStore.add(new Student("SV003", "Le Van Cuong", "DCCNTT13", "cuong@example.com"));
        StudentStore.add(new Student("SV004", "Pham Thi Dung", "DCCNTT13", "dung@example.com"));
        StudentStore.add(new Student("SV005", "Hoang Van Em", "DCCNTT14", "em@example.com"));

        int total = StudentStore.findAll().size();
        sce.getServletContext().setAttribute("initialStudentCount", total);

        System.out.println("DataInitListener: da khoi tao " + total + " sinh vien mau");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        int total = StudentStore.findAll().size();
        System.out.println("DataInitListener: ung dung dung, con lai " + total + " sinh vien trong bo nho");
    }
}
