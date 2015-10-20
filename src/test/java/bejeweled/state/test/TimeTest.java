package bejeweled.state.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bejeweled.Main;
import bejeweled.state.Time;


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
		time.updateTime(1);
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
	
	/**
	 * Tests the toString() method.
	 * toString should give back "Time left: m:ss"
	 */
	@Test
	public final void testToString() {
		assertEquals("Time left: 1:30", time.toString());
		time.setTime(59);
		assertEquals("Time left: 0:59", time.toString());
	}
}
