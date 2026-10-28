# BFS / DFS Maze Solver (Java)

A Java Swing maze solver that generates random mazes and solves them with
**Breadth-First Search (BFS)** and **Depth-First Search (DFS)**, animating the
exploration and the final path.

Built as an OOP course project to demonstrate encapsulation, inheritance,
polymorphism, and abstraction.

## Features

- Random maze generation using **Recursive Backtracking** (always a perfect maze).
- Two interchangeable solvers behind a common interface:
  - **BFS** — explores level by level and guarantees the shortest path.
  - **DFS** — dives deep first, may return a longer path.
- Animated step-by-step visualisation of explored cells and the final path.
- Adjustable maze size (5–30) and animation speed.
- Status bar reporting path length and number of cells explored.

## Project structure

```
src/mazesolver/
├── Main.java                    # Entry point, starts the GUI on the EDT
├── model/
│   ├── Cell.java                # A single grid cell
│   ├── CellType.java            # WALL, PATH, START, END, VISITED, SOLUTION
│   └── Maze.java                # Grid + recursive-backtracking generator
├── solver/
│   ├── MazeSolver.java          # Solver interface (abstraction)
│   ├── AbstractSolver.java      # Shared path reconstruction (inheritance)
│   ├── BFSSolver.java           # Breadth-First Search
│   ├── DFSSolver.java           # Depth-First Search
│   └── SolveResult.java         # Visited order + solution path (encapsulation)
└── gui/
    ├── MazeApp.java             # JFrame, controls, animation timer
    └── MazePanel.java           # Custom painting of the maze
```

## Requirements

- JDK 8 or newer (`javac` and `java` on the `PATH`).

## Run

Windows:

```bat
run.bat
```

The script cleans `out/`, compiles every source file into `out/`, and launches
`mazesolver.Main`.

Manually, on any OS:

```bash
javac -d out src/mazesolver/model/*.java src/mazesolver/solver/*.java src/mazesolver/gui/*.java src/mazesolver/Main.java
java -cp out mazesolver.Main
```

## Usage

1. Pick a maze size with the spinner (5–30).
2. Click **Generate Maze**.
3. Click **Solve (BFS)** or **Solve (DFS)** and watch the animation.
4. Use the **Speed** slider to slow down or speed up the animation.
5. Click **Reset** to clear the solution and solve again.

## OOP concepts demonstrated

| Concept       | Where                                                        |
| ------------- | ------------------------------------------------------------ |
| Encapsulation | `Maze` keeps its grid private; `SolveResult` bundles results  |
| Inheritance   | `BFSSolver` / `DFSSolver` extend `AbstractSolver`             |
| Polymorphism  | Both solvers are used through the `MazeSolver` interface      |
| Abstraction   | `MazeSolver` defines *what* a solver does, not *how*          |
