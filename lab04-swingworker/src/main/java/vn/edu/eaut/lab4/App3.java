package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App3 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new PrimeSumFrame().setVisible(true);
        });

    }
}