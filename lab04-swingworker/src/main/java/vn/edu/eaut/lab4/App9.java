package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App9 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ProductLoaderFrame()
                    .setVisible(true);
        });

    }
}