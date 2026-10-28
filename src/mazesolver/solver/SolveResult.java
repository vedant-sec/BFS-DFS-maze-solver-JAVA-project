package mazesolver.solver;

import mazesolver.model.Cell;
import java.util.List;

/**
 * Holds the result of solving a maze.
 * 
 * OOP Concept: Encapsulation — groups related data
 * (visited cells, solution path, solved flag) into one object.
 */
public class SolveResult {

    private final List<Cell> visitedOrder;   // cells explored (in order)
    private final List<Cell> solutionPath;   // shortest/found path from start → end
    private final boolean solved;            // true if a path was found

    public SolveResult(List<Cell> visitedOrder, List<Cell> solutionPath, boolean solved) {
        this.visitedOrder  = visitedOrder;
        this.solutionPath  = solutionPath;
        this.solved        = solved;
    }

    public List<Cell> getVisitedOrder()  { return visitedOrder; }
    public List<Cell> getSolutionPath()  { return solutionPath; }
    public boolean isSolved()            { return solved; }
}
