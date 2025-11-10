package main;

import main.ui.FilmGUI;

import javax.swing.*;

/**
 * @author lucio
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            FilmGUI gui = new FilmGUI();
        });
    }
}
