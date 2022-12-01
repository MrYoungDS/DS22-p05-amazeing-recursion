package structure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import config.Configuration;

public class ListInterfaceTest {

	private ListInterface<String> list;
	
	@BeforeEach
	public void setup(){
		list = Configuration.getListInterface();
		ListInterface<String> l2 = Configuration.getListInterface();
		assertNotNull(list,
				"Need to set your list in Configuration.getListInterface().");
		assertNotEquals(list, l2,
				"Should return a new instance of list when getListInterface is called.");
	}
	
	@Test
	public void testInsertFirstIsEmptySizeAndGetFirst() {
		assertTrue(list.isEmpty(), "Newly constructed list should be empty.");
		assertEquals(0, list.size(), "Newly constructed list should be size 0.");
		assertEquals(list, list.insertFirst("hello"),
				"Insert First should return instance of self");
		assertFalse(list.isEmpty(), "List should now have elements.");
		assertEquals(1, list.size(), "List should now have 1 element.");
		assertEquals("hello", list.getFirst(), "First element should .equals \"hello\".");
		list.insertFirst("world");
		assertEquals(2, list.size());
		list.insertFirst("foo");
		assertEquals(3, list.size());
		assertEquals("foo", list.getFirst(),
				"First element should .equals \"foo\".");
	}
	
	@Test
	public void testInsertLastIsEmptySizeAndGetLast() {
		assertTrue(list.isEmpty(), "Newly constructed list should be empty.");
		assertEquals(0, list.size(), "Newly constructed list should have size 0.");
		assertEquals(list, list.insertLast("hello"),
				"Insert Last should return instance of self");
		assertFalse(list.isEmpty(), "List should now have elements.");
		assertEquals(1, list.size(), "List should now have 1 element.");
		assertEquals("hello", list.getLast(), "Last element should .equals \"hello\".");
		list.insertLast("world");
		assertEquals(2, list.size());
		list.insertLast("foo");
		assertEquals(3, list.size());
		assertEquals("foo", list.getLast(), "Last element should .equals \"foo\".");
	}
	
	@Test
	public void testInsertAtIsEmptySizeAndGetAt() {
		assertTrue(list.isEmpty(), "Newly constructed list should be empty.");
		assertEquals(0, list.size(), "Newly constructed list should be size 0.");
		assertEquals(list, list.insertAt(0, "hello"),
				"Insert At should return instance of self");
		assertFalse(list.isEmpty(), "List should now have elements.");
		assertEquals(1, list.size(), "List should now have 1 element.");
		assertEquals("hello", list.get(0),
				"Index 0 element should .equals \"hello\".");
		list.insertAt(1, "world");
		assertEquals("world", list.get(1),
				"Index 1 element should .equals \"world\".");
		assertEquals(2, list.size());
		list.insertAt(0, "foo");
		assertEquals(3, list.size());
		assertEquals("foo", list.get(0), "Index 0 element should .equals \"foo\".");
		assertEquals("hello", list.get(1), "Index 1 element should .equals \"hello\".");
		assertEquals("world", list.get(2), "Index 2 element should .equals \"world\".");
	}
	
	@Test
	public void testExceptionOnEmptyGetFirst() {
		assertThrows(IllegalStateException.class,
				() -> list.getFirst());
	}
	
	@Test
	public void testExceptionOnEmptyGetLast() {
		assertThrows(IllegalStateException.class,
				() -> list.getLast());
	}
	
	@Test
	public void testInsertFirstRemoveFirstSizeAndIsEmpty() {
		assertTrue(list.isEmpty(), "Newly constructed list should be empty.");
		list.insertFirst("hello").insertFirst("there").insertFirst("world");
		assertEquals(3, list.size(), "List should now have 3 elements");
		assertEquals("world", list.removeFirst());
		assertEquals(2, list.size(), "List should now have 2 elements");
		assertEquals("there", list.removeFirst());
		assertEquals(1, list.size(), "List should now have 1 elements");
		assertEquals("hello", list.removeFirst());
		assertEquals(0, list.size(), "List should now have 0 elements");
		assertTrue(list.isEmpty(), "All elements removed, list should be empty.");
	}
	
