package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class FibonacciFrame extends JFrame {

    private JTextField txtN;
    private JButton btnFind;
    private JLabel lblResult;
    private JProgressBar progressBar;

    public FibonacciFrame() {

        setTitle("Bài 4 - Fibonacci");
        setSize(600, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtN = new JTextField();

        btnFind = new JButton("Tìm");

        lblResult = new JLabel("Kết quả:");

        progressBar = new JProgressBar();

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        panel.add(txtN);
        panel.add(btnFind);
        panel.add(progressBar);
        panel.add(lblResult);

        add(panel);

        btnFind.addActionListener(e -> findFibonacci());
    }

    private BigInteger fibonacci(
            int n,
            Map<Integer, BigInteger> memo) {

        if (n <= 1) {
            return BigInteger.valueOf(n);
        }

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        BigInteger value =
                fibonacci(n - 1, memo)
                        .add(fibonacci(n - 2, memo));

        memo.put(n, value);

        return value;
    }

    private void findFibonacci() {

        int n;

        try {

            n = Integer.parseInt(
                    txtN.getText().trim());

            if (n < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "N phải >= 0");

                return;
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập số nguyên hợp lệ");

            return;
        }

        btnFind.setEnabled(false);

        progressBar.setIndeterminate(true);

        lblResult.setText(
                "Đang tính Fibonacci...");

        SwingWorker<BigInteger, Void> worker =
                new SwingWorker<BigInteger, Void>() {

            @Override
            protected BigInteger doInBackground() {

                Map<Integer, BigInteger> memo =
                        new HashMap<>();

                return fibonacci(n, memo);
            }

            @Override
            protected void done() {

                try {

                    BigInteger result = get();

                    lblResult.setText(
                            "Fibonacci(" + n + ") = "
                                    + result);

                } catch (Exception ex) {

                    lblResult.setText(
                            "Có lỗi khi tính Fibonacci");
                }

                progressBar.setIndeterminate(false);

                progressBar.setValue(100);

                btnFind.setEnabled(true);
            }
        };

        worker.execute();
    }
}