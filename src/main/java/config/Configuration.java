package config;

import maze.Maze;
import maze.MazeBuilder;
import maze.MazeSolution;
import structure.ListInterface;

/**
 * This class is a configuration file which tells the testing framework
 * which implementation to for grading.
 * @author jcollard, jddevaug
 */
public final class Configuration {
    /**
     * Private constructor to prevent class instantiation.
     */
    private Configuration() {
    }


    /**
     * Returns a new instance of the {@link ListInterface} to be graded.
     * @param <T>
     *        the type of the {@link ListInterface}
     * @return
     *        the {@link ListInterface} to be graded
     */
    public static <T> ListInterface<T> getListInterface() {
        return null;
    }

    /**
     * Returns a new instance of the {@link MazeBuilder} to be graded.
     * @return
     *        the {@link MazeBuilder} to be graded
     */
    public static MazeBuilder getMazeBuilder() {
        return null;
    }

    /**
     * Given a {@link Maze}, returns a new instance of the {@link MazeSolution}
     * to be graded.
     * @param maze the {@link Maze} that must be solved
     * @return
     *        the {@link MazeSolution} to be graded
     */
    public static MazeSolution getMazeSolution(final Maze maze) {
        return null;
    }
}
