package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App1 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new CountdownFrame().setVisible(true);
        });

    }
}