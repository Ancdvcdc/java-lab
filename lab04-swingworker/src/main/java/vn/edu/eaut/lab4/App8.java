package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App8 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new StudentStatisticsFrame()
                    .setVisible(true);
        });

    }
}