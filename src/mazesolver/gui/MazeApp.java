package mazesolver.gui;

import mazesolver.model.CellType;
import mazesolver.model.Cell;
import mazesolver.model.Maze;
import mazesolver.solver.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.List;

/**
 * Main application window (JFrame).
 * Contains the MazePanel, control buttons, a speed slider,
 * and a status bar.
 * 
 * OOP Concept: Composition — MazeApp *has-a* MazePanel, Maze,
 * and MazeSolver objects that it coordinates.
 */
public class MazeApp extends JFrame {

    private MazePanel mazePanel;
    private JButton generateBtn, solveBfsBtn, solveDfsBtn, resetBtn;
    private JSlider speedSlider;
    private JLabel statusLabel;
    private JSpinner sizeSpinner;
    private Timer animationTimer;      // Swing Timer for animation
    private boolean isSolving = false;

    public MazeApp() {
        setTitle("Maze Solver — OOP Project");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initControlPanel();     // top
        initMazePanel();        // centre
        initStatusBar();        // bottom

        generateMaze();         // show a maze on startup

        pack();
        setMinimumSize(new Dimension(720, 620));
        setLocationRelativeTo(null);   // centre on screen
    }

    // ==================== UI Initialisation ====================

    private void initMazePanel() {
        mazePanel = new MazePanel();
        mazePanel.setPreferredSize(new Dimension(600, 500));
        add(mazePanel, BorderLayout.CENTER);
    }

    private void initControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Controls"));

        // Maze size spinner (5–30)
        panel.add(new JLabel("Size:"));
        sizeSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 30, 1));
        panel.add(sizeSpinner);

        // Action buttons
        generateBtn  = new JButton("Generate Maze");
        solveBfsBtn  = new JButton("Solve (BFS)");
        solveDfsBtn  = new JButton("Solve (DFS)");
        resetBtn     = new JButton("Reset");

        generateBtn.addActionListener(e -> generateMaze());
        solveBfsBtn.addActionListener(e -> solveMaze(new BFSSolver()));   // polymorphism!
        solveDfsBtn.addActionListener(e -> solveMaze(new DFSSolver()));   // polymorphism!
        resetBtn.addActionListener(e -> resetMaze());

        panel.add(generateBtn);
        panel.add(solveBfsBtn);
        panel.add(solveDfsBtn);
        panel.add(resetBtn);

        // Animation speed slider
        panel.add(new JLabel("Speed:"));
        speedSlider = new JSlider(1, 100, 50);
        speedSlider.setPreferredSize(new Dimension(120, 26));
        // Dynamically update animation speed
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (animationTimer != null && animationTimer.isRunning()) {
                    animationTimer.setDelay(calcDelay());
                }
            }
        });
        panel.add(speedSlider);

        add(panel, BorderLayout.NORTH);
    }

    private void initStatusBar() {
        statusLabel = new JLabel("  Ready — generate a maze and solve it!");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        add(statusLabel, BorderLayout.SOUTH);
    }

    // ==================== Actions ====================

    /**
     * Generates a new random maze based on the spinner size.
     */
    private void generateMaze() {
        stopAnimation();
        int size = (int) sizeSpinner.getValue();
        Maze maze = new Maze(size, size);
        mazePanel.setMaze(maze);
        setStatus("New " + size + "×" + size + " maze generated.");
        setButtonsEnabled(true);
    }

    /**
     * Solves the current maze with the given solver (BFS or DFS).
     * Uses polymorphism: the method accepts any MazeSolver.
     */
    private void solveMaze(MazeSolver solver) {
        if (isSolving) return;
        Maze maze = mazePanel.getMaze();
        if (maze == null) return;

        // Clear any previous solution marks
        maze.resetSolution();
        mazePanel.repaint();

        // Solve (instant computation, then animated display)
        SolveResult result = solver.solve(maze);
        animateSolution(result, solver.getName());
    }

    /**
     * Resets the maze to its unsolved state.
     */
    private void resetMaze() {
        stopAnimation();
        Maze maze = mazePanel.getMaze();
        if (maze != null) {
            maze.resetSolution();
            mazePanel.repaint();
            setStatus("Maze reset — ready to solve again.");
        }
        setButtonsEnabled(true);
    }

    // ==================== Animation ====================

    /**
     * Animates the solving process:
     *   1. Shows explored cells one-by-one (light blue).
     *   2. Then highlights the solution path (yellow).
     */
    private void animateSolution(SolveResult result, String solverName) {
        isSolving = true;
        setButtonsEnabled(false);

        List<Cell> visited  = result.getVisitedOrder();
        List<Cell> solution = result.getSolutionPath();

        // Mutable state for the timer callback
        final int[] idx = {0};
        final boolean[] drawingPath = {false};

        animationTimer = new Timer(calcDelay(), e -> {
            if (!drawingPath[0]) {
                // Phase 1: mark explored cells
                if (idx[0] < visited.size()) {
                    Cell cell = visited.get(idx[0]);
                    if (cell.getType() != CellType.START && cell.getType() != CellType.END) {
                        cell.setType(CellType.VISITED);
                    }
                    idx[0]++;
                    setStatus(solverName + " — Exploring… (" + idx[0] + "/" + visited.size() + ")");
                } else {
                    // Switch to phase 2
                    drawingPath[0] = true;
                    idx[0] = 0;
                }
            } else {
                // Phase 2: highlight solution path
                if (idx[0] < solution.size()) {
                    Cell cell = solution.get(idx[0]);
                    if (cell.getType() != CellType.START && cell.getType() != CellType.END) {
                        cell.setType(CellType.SOLUTION);
                    }
                    idx[0]++;
                    setStatus(solverName + " — Drawing path… (" + idx[0] + "/" + solution.size() + ")");
                } else {
                    // Done
                    ((Timer) e.getSource()).stop();
                    isSolving = false;
                    setButtonsEnabled(true);
                    if (result.isSolved()) {
                        setStatus(solverName + " ✓  Path length: " + solution.size()
                                  + "  |  Cells explored: " + visited.size());
                    } else {
                        setStatus(solverName + " ✗  No solution found.");
                    }
                }
            }
            mazePanel.repaint();
        });
        animationTimer.start();
    }

    /**
     * Maps the speed slider (1–100) to a timer delay (200 ms → 5 ms).
     */
    private int calcDelay() {
        int speed = speedSlider.getValue();
        return Math.max(5, 205 - speed * 2);
    }

    private void stopAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        isSolving = false;
    }

    // ==================== Helpers ====================

    private void setButtonsEnabled(boolean enabled) {
        generateBtn.setEnabled(enabled);
        solveBfsBtn.setEnabled(enabled);
        solveDfsBtn.setEnabled(enabled);
        resetBtn.setEnabled(true);  // reset is always available
    }

    private void setStatus(String text) {
        statusLabel.setText("  " + text);
    }
}
