package mazesolver.solver;

import mazesolver.model.Cell;
import mazesolver.model.Maze;

import java.util.*;

/**
 * Solves the maze using Breadth-First Search (BFS).
 * BFS explores level by level, guaranteeing the shortest path.
 * 
 * OOP Concepts:
 *   Inheritance  — extends AbstractSolver to reuse buildPath().
 *   Polymorphism — can be used wherever MazeSolver is expected.
 */
public class BFSSolver extends AbstractSolver {

    @Override
    public SolveResult solve(Maze maze) {
        List<Cell> visitedOrder = new ArrayList<>();
        Cell start = maze.getStartCell();
        Cell end   = maze.getEndCell();

        // BFS uses a Queue (FIFO)
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();
        Map<Cell, Cell> parentMap = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
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
                    queue.add(neighbor);
                }
            }
        }

        // No path found
        return new SolveResult(visitedOrder, Collections.emptyList(), false);
    }

    @Override
    public String getName() {
        return "BFS";
    }
}
