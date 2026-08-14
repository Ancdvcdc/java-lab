package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class StudentStatisticsFrame extends JFrame {

    private JButton btnChooseFile;
    private JButton btnLoad;

    private JLabel lblFile;
    private JLabel lblAverage;
    private JLabel lblTopStudent;

    private JTable table;
    private DefaultTableModel model;

    private File selectedFile;

    public StudentStatisticsFrame() {

        setTitle("Bài 8 - Thống kê điểm sinh viên");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnChooseFile = new JButton("Chọn file CSV");
        btnLoad = new JButton("Đọc dữ liệu");

        lblFile = new JLabel("Chưa chọn file");
        lblAverage = new JLabel("Điểm trung bình: ");
        lblTopStudent = new JLabel("Sinh viên cao điểm nhất: ");

        model = new DefaultTableModel();

        model.addColumn("Mã SV");
        model.addColumn("Họ tên");
        model.addColumn("Điểm");

        table = new JTable(model);

        JScrollPane scrollPane =
                new JScrollPane(table);

        JPanel topPanel =
                new JPanel(new GridLayout(5, 1, 5, 5));

        topPanel.add(btnChooseFile);
        topPanel.add(btnLoad);
        topPanel.add(lblFile);
        topPanel.add(lblAverage);
        topPanel.add(lblTopStudent);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnChooseFile.addActionListener(
                e -> chooseFile());

        btnLoad.addActionListener(
                e -> loadData());
    }

    private void chooseFile() {

        JFileChooser chooser =
                new JFileChooser();

        int result =
                chooser.showOpenDialog(this);

        if (result ==
                JFileChooser.APPROVE_OPTION) {

            selectedFile =
                    chooser.getSelectedFile();

            lblFile.setText(
                    selectedFile.getAbsolutePath());
        }
    }

    private void loadData() {

        if (selectedFile == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn file CSV");

            return;
        }

        model.setRowCount(0);

        btnLoad.setEnabled(false);

        SwingWorker<Void, String[]> worker =
                new SwingWorker<Void, String[]>() {

            double totalScore = 0;
            int count = 0;

            String topStudent = "";
            double maxScore = -1;

            @Override
            protected Void doInBackground()
                    throws Exception {

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(
                                                selectedFile),
                                        StandardCharsets.UTF_8));

                String line;

                // Bỏ dòng tiêu đề
                reader.readLine();

                while ((line =
                        reader.readLine()) != null) {

                    String[] data =
                            line.split(",");

                    if (data.length >= 3) {

                        publish(data);

                        double score =
                                Double.parseDouble(
                                        data[2]);

                        totalScore += score;

                        count++;

                        if (score > maxScore) {

                            maxScore = score;

                            topStudent =
                                    data[1];
                        }
                    }
                }

                reader.close();

                return null;
            }

            @Override
            protected void process(
                    java.util.List<String[]> chunks) {

                for (String[] row : chunks) {

                    model.addRow(row);
                }
            }

            @Override
            protected void done() {

                if (count > 0) {

                    double average =
                            totalScore / count;

                    lblAverage.setText(
                            "Điểm trung bình: "
                                    + String.format(
                                    "%.2f",
                                    average));

                    lblTopStudent.setText(
                            "Sinh viên cao điểm nhất: "
                                    + topStudent
                                    + " ("
                                    + maxScore
                                    + ")");
                }

                btnLoad.setEnabled(true);
            }
        };

        worker.execute();
    }
}