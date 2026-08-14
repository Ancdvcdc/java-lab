package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App2 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ProgressDemoFrame().setVisible(true);
        });

    }
}