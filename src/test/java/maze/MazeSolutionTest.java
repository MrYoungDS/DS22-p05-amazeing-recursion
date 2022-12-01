package maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import structure.ListInterface;
import config.Configuration;

public class MazeSolutionTest {

	private MazeBuilder builder;

	// NOTICE: This test assumes your builder works. If you find errors, it is
	// possible that your MazeBuilder implementation is broken.

	@BeforeEach
	public void setup() {
		builder = Configuration.getMazeBuilder(); 
		assertNotNull(builder,
				"Need to set your builder in Configuration.getMazeBuilder().");
		assertNotEquals(builder, Configuration.getMazeBuilder(),
				"Should return a new instance of MazeBuilder when getMazeBuilder is called.");
	}

	@Test
	public void testSimpleMaze() {
		Room r0 = builder.createExit("This is an exit", "very simple.");
		Maze maze = new Maze(r0);
		MazeSolution solution = Configuration.getMazeSolution(maze);
		assertNotNull(solution,	"You seem to have forgotten to set which MazeSolution to use in the Configuration.getMazeSolution method.");
		
		assertEquals(maze, solution.getMaze(), "The mazes should match.");
		ListInterface<Room> rooms = solution.getSolution();
		assertFalse(rooms.isEmpty());
		assertEquals(1, rooms.size(), "The solution should contain 1 room");
		assertEquals(r0, rooms.getFirst(), "The one room should be the exit.");
	}

	private void checkSolution(Room start, ListInterface<Room> rooms){
		assertEquals(start, rooms.getFirst());
 
		while(!rooms.isEmpty()){
			Room current = rooms.removeFirst();
			if(rooms.isEmpty()){
				assertTrue(current.isExit(), "Last room in solution was not an exit.");
				return;
			}
			Room movingTo = rooms.getFirst();
			assertNotEquals(-1, current.getRooms().contains(movingTo),
					"The room " + current.getFullDescription() + " did not contain a path to " + movingTo.getFullDescription());
		}
	}
	
	@Test
	public void testFourRoomMaze() {
		Room r0 = builder.createRoom("An orange room!", "Orange Hallway");
		Room r1 = builder.createRoom("A red room!", "Red Hallway");
		Room r2 = builder.createRoom("A blue room!", "Blue Hallway.");
		Room r3 = builder.createExit("A purple room!", "Purple Hallway.");

		builder.addOneWayPassage(r0, r1).addPassage(r1, r2).addOneWayPassage(r2, r0).addOneWayPassage(r2, r3).addOneWayPassage(r3, r1);
		Maze maze = new Maze(r0);
		MazeSolution solution = Configuration.getMazeSolution(maze);
		assertNotNull(solution,
				"You seem to have forgotten to set which MazeSolution to use in the Configuration.getMazeSolution method.");
		
		assertEquals(maze, solution.getMaze(), "The mazes should match.");
		ListInterface<Room> rooms = solution.getSolution();
		checkSolution(r0, rooms);
	}
	
	@Test
	public void testFourRoomMazeNoExit() {
		Room r0 = builder.createRoom("An orange room!", "Orange Hallway");
		Room r1 = builder.createRoom("A red room!", "Red Hallway");
		Room r2 = builder.createRoom("A blue room!", "Blue Hallway.");
		Room r3 = builder.createRoom("A purple room!", "Purple Hallway.");

		builder.addOneWayPassage(r0, r1).addPassage(r1, r2).addOneWayPassage(r2, r0).addOneWayPassage(r2, r3).addOneWayPassage(r3, r1);
		Maze maze = new Maze(r0);
		MazeSolution solution = Configuration.getMazeSolution(maze);
		assertThrows(UnsolvableMazeException.class,
				() -> solution.getSolution());
	}
	
	@Test
	public void testDeadEndMaze() {
		Room r0 = builder.createRoom("An orange room!", "Orange Hallway");
		Room r1 = builder.createRoom("A red room!", "Red Hallway");
		Room r2 = builder.createRoom("A blue room!", "Blue Hallway.");
		Room r3 = builder.createExit("A purple room!", "Purple Hallway.");

		builder.addOneWayPassage(r0, r1).addPassage(r0, r2).addPassage(r2, r3);
		Maze maze = new Maze(r0);
		MazeSolution solution = Configuration.getMazeSolution(maze);
		assertNotNull(solution,
				"You seem to have forgotten to set which MazeSolution to use in the Configuration.getMazeSolution method.");
		
		assertEquals(maze, solution.getMaze(), "The mazes should match.");
		ListInterface<Room> rooms = solution.getSolution();
		checkSolution(r0, rooms);
	}
}
