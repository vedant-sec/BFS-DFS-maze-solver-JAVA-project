package mazesolver;

import mazesolver.gui.MazeApp;
import javax.swing.*;

/**
 * Entry point of the Maze Solver application.
 * Launches the GUI on the Event Dispatch Thread (EDT).
 */
public class Main {

    public static void main(String[] args) {
        // Always start Swing apps on the EDT
        SwingUtilities.invokeLater(() -> {
            try {
                // Use the system look-and-feel for a native appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MazeApp app = new MazeApp();
            app.setVisible(true);
        });
    }
}
