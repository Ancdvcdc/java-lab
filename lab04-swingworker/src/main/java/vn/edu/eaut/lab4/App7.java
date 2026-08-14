package vn.edu.eaut.lab4;

import javax.swing.SwingUtilities;

public class App7 {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new SearchKeywordFrame()
                    .setVisible(true);
        });

    }
}