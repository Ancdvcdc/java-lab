package vn.edu.eaut.lab6.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Bài 5: theo dõi vòng đời ứng dụng - ghi log khi ứng dụng khởi động / dừng.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Ung dung Lab 6 da khoi dong");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Ung dung Lab 6 da dung");
    }
}
