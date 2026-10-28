package mazesolver.solver;

import mazesolver.model.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Base class for solvers. Contains shared helper methods.
 * 
 * OOP Concept: Inheritance — BFSSolver and DFSSolver extend this
 * class to reuse the buildPath() method, avoiding code duplication.
 */
public abstract class AbstractSolver implements MazeSolver {

    /**
     * Reconstructs the path from start to end by following
     * the parent pointers stored during exploration.
     */
    protected List<Cell> buildPath(Map<Cell, Cell> parentMap, Cell end) {
        List<Cell> path = new ArrayList<>();
        Cell current = end;

        while (current != null) {
            path.add(current);
            current = parentMap.get(current);  // move to parent
        }

        Collections.reverse(path);  // start → end order
        return path;
    }
}
