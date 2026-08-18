package vn.edu.eaut.lab5.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Cửa sổ chính của ứng dụng MiniShop.
 * Gồm 4 tab: San Pham, Khach Hang, Hoa Don, Thong Ke.
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("MiniShop - Quan ly ban hang (Lab 5 - Cong nghe Java)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 550));

        JTabbedPane tabbedPane = new JTabbedPane();

        SanPhamPanel sanPhamPanel = new SanPhamPanel();
        HoaDonPanel hoaDonPanel = new HoaDonPanel();

        tabbedPane.addTab("Sản Phẩm", sanPhamPanel);
        tabbedPane.addTab("Khách Hàng", new KhachHangPanel());
        tabbedPane.addTab("Hóa Đơn", hoaDonPanel);
        tabbedPane.addTab("Thống Kê", new ThongKePanel());

        // Khi lập hóa đơn xong thì tự refresh lại danh sách sản phẩm (vì đã trừ kho)
        hoaDonPanel.setOnHoaDonSaved(sanPhamPanel::loadData);

        add(tabbedPane, BorderLayout.CENTER);
    }
}
