package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileLineCounterFrame extends JFrame {

    private JButton btnChoose;
    private JButton btnCount;

    private JLabel lblFile;
    private JLabel lblResult;

    private JProgressBar progressBar;

    private File selectedFile;

    public FileLineCounterFrame() {

        setTitle("Bài 5 - Đếm số dòng trong file");
        setSize(600, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnChoose = new JButton("Chọn file");
        btnCount = new JButton("Đếm dòng");

        lblFile = new JLabel("Chưa chọn file");

        lblResult = new JLabel("Kết quả:");

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        panel.add(btnChoose);
        panel.add(btnCount);
        panel.add(lblFile);
        panel.add(progressBar);
        panel.add(lblResult);

        add(panel);

        btnChoose.addActionListener(e -> chooseFile());

        btnCount.addActionListener(e -> countLines());
    }

    private void chooseFile() {

        JFileChooser chooser = new JFileChooser();

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            selectedFile = chooser.getSelectedFile();

            lblFile.setText(
                    "File: "
                            + selectedFile.getAbsolutePath());
        }
    }

    private void countLines() {

        if (selectedFile == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn file trước");

            return;
        }

        btnCount.setEnabled(false);

        progressBar.setValue(0);

        lblResult.setText("Đang đọc file...");

        SwingWorker<Long, Void> worker =
                new SwingWorker<Long, Void>() {

            @Override
            protected Long doInBackground()
                    throws Exception {

                long totalBytes =
                        Files.size(selectedFile.toPath());

                long readBytes = 0;

                long lines = 0;

                try (BufferedReader reader =
                             Files.newBufferedReader(
                                     selectedFile.toPath(),
                                     StandardCharsets.UTF_8)) {

                    String line;

                    while ((line = reader.readLine())
                            != null) {

                        lines++;

                        readBytes +=
                                line.getBytes(
                                        StandardCharsets.UTF_8)
                                        .length + 1;

                        int progress =
                                totalBytes == 0
                                        ? 100
                                        : (int) Math.min(
                                        100,
                                        (readBytes * 100
                                                / totalBytes));

                        setProgress(progress);
                    }
                }

                return lines;
            }

            @Override
            protected void done() {

                try {

                    long lineCount = get();

                    lblResult.setText(
                            "Số dòng: "
                                    + lineCount);

                } catch (Exception ex) {

                    lblResult.setText(
                            "Lỗi khi đọc file");
                }

                progressBar.setValue(100);

                btnCount.setEnabled(true);
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
}