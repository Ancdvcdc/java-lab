package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App6 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new CancelProgressFrame()
                    .setVisible(true);
        });

    }
}