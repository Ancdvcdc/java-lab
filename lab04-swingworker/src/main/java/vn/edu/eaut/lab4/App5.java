package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App5 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new FileLineCounterFrame().setVisible(true);
        });

    }
}