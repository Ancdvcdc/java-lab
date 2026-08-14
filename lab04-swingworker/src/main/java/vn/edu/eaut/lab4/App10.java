package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App10 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ProductManagerFrame()
                    .setVisible(true);
        });

    }
}