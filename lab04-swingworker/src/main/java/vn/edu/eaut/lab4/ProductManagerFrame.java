package vn.edu.eaut.lab4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ProductManagerFrame extends JFrame {

    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtDonGia;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSave;
    private JButton btnLoad;

    private JTable table;
    private DefaultTableModel model;

    public ProductManagerFrame() {

        setTitle("Mini Project - Quản lý sản phẩm");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtMaSP = new JTextField();
        txtTenSP = new JTextField();
        txtDonGia = new JTextField();

        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnSave = new JButton("Lưu CSV");
        btnLoad = new JButton("Đọc CSV");

        model = new DefaultTableModel(
                new Object[]{
                        "Mã SP",
                        "Tên SP",
                        "Đơn giá"
                }, 0);

        table = new JTable(model);

        JPanel inputPanel =
                new JPanel(new GridLayout(3,2,5,5));

        inputPanel.add(new JLabel("Mã SP"));
        inputPanel.add(txtMaSP);

        inputPanel.add(new JLabel("Tên SP"));
        inputPanel.add(txtTenSP);

        inputPanel.add(new JLabel("Đơn giá"));
        inputPanel.add(txtDonGia);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnLoad);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addProduct());

        btnUpdate.addActionListener(e -> updateProduct());

        btnDelete.addActionListener(e -> deleteProduct());

        btnSave.addActionListener(e -> saveCSV());

        btnLoad.addActionListener(e -> loadCSV());

        table.getSelectionModel()
                .addListSelectionListener(e -> showSelected());
    }

    private void addProduct() {

        model.addRow(new Object[]{
                txtMaSP.getText(),
                txtTenSP.getText(),
                txtDonGia.getText()
        });

        clearInput();
    }

    private void updateProduct() {

        int row = table.getSelectedRow();

        if(row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Chọn sản phẩm cần sửa");
            return;
        }

        model.setValueAt(
                txtMaSP.getText(), row, 0);

        model.setValueAt(
                txtTenSP.getText(), row, 1);

        model.setValueAt(
                txtDonGia.getText(), row, 2);
    }

    private void deleteProduct() {

        int row = table.getSelectedRow();

        if(row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Chọn sản phẩm cần xóa");
            return;
        }

        model.removeRow(row);

        clearInput();
    }

    private void showSelected() {

        int row = table.getSelectedRow();

        if(row >= 0) {

            txtMaSP.setText(
                    model.getValueAt(row,0).toString());

            txtTenSP.setText(
                    model.getValueAt(row,1).toString());

            txtDonGia.setText(
                    model.getValueAt(row,2).toString());
        }
    }

    private void clearInput() {

        txtMaSP.setText("");
        txtTenSP.setText("");
        txtDonGia.setText("");
    }

    private void saveCSV() {

        JFileChooser chooser =
                new JFileChooser();

        if(chooser.showSaveDialog(this)
                != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file =
                chooser.getSelectedFile();

        SwingWorker<Void, Void> worker =
                new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground()
                    throws Exception {

                PrintWriter writer =
                        new PrintWriter(
                                new OutputStreamWriter(
                                        new FileOutputStream(file),
                                        StandardCharsets.UTF_8));

                writer.println("MaSP,TenSP,DonGia");

                for(int i = 0;
                    i < model.getRowCount();
                    i++) {

                    writer.println(
                            model.getValueAt(i,0)
                                    + ","
                                    + model.getValueAt(i,1)
                                    + ","
                                    + model.getValueAt(i,2));
                }

                writer.close();

                return null;
            }

            @Override
            protected void done() {

                JOptionPane.showMessageDialog(
                        ProductManagerFrame.this,
                        "Lưu file thành công");
            }
        };

        worker.execute();
    }

    private void loadCSV() {

        JFileChooser chooser =
                new JFileChooser();

        if(chooser.showOpenDialog(this)
                != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file =
                chooser.getSelectedFile();

        model.setRowCount(0);

        SwingWorker<Void, String[]> worker =
                new SwingWorker<Void, String[]>() {

            @Override
            protected Void doInBackground()
                    throws Exception {

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(file),
                                        StandardCharsets.UTF_8));

                String line;

                reader.readLine();

                while((line =
                        reader.readLine()) != null) {

                    publish(line.split(","));
                }

                reader.close();

                return null;
            }

            @Override
            protected void process(
                    List<String[]> chunks) {

                for(String[] row : chunks) {

                    model.addRow(row);
                }
            }

            @Override
            protected void done() {

                JOptionPane.showMessageDialog(
                        ProductManagerFrame.this,
                        "Đọc file thành công");
            }
        };

        worker.execute();
    }
}