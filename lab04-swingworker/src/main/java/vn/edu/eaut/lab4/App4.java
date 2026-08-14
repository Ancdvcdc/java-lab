package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App4 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new FibonacciFrame().setVisible(true);
        });

    }
}