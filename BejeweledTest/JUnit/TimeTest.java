import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TimeTest {
	private Time time;
	
	/**
	 * Set up a Time object before every test.
	 */
	@Before
	public final void setUp() {
		time = new Time(90);
	}
	
	/**
	 * Tests the updateTime method.
	 */
	@Test
	public final void testUpdateTime() {
		assertEquals(90, time.getTime());
		time.updateTime();
		assertEquals(91, time.getTime());
	}
	
	/**
	 * Tests the decrementTime method.
	 */
	@Test
	public final void testDecrementTime() {
		assertEquals(90, time.getTime());
		time.decrementTime();
		assertEquals(89, time.getTime());
	}

}
