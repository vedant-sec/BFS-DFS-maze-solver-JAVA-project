package mazesolver.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the maze as a 2D grid of Cells.
 * Generates a perfect maze using the Recursive Backtracking algorithm.
 * 
 * Grid layout:
 *   - Actual grid size = (2 * logicalSize + 1) in each dimension.
 *   - Even indices are walls / wall intersections.
 *   - Odd  indices are passages (potential walkable cells).
 * 
 * OOP Concept: Encapsulation — the grid is private; external code
 * interacts through well-defined methods.
 */
public class Maze {

    private final int rows;       // total grid rows
    private final int cols;       // total grid cols
    private Cell[][] grid;
    private Cell startCell;
    private Cell endCell;

    /**
     * Creates and generates a new maze.
     * @param logicalRows number of logical rows  (passages)
     * @param logicalCols number of logical columns (passages)
     */
    public Maze(int logicalRows, int logicalCols) {
        this.rows = 2 * logicalRows + 1;
        this.cols = 2 * logicalCols + 1;
        generate();
    }

    // ==================== Maze Generation ====================

    /**
     * Initialises the grid with all walls, then carves passages.
     */
    private void generate() {
        // Step 1 — fill everything with walls
        grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(r, c, CellType.WALL);
            }
        }

        // Step 2 — carve passages starting from (1, 1)
        boolean[][] visited = new boolean[rows][cols];
        carve(1, 1, visited);

        // Step 3 — mark start and end cells
        startCell = grid[1][1];
        startCell.setType(CellType.START);

        endCell = grid[rows - 2][cols - 2];
        endCell.setType(CellType.END);
    }

    /**
     * Recursive Backtracking: carves passages by visiting
     * neighbours 2 cells away and removing the wall in between.
     */
    private void carve(int r, int c, boolean[][] visited) {
        grid[r][c].setType(CellType.PATH);
        visited[r][c] = true;

        // Four possible directions (each jumps 2 cells)
        int[][] directions = { {-2, 0}, {2, 0}, {0, -2}, {0, 2} };
        List<int[]> dirs = new ArrayList<>(Arrays.asList(directions));
        Collections.shuffle(dirs);   // randomise for unique mazes

        for (int[] dir : dirs) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            if (isInBounds(nr, nc) && !visited[nr][nc]) {
                // Remove the wall between current cell and neighbour
                int wallR = r + dir[0] / 2;
                int wallC = c + dir[1] / 2;
                grid[wallR][wallC].setType(CellType.PATH);

                carve(nr, nc, visited);
            }
        }
    }

    private boolean isInBounds(int r, int c) {
        return r > 0 && r < rows - 1 && c > 0 && c < cols - 1;
    }

    // ==================== Public API ====================

    /**
     * Returns the walkable (non-wall) neighbours of a cell.
     * Used by solvers to explore the maze.
     */
    public List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] dirs = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

        for (int[] dir : dirs) {
            int nr = cell.getRow() + dir[0];
            int nc = cell.getCol() + dir[1];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                    && grid[nr][nc].isWalkable()) {
                neighbors.add(grid[nr][nc]);
            }
        }
        return neighbors;
    }

    /**
     * Clears VISITED and SOLUTION markers so the maze
     * can be solved again without regenerating.
     */
    public void resetSolution() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                CellType t = grid[r][c].getType();
                if (t == CellType.VISITED || t == CellType.SOLUTION) {
                    grid[r][c].setType(CellType.PATH);
                }
            }
        }
        startCell.setType(CellType.START);
        endCell.setType(CellType.END);
    }

    // --- Getters ---

    public Cell getCell(int row, int col) { return grid[row][col]; }
    public int getRows()                  { return rows; }
    public int getCols()                  { return cols; }
    public Cell getStartCell()            { return startCell; }
    public Cell getEndCell()              { return endCell; }
}
