package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductLoaderFrame extends JFrame {

    private JButton btnLoad;
    private JTable table;
    private DefaultTableModel model;
    private JProgressBar progressBar;
    private JLabel lblStatus;

    public ProductLoaderFrame() {

        setTitle("Bài 9 - Tải danh sách sản phẩm");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnLoad = new JButton("Tải sản phẩm");

        model = new DefaultTableModel(
                new Object[]{
                        "Mã SP",
                        "Tên SP",
                        "Đơn giá"
                }, 0);

        table = new JTable(model);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        lblStatus = new JLabel("Chưa tải dữ liệu");

        JScrollPane scrollPane =
                new JScrollPane(table);

        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.add(btnLoad, BorderLayout.NORTH);
        topPanel.add(progressBar, BorderLayout.CENTER);
        topPanel.add(lblStatus, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnLoad.addActionListener(
                e -> loadProducts());
    }

    private void loadProducts() {

        model.setRowCount(0);

        btnLoad.setEnabled(false);

        lblStatus.setText(
                "Đang tải sản phẩm...");

        SwingWorker<Void, Object[]> worker =
                new SwingWorker<Void, Object[]>() {

            private final Object[][] products = {
                    {"SP01", "Bàn phím", 250000},
                    {"SP02", "Chuột", 150000},
                    {"SP03", "Màn hình", 2500000}
            };

            @Override
            protected Void doInBackground()
                    throws Exception {

                int total = products.length;

                for (int i = 0;
                     i < total;
                     i++) {

                    publish(products[i]);

                    int progress =
                            (i + 1) * 100 / total;

                    setProgress(progress);

                    Thread.sleep(1000);
                }

                return null;
            }

            @Override
            protected void process(
                    java.util.List<Object[]> chunks) {

                for (Object[] row : chunks) {

                    model.addRow(row);
                }
            }

            @Override
            protected void done() {

                lblStatus.setText(
                        "Tải dữ liệu hoàn tất");

                btnLoad.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(
                evt -> {

                    if ("progress".equals(
                            evt.getPropertyName())) {

                        progressBar.setValue(
                                (int) evt.getNewValue());
                    }
                });

        worker.execute();
    }
}