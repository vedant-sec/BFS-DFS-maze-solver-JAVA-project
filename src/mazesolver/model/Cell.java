package mazesolver.model;

/**
 * Represents a single cell in the maze grid.
 * 
 * OOP Concept: Encapsulation — fields are private,
 * accessed only through getters and setters.
 */
public class Cell {

    private final int row;
    private final int col;
    private CellType type;

    public Cell(int row, int col, CellType type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    // --- Getters & Setter ---

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    /**
     * A cell is walkable if it is NOT a wall.
     */
    public boolean isWalkable() {
        return type != CellType.WALL;
    }

    @Override
    public String toString() {
        return "Cell(" + row + ", " + col + ", " + type + ")";
    }
}
