package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;

public class CancelProgressFrame extends JFrame {

    private JButton btnStart;
    private JButton btnCancel;

    private JProgressBar progressBar;
    private JLabel lblStatus;

    private SwingWorker<Void, Integer> worker;

    public CancelProgressFrame() {

        setTitle("Bài 6 - Hủy tác vụ");
        setSize(500, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnStart = new JButton("Bắt đầu");
        btnCancel = new JButton("Hủy");

        btnCancel.setEnabled(false);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        lblStatus = new JLabel("Sẵn sàng");

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        panel.add(btnStart);
        panel.add(btnCancel);
        panel.add(progressBar);
        panel.add(lblStatus);

        add(panel);

        btnStart.addActionListener(e -> startTask());

        btnCancel.addActionListener(e -> cancelTask());
    }

    private void startTask() {

        btnStart.setEnabled(false);
        btnCancel.setEnabled(true);

        progressBar.setValue(0);

        lblStatus.setText("Đang chạy...");

        worker = new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground()
                    throws Exception {

                for (int i = 0; i <= 100; i++) {

                    if (isCancelled()) {
                        return null;
                    }

                    setProgress(i);

                    Thread.sleep(100);
                }

                return null;
            }

            @Override
            protected void done() {

                if (isCancelled()) {

                    lblStatus.setText(
                            "Đã hủy tác vụ");

                } else {

                    lblStatus.setText(
                            "Hoàn thành");
                }

                btnStart.setEnabled(true);
                btnCancel.setEnabled(false);
            }
        };

        worker.addPropertyChangeListener(evt -> {

            if ("progress".equals(
                    evt.getPropertyName())) {

                progressBar.setValue(
                        (int) evt.getNewValue());
            }
        });

        worker.execute();
    }

    private void cancelTask() {

        if (worker != null
                && !worker.isDone()) {

            worker.cancel(true);
        }
    }
}