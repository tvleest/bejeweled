package bejeweled.board.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bejeweled.board.Gem;
import bejeweled.board.GemType;

/**
 * Tests the Gem class.
 * @author Job
 *
 */
public class GemTest {
	
	/**
	 * The gem that's being used for the tests.
	 */
	private Gem gem;
	
	/**
	 * Initializes the Gem for the tests, is run before every testcase.
	 */
	@Before
	public final void setUp() {
		gem = new Gem(0, 0, GemType.RED, false);
	}

	/**
	 * Tests the setType and getType methods, by switching a Gems type.
	 */
	@Test
	public final void typeTest() {
		gem.setType(GemType.BLUE);
		assertEquals(gem.getType(), GemType.BLUE);
	}

	/**
	 * Tests the setPosition, getRow and getCol methods, 
	 * by switching the position and checking
	 * if the outcome from getRow and getCol have changed.
	 */
	@Test
	public final void positionTest() {
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
	public final void selectedTest() {
		assertFalse(gem.isSelected());
		gem.setSelected(true);
		assertTrue(gem.isSelected());
		gem.setSelected(false);
		assertFalse(gem.isSelected());
	}
}
