package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class SearchKeywordFrame extends JFrame {

    private JButton btnChooseFile;
    private JButton btnSearch;

    private JTextField txtKeyword;

    private JLabel lblFile;
    private JLabel lblCount;

    private JTextArea txtResult;

    private File selectedFile;

    public SearchKeywordFrame() {

        setTitle("Bài 7 - Tìm kiếm từ khóa");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnChooseFile = new JButton("Chọn file");
        btnSearch = new JButton("Tìm kiếm");

        txtKeyword = new JTextField();

        lblFile = new JLabel("Chưa chọn file");
        lblCount = new JLabel("Số dòng tìm thấy: 0");

        txtResult = new JTextArea();
        txtResult.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(txtResult);

        JPanel topPanel =
                new JPanel(new GridLayout(4,1,5,5));

        topPanel.add(btnChooseFile);
        topPanel.add(txtKeyword);
        topPanel.add(lblFile);
        topPanel.add(lblCount);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnSearch, BorderLayout.SOUTH);

        btnChooseFile.addActionListener(
                e -> chooseFile());

        btnSearch.addActionListener(
                e -> searchKeyword());
    }

    private void chooseFile() {

        JFileChooser chooser =
                new JFileChooser();

        chooser.setFileFilter(
                new FileNameExtensionFilter(
                        "Text Files (*.txt)",
                        "txt"));

        int result =
                chooser.showOpenDialog(this);

        if(result ==
                JFileChooser.APPROVE_OPTION) {

            selectedFile =
                    chooser.getSelectedFile();

            lblFile.setText(
                    selectedFile.getAbsolutePath());
        }
    }

    private void searchKeyword() {

        if(selectedFile == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn file");

            return;
        }

        String keyword =
                txtKeyword.getText().trim();

        if(keyword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập từ khóa");

            return;
        }

        txtResult.setText("");
        lblCount.setText(
                "Đang tìm kiếm...");

        btnSearch.setEnabled(false);

        SwingWorker<Integer, String> worker =
                new SwingWorker<Integer, String>() {

            @Override
            protected Integer doInBackground()
                    throws Exception {

                int count = 0;

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(
                                                selectedFile),
                                        StandardCharsets.UTF_8));

                String line;

                while((line =
                        reader.readLine()) != null) {

                    if(line.toLowerCase()
                            .contains(
                                    keyword.toLowerCase())) {

                        count++;

                        publish(line);
                    }
                }

                reader.close();

                return count;
            }

            @Override
            protected void process(
                    java.util.List<String> chunks) {

                for(String line : chunks) {

                    txtResult.append(
                            line + "\n");
                }
            }

            @Override
            protected void done() {

                try {

                    int count = get();

                    lblCount.setText(
                            "Số dòng tìm thấy: "
                                    + count);

                } catch(Exception ex) {

                    lblCount.setText(
                            "Có lỗi xảy ra");
                }

                btnSearch.setEnabled(true);
            }
        };

        worker.execute();
    }
}