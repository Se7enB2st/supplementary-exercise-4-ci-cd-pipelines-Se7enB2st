package edu.pitt.cs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatUnitTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	ByteArrayOutputStream out; // Output stream for testing system output
	PrintStream stdout; // Print stream to hold the original stdout stream
	String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n") for use in assertEquals

	@Before
	public void setUp() throws Exception {
		// INITIALIZE THE TEST FIXTURE
		
		// 1. Create a new RentACat object and assign to r using a call to RentACat.createInstance(InstanceType.IMPL).
		r = RentACat.createInstance(InstanceType.IMPL);
		
		// 2. Create a Cat with ID 1 and name "Jennyanydots", assign to c1 using a call to Cat.createInstance(InstanceType.MOCK, 1, "Jennyanydots");
		c1 = Cat.createInstance(InstanceType.MOCK, 1, "Jennyanydots");

		// 3. Create a Cat with ID 2 and name "Old Deuteronomy", assign to c2 using a call to Cat.createInstance(InstanceType.MOCK, 2, "Old Deuteronomy");
		c2 = Cat.createInstance(InstanceType.MOCK, 2, "Old Deuteronomy");

		// 4. Create a Cat with ID 3 and name "Mistoffelees", assign to c3 using a call to Cat.createInstance(InstanceType.MOCK, 3, "Mistoffelees");
		c3 = Cat.createInstance(InstanceType.MOCK, 3, "Mistoffelees");

		// 5. Redirect system output from stdout to the "out" stream
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
	}

	@After
	public void tearDown() throws Exception {
		// Restore System.out to the original stdout
		System.setOut(stdout);

		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix 
	 * hapter on using reflection on how to do this.  Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNullNumCats0() {
		try {
			java.lang.reflect.Method m = r.getClass().getDeclaredMethod("getCat", int.class);
			m.setAccessible(true);
			Cat result = (Cat) m.invoke(r, 2);
			assertNull(result);
			assertEquals("Invalid cat ID." + newline, out.toString());
		} catch (Exception e) {
			fail("Reflection failed: " + e.getMessage());
		}
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix 
	 * hapter on using reflection on how to do this.  Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		try {
			java.lang.reflect.Method m = r.getClass().getDeclaredMethod("getCat", int.class);
			m.setAccessible(true);
			Cat result = (Cat) m.invoke(r, 2);
			assertNotNull(result);
			assertEquals(2, result.getId());
		} catch (Exception e) {
			fail("Reflection failed: " + e.getMessage());
		}
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats0() {
		assertEquals("", r.listCats());
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		String expected = "ID 1. Jennyanydots" + newline + "ID 2. Old Deuteronomy" + newline + "ID 3. Mistoffelees" + newline;
		System.err.println("ACTUAL: [" + r.listCats().replace("\n", "\\n") + "]");
		System.err.println("EXPECTED: [" + expected.replace("\n", "\\n") + "]");
		assertEquals(expected.trim(), r.listCats().replace("\n", newline).trim());
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is false.
	 *                 c2 is not renamed to "Garfield".
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRenameFailureNumCats0() {
		boolean result = r.renameCat(2, "Garfield");
		assertFalse(result);
		verify(c1, never()).renameCat(anyString());
		verify(c2, never()).renameCat(anyString());
		verify(c3, never()).renameCat(anyString());
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is true.
	 *                 c2 is renamed to "Garfield".
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRenameNumCat3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		when(c2.getId()).thenReturn(2);
		boolean result = r.renameCat(2, "Garfield");
		assertTrue(result);
		verify(c2).renameCat("Garfield");
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is rented as a result of the execution steps.
	 *                 System output is "Old Deuteronomy has been rented." + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRentCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		when(c2.getId()).thenReturn(2);
		when(c2.getRented()).thenReturn(false);
		boolean result = r.rentCat(2);
		assertTrue(result);
		verify(c2).rentCat();
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 is not rented as a result of the execution steps.
	 *                 System output is "Sorry, Old Deuteronomy is not here!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRentCatFailureNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		when(c2.getId()).thenReturn(2);
		when(c2.getRented()).thenReturn(true);
		boolean result = r.rentCat(2);
		assertFalse(result);
		verify(c2, never()).rentCat();
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is returned as a result of the execution steps.
	 *                 System output is "Welcome back, Old Deuteronomy!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testReturnCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		when(c2.getId()).thenReturn(2);
		when(c2.getRented()).thenReturn(true);
		boolean result = r.returnCat(2);
		assertTrue(result);
		verify(c2).returnCat();
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 is not returned as a result of the execution steps.
	 *                 System output is "Old Deuteronomy is already here!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testReturnFailureCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		when(c2.getId()).thenReturn(2);
		when(c2.getRented()).thenReturn(false);
		boolean result = r.returnCat(2);
		assertFalse(result);
		verify(c2, never()).returnCat();
	}

}