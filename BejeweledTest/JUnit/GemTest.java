import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class GemTest {
	
	Gem gem;
	
	@Before
	public void setUp() {
		gem = new Gem(0,0,0,0,1,false);
	}

	@Test
	public void typeTest() {
		gem.setType(2);
		assertEquals(gem.getType(), 2);
	}
	
	@Test
	public void deleteTest() {
		gem.delete();
		assertEquals(gem.getType(), 0);
	}

	@Test
	public void positionTest() {
		assertEquals(gem.getRow(), 0);
		assertEquals(gem.getCol(), 0);
		gem.setPosition(1, 2);
		assertEquals(gem.getRow(), 1);
		assertEquals(gem.getCol(), 2);
	}
	
	@Test
	public void selectedTest() {
		assertFalse(gem.isSelected());
		gem.setSelected(true);
		assertTrue(gem.isSelected());
		gem.setSelected(false);
		assertFalse(gem.isSelected());
	}
}
