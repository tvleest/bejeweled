package bejeweled.game.test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bejeweled.Difficulties;
import bejeweled.Main;
import bejeweled.board.Board;
import bejeweled.board.Gem;
import bejeweled.board.GemType;
import bejeweled.board.MediumGameFactory;
import bejeweled.game.GameLogic;

/**
 * Tests for the GameLogic class.
 * @author Group 30
 *
 */
public class GameLogicTest {
	
	GameLogic gl;

	/**
	 * Set up before every test.
	 */
	@Before
	public final void setUp() {
		gl = new GameLogic(Difficulties.MEDIUM);
	}
	
	/**
	 * Tests the getBoard method.
	 */
	@Test
	public final void testGetBoard() {
		Board b = new Board(8, new MediumGameFactory());
		assertEquals(b, gl.getBoard());
	}
	
	/**
	 * Tests the mouse handler for selecting gems.
	 */
	@Test
	public final void testHandleMouseClicked() {
		Board b = gl.getBoard();
		Gem[][] g = b.getGems();
		g[0][0].setType(GemType.RED);
		g[0][1].setType(GemType.BLUE);
		g[0][2].setType(GemType.BLUE);
		g[0][3].setType(GemType.GREEN);
		g[1][0].setType(GemType.BLUE);
		g[1][1].setType(GemType.ORANGE);
		gl.handleMouseClicked(0, 0);
		assertEquals(b.getSelectedgem(), b.getGems()[0][0]);
	}
}
