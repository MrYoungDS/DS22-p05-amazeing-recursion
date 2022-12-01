package maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import config.Configuration;

public class MazeBuilderTest {

	private MazeBuilder builder;

	private String exitDesc = "This room is hung with hundreds of "
			+ "dusty tapestries. All show signs of wear: moth "
			+ "holes, scorch marks, dark stains, and the damage "
			+ "of years of neglect. They hang on all the walls "
			+ "and hang from the ceiling to brush against the floor, "
			+ "blocking your view of the rest of the room.";

	private String basicDesc = "You open the door, and the reek "
			+ "of garbage assaults your nose. Looking inside, "
			+ "you see a pile of refuse and offal that nearly "
			+ "reaches the ceiling. In the ceiling above it is "
			+ "a small hole that is roughly as wide as two human "
			+ "hands. No doubt some city dweller high above disposes "
			+ "of his rubbish without ever thinking about where it goes.";

	@BeforeEach
	public void setup() {
		builder = Configuration.getMazeBuilder();
		assertNotNull(builder,
				"You seem to have forgotten to set which builder to use in the Configuration.getMazeBuilder method.");
		assertNotEquals(builder, Configuration.getMazeBuilder(),
				"You should return a new instance of MazeBuilder when Configuration.getMazeBuilder() is called.");
	}

	@Test
	public void testCreateRoom() {
		Room room = builder.createRoom(basicDesc, "A smelly room.");
		assertFalse(room.isExit());
		assertEquals(basicDesc, room.getFullDescription());
		assertEquals("A smelly room.", room.getShortDescription());
		assertTrue(room.getRooms().isEmpty());
		assertEquals(0, room.getRooms().size());
	}

	@Test
	public void testCreateExit() {
		Room room = builder.createExit(exitDesc, "A dusty room.");
		assertTrue(room.isExit());
		assertEquals(exitDesc, room.getFullDescription());
		assertEquals("A dusty room.", room.getShortDescription());
		assertTrue(room.getRooms().isEmpty());
		assertEquals(0, room.getRooms().size());
	}

	@Test
	public void testAddPassage() {
		Room r0 = builder.createRoom(basicDesc, "A smelly room.");
		Room r1 = builder.createRoom(exitDesc, "A dusty room.");
		builder.addPassage(r0, r1);
		assertFalse(r0.getRooms().isEmpty());
		assertFalse(r1.getRooms().isEmpty());
		
		assertEquals(1, r0.getRooms().size());
		assertEquals(1, r1.getRooms().size());
		
		assertEquals(0, r0.getRooms().contains(r1));
		assertEquals(0, r1.getRooms().contains(r0));
		
		builder.addPassage(r0, r1);
		assertFalse(r0.getRooms().isEmpty());
		assertFalse(r1.getRooms().isEmpty());
		
		assertEquals(1, r0.getRooms().size());
		assertEquals(1, r1.getRooms().size());
		
		assertEquals(0, r0.getRooms().contains(r1));
		assertEquals(0, r1.getRooms().contains(r0));
	}
	
	@Test
	public void testBuildCircularRooms() {
		Room r0 = builder.createRoom(basicDesc, "A smelly room.");
		Room r1 = builder.createRoom(exitDesc, "A dusty room.");
		Room r2 = builder.createRoom("Another room", "meh.");
		Room r3 = builder.createRoom("Fourth room", "more.");

		builder.addPassage(r0, r1);
		builder.addPassage(r1, r2);
		builder.addPassage(r2, r3);
		builder.addPassage(r3, r0);

		assertEquals(2, r0.getRooms().size());
		assertEquals(2, r1.getRooms().size());
		assertEquals(2, r2.getRooms().size());
		assertEquals(2, r3.getRooms().size());
		
		assertNotEquals(-1, r0.getRooms().contains(r1));
		assertNotEquals(-1, r0.getRooms().contains(r3));
		
		assertNotEquals(-1, r1.getRooms().contains(r0));
		assertNotEquals(-1, r1.getRooms().contains(r2));
		
		assertNotEquals(-1, r2.getRooms().contains(r1));
		assertNotEquals(-1, r2.getRooms().contains(r3));
		
		assertNotEquals(-1, r3.getRooms().contains(r0));
		assertNotEquals(-1, r3.getRooms().contains(r2));
	}
	
	@Test
	public void testAddOneWayPassage() {
		Room r0 = builder.createRoom(basicDesc, "A smelly room.");
		Room r1 = builder.createRoom(exitDesc, "A dusty room.");

		builder.addOneWayPassage(r0, r1);
		assertFalse(r0.getRooms().isEmpty());
		assertTrue(r1.getRooms().isEmpty());
		
		assertEquals(1, r0.getRooms().size());
		assertEquals(0, r1.getRooms().size());
		
		assertEquals(0, r0.getRooms().contains(r1));
		assertEquals(-1, r1.getRooms().contains(r0));
		
		builder.addOneWayPassage(r0, r1);
		assertFalse(r0.getRooms().isEmpty());
		assertTrue(r1.getRooms().isEmpty());
		
		assertEquals(1, r0.getRooms().size());
		assertEquals(0, r1.getRooms().size());
		
		assertEquals(0, r0.getRooms().contains(r1));
		assertEquals(-1, r1.getRooms().contains(r0));
	}
	
	@Test
	public void testNPE1(){
		Room r0 = builder.createRoom(basicDesc, "A smelly room.");
		assertThrows(NullPointerException.class,
				() -> builder.addPassage(null, r0));
	}
	
	@Test
	public void testNPE2(){
		Room r0 = builder.createRoom(basicDesc, "A smelly room.");
		assertThrows(NullPointerException.class,
				() -> builder.addPassage(r0, null));
	}
	
	@Test
	public void testNPE3(){
		assertThrows(NullPointerException.class,
				() -> builder.addPassage(null, null));
	}
	
	@Test
	public void testNPE4(){
		assertThrows(NullPointerException.class,
				() -> builder.createRoom(null, null));
	}
	
	@Test
	public void testNPE5(){
		assertThrows(NullPointerException.class,
				() -> builder.createRoom("Something", null));
	}
	
	@Test
	public void testNPE6(){
		assertThrows(NullPointerException.class,
				() -> builder.createRoom(null, "Something"));
	}
	
	@Test
	public void testNPE7(){
		assertThrows(NullPointerException.class,
				() -> builder.createExit(null, null));
	}
	
	@Test
	public void testNPE8(){
		assertThrows(NullPointerException.class,
				() -> builder.createRoom("Something", null));
	}
	
	@Test
	public void testNPE9(){
		assertThrows(NullPointerException.class,
				() -> builder.createRoom(null, "Something"));
	}
	
	@Test
	public void testNPE10(){
		assertThrows(NullPointerException.class,
				() -> builder.addOneWayPassage(null, null));
	}
	
	@Test
	public void testNPE11(){
		Room r0 = builder.createRoom(basicDesc, "A smelly room.");
		assertThrows(NullPointerException.class,
				() -> builder.addOneWayPassage(r0, null));
	}

	@Test
	public void testNPE12(){
		Room r0 = builder.createRoom(basicDesc, "A smelly room.");
		assertThrows(NullPointerException.class,
				() -> builder.addOneWayPassage(null, r0));
	}
}

