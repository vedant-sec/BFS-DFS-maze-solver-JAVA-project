package mazesolver.solver;

import mazesolver.model.Cell;
import mazesolver.model.Maze;

import java.util.*;

/**
 * Solves the maze using Depth-First Search (DFS).
 * DFS dives deep into one path before backtracking.
 * It does NOT guarantee the shortest path.
 * 
 * OOP Concepts:
 *   Inheritance  — extends AbstractSolver to reuse buildPath().
 *   Polymorphism — can be used wherever MazeSolver is expected.
 */
public class DFSSolver extends AbstractSolver {

    @Override
    public SolveResult solve(Maze maze) {
        List<Cell> visitedOrder = new ArrayList<>();
        Cell start = maze.getStartCell();
        Cell end   = maze.getEndCell();

        // DFS uses a Stack (LIFO)
        Stack<Cell> stack = new Stack<>();
        Set<Cell> visited = new HashSet<>();
        Map<Cell, Cell> parentMap = new HashMap<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            visitedOrder.add(current);

            // Found the end — build and return the path
            if (current == end) {
                List<Cell> path = buildPath(parentMap, end);
                return new SolveResult(visitedOrder, path, true);
            }

            // Explore all walkable neighbours
            for (Cell neighbor : maze.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    stack.push(neighbor);
                }
            }
        }

        // No path found
        return new SolveResult(visitedOrder, Collections.emptyList(), false);
    }

    @Override
    public String getName() {
        return "DFS";
    }
}
