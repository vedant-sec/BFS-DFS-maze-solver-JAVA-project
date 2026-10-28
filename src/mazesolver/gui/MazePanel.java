package mazesolver.gui;

import mazesolver.model.Cell;
import mazesolver.model.Maze;

import javax.swing.*;
import java.awt.*;

/**
 * Custom JPanel that renders the maze grid.
 * Each cell is drawn as a colored rectangle based on its CellType.
 * 
 * OOP Concept: Inheritance — extends JPanel and overrides
 * paintComponent() to provide custom drawing.
 */
public class MazePanel extends JPanel {

    private Maze maze;

    public MazePanel() {
        setBackground(Color.WHITE);
    }

    /**
     * Assigns a maze to this panel and triggers a repaint.
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
        revalidate();
        repaint();
    }

    public Maze getMaze() {
        return maze;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (maze == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate cell size so the maze fits inside the panel
        int cellW = getWidth()  / maze.getCols();
        int cellH = getHeight() / maze.getRows();
        int cellSize = Math.max(2, Math.min(cellW, cellH));

        // Centre the maze in the panel
        int offsetX = (getWidth()  - cellSize * maze.getCols()) / 2;
        int offsetY = (getHeight() - cellSize * maze.getRows()) / 2;

        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                Cell cell = maze.getCell(r, c);
                int x = offsetX + c * cellSize;
                int y = offsetY + r * cellSize;

                // Fill the cell with its type colour
                g2.setColor(cell.getType().getColor());
                g2.fillRect(x, y, cellSize, cellSize);
            }
        }
    }
}
