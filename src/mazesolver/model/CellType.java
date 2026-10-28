package mazesolver.model;

import java.awt.Color;

/**
 * Enum representing the different types a maze cell can be.
 * Each type has an associated color for GUI rendering.
 * 
 * OOP Concept: Encapsulation — each enum constant bundles
 * its display color internally.
 */
public enum CellType {

    WALL(new Color(44, 62, 80)),         // Dark blue-gray
    PATH(Color.WHITE),                    // White
    START(new Color(46, 204, 113)),       // Green
    END(new Color(231, 76, 60)),          // Red
    VISITED(new Color(174, 214, 241)),    // Light blue
    SOLUTION(new Color(241, 196, 15));    // Yellow / Gold

    private final Color color;

    CellType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
