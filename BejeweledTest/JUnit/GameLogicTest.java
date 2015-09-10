import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class GameLogicTest {
	
	GameLogic gl;

	@Before
	public void setUp() {
		Main m = new Main();
		gl = new GameLogic(0, 0, m, false);
	}
	
	@Test
	public void testGetBoard() {
		Board b = new Board(8,0,0,false);
		assertEquals(b, gl.getBoard());
	}

	@Test
	public void testHandleMouseClicked() {
		Board b = gl.getBoard();
		Gem[][] g = b.getGems();
		g[0][0].setType(1);
		g[0][1].setType(2);
		g[0][2].setType(2);
		g[0][3].setType(3);
		g[1][0].setType(2);
		g[1][1].setType(4);
		gl.handleMouseClicked(0, 0);
		assertEquals(b.getSelectedgem(), b.getGems()[0][0]);
		gl.handleMouseClicked(0, 1);
		assertNull(b.getSelectedgem());
		gl.handleMouseClicked(0, 0);
		gl.handleMouseClicked(1, 0);
		assertEquals(gl.getTime(), 105);
	}

	@Test
	public void testUpdateTime() {
		assertEquals(90, gl.getTime());
		gl.updateTime();
		assertEquals(95, gl.getTime());
	}

	@Test
	public void testDecrementTime() {
		assertEquals(90, gl.getTime());
		gl.decrementTime();
		assertEquals(89, gl.getTime());
	}


}
