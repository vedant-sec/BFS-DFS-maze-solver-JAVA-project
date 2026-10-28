package mazesolver.solver;

import mazesolver.model.Maze;

/**
 * Interface that every maze-solving algorithm must implement.
 * 
 * OOP Concepts:
 *   Abstraction  — hides the algorithm details behind a common contract.
 *   Polymorphism — any MazeSolver can be passed to the GUI; the
 *                  correct algorithm runs at runtime.
 */
public interface MazeSolver {

    /**
     * Solves the given maze and returns the result.
     * @param maze the maze to solve
     * @return SolveResult containing visited cells & solution path
     */
    SolveResult solve(Maze maze);

    /**
     * Returns the display name of this solver (e.g. "BFS", "DFS").
     */
    String getName();
}
