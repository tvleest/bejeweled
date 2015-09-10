import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Gem class.
 * @author Job
 *
 */
public class GemTest {
	
	/**
	 * The gem that's being used for the tests.
	 */
	Gem gem;
	
	/**
	 * Initializes the Gem for the tests, is run before every testcase.
	 */
	@Before
	public void setUp() {
		gem = new Gem(0,0,0,0,1,false);
	}

	/**
	 * Tests the setType and getType methods, by switching a Gems type.
	 */
	@Test
	public void typeTest() {
		gem.setType(2);
		assertEquals(gem.getType(), 2);
	}
	
	/**
	 * Tests the delete method, by running in on the Gem and checking of the type is right.
	 */
	@Test
	public void deleteTest() {
		gem.delete();
		assertEquals(gem.getType(), 0);
	}

	/**
	 * Tests the setPosition, getRow and getCol methods, by switching the position and checking
	 * if the outcome from getRow and getCol have changed.
	 */
	@Test
	public void positionTest() {
		assertEquals(gem.getRow(), 0);
		assertEquals(gem.getCol(), 0);
		gem.setPosition(1, 2);
		assertEquals(gem.getRow(), 1);
		assertEquals(gem.getCol(), 2);
	}
	
	/**
	 * Tests the isSelected and setSelected methods, by changing the 
	 * selected state and checking if it has changed.
	 */
	@Test
	public void selectedTest() {
		assertFalse(gem.isSelected());
		gem.setSelected(true);
		assertTrue(gem.isSelected());
		gem.setSelected(false);
		assertFalse(gem.isSelected());
	}
}