	@Test
	public void testInsertLastRemoveLastSizeAndIsEmpty() {
		assertTrue(list.isEmpty(), "Newly constructed list should be empty.");
		list.insertLast("hello").insertLast("there").insertLast("world");
		assertEquals(3, list.size(), "List should now have 3 elements");
		assertEquals("world", list.removeLast());
		assertEquals(2, list.size(), "List should now have 2 elements");
		assertEquals("there", list.removeLast());
		assertEquals(1, list.size(), "List should now have 1 elements");
		assertEquals("hello", list.removeLast());
		assertEquals(0, list.size(), "List should now have 0 elements");
		assertTrue(list.isEmpty(), "All elements removed, list should be empty.");
	}
	
	@Test
	public void testExceptionOnEmptyRemoveFirst() {
		assertThrows(IllegalStateException.class,
				() -> list.removeFirst());
	}
	
	@Test
	public void testExceptionOnEmptyRemoveLast() {
		assertThrows(IllegalStateException.class,
				() -> list.removeLast());
	}
	
	@Test
	public void testExceptionOnOutOfBounds1() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> list.removeAt(0));
	}
	
	@Test
	public void testExceptionOnOutOfBounds2() {
		list.insertFirst("hello");
		assertThrows(IndexOutOfBoundsException.class,
				() -> list.removeAt(1));
	}
	
	@Test
	public void testExceptionOnOutOfBounds3() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> list.removeAt(-5));
	}
	
	@Test
	public void testExceptionOnOutOfBounds4() {
		list.insertFirst("hello");
		assertThrows(IndexOutOfBoundsException.class,
				() -> list.removeAt(5));
	}
	
	@Test
	public void testInsertsGetsRemovesSize(){
		assertTrue(list.isEmpty(), "Newly constructed list should be empty.");
		list.insertLast("Hello").insertLast("World!");
		assertEquals(list, list.insertAt(1, "There"),
				"Insert at should return an instance of the list.");
		assertEquals(3, list.size(), "Size should be 3");
		assertEquals("Hello", list.get(0),
				"Index 0 element should .equals \"Hello\"");
		assertEquals("World!", list.getLast(),
				"Last element should .equals \"World!\"");
		list.insertAt(0, "foo").insertAt(4, "bar");
		assertEquals("foo", list.get(0));
		assertEquals("bar", list.get(4));
		assertEquals(5, list.size(), "Size should be 5");
		assertEquals("World!", list.removeAt(3),
				"Index 3 element should .equals \"World!\"");
		assertEquals(4, list.size(), "Size should be 4");
		assertEquals("bar", list.getLast(),
				"Last element should .equals \"bar\"");
	}
	
	@Test
	public void testInsertsRemoveAndContains(){
		list.insertLast("Hello").insertLast("World");
		assertEquals(0, list.contains("Hello"), "Hello is at index 0");
		assertEquals(1, list.contains("World"), "World is at index 1");
		assertEquals(-1, list.contains("Foo"), "Foo is not in the list.");
		assertTrue(list.remove("Hello"), "Hello can be removed.");
		assertEquals(1, list.size(), "Size should now be 1");
		list.insertLast("Hello").insertLast("There").insertLast("Hello");
		assertEquals(0, list.contains("World"), "World is at index 0");
		assertEquals(1, list.contains("Hello"), "First Hello is at index 1");
		assertTrue(list.remove("Hello"), "Hello can be removed.");
		assertEquals(2, list.contains("Hello"), "First Hello is at index 2");
		assertTrue(list.remove("Hello"), "Hello can be removed.");
		assertEquals(2, list.size(), "Size of list should now be 2");
		assertFalse(list.remove("Foo"), "Foo cannot be removed.");
		assertEquals(2, list.size(), "Size of list should now be 2");
		assertFalse(list.remove("Hello"), "Hello cannot be removed.");
	}
	
	@Test
	@Timeout(1)
	public void testSpeed() {
		for(int i = 0; i < 500000; i++){
			assertEquals(i, list.size());
			list.insertFirst("MORE!");
			list.getFirst();
		}
		
		while(!list.isEmpty())
			list.removeFirst();
	}
}
