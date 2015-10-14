package bejeweled.board.test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import bejeweled.board.Board;
import bejeweled.board.Gem;
import bejeweled.board.GemType;

/**
 * @author Timo
 *
 */
public class BoardTest {
	
	Board board;
	Gem[][] gems;

	/**
	 * Tests the fillBoard method, it check for every place on the board if
	 * there is a Gem with a type from 1 to 6.
	 */
	@Test
	public final void testFillBoardIntInt() {
		board = new Board(3);
		board.fillBoard(0, 0);
		gems = board.getGems();
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				assertNotNull(gems[row][col].getType());
				assertNotNull(gems[row][col].getType());
			}
		}
	}

	/**
	 * Tests the rowCheck method, by changing some types in a row
	 * and checking if the returned boolean is right.
	 */
	@Test
	public final void testRowCheck() {
		board = new Board(3);
		gems = board.getGems();
		gems[0][0].setType(GemType.RED);
		gems[0][1].setType(GemType.RED);
		assertFalse(board.rowCheck(0, 2, GemType.RED));
		gems[0][0].setType(GemType.BLUE);
		assertTrue(board.rowCheck(0, 2, GemType.RED));
	}

	/**
	 * Tests the colCheck method, by changing some types in a col
	 * and checking if the returned boolean is right.
	 */
	@Test
	public final void testColCheck() {
		board = new Board(3);
		gems = board.getGems();
		gems[0][0].setType(GemType.RED);
		gems[1][0].setType(GemType.RED);
		assertFalse(board.colCheck(2, 0, GemType.RED));
		gems[0][0].setType(GemType.BLUE);
		assertTrue(board.colCheck(2, 0, GemType.RED));
	}

	/**
	 * Tests the delete method, by deleting a gem and 
	 * checking if the gem above it has shoved
	 */
	@Test
	public final void testDelete() {
		board = new Board(3);
		gems = board.getGems();
		gems[0][0] = new Gem(0, 0, GemType.RED);
		gems[1][0] = new Gem(1, 0, GemType.RED);
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[1][0];
		board.delete(gem2, null);
		assertEquals(gems[1][0], gem1);
	}

	/**
	 * Tests the swap method by swapping two gems and 
	 * checking if their position has changed.
	 * Also, it checks whether the gems are not changed 
	 * if they are not next to each other.
	 */
	@Test
	public final void testSwap() {
		board = new Board(2);
		gems = board.getGems();
		Gem gemnoswap = gems[1][1];
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		assertFalse(board.swap(gem1, gemnoswap));
		assertTrue(board.swap(gem1, gem2));
		assertEquals(gems[0][0], gem2);
		assertEquals(gems[0][1], gem1);
		assertTrue(board.swap(gem1, gem2));
	}

	/**
	 * Tests the getUpper method by checking if 
	 * the getUpper method returns the right Gem.
	 */
	@Test
	public final void testGetUpper() {
		board = new Board(2);
		gems = board.getGems();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		Gem gem3 = gems[1][0];
		assertEquals(board.getUpper(gem3), gem1);
		assertFalse(board.getUpper(gem3) == gem2);
	}

	/**
	 * Tests the getRight method by checking if 
	 * the getRight method returns the right Gem.
	 */
	@Test
	public final void testGetRight() {
		board = new Board(2);
		gems = board.getGems();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		Gem gem3 = gems[1][1];
		assertEquals(board.getRight(gem1), gem2);
		assertFalse(board.getRight(gem1) == gem3);
	}

	/**
	 * Tests the getBelow method by checking if 
	 * the getBelow method returns the right Gem.
	 */
	@Test
	public final void testGetBelow() {
		board = new Board(2);
		gems = board.getGems();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		Gem gem3 = gems[1][0];
		assertEquals(board.getBelow(gem1), gem3);
		assertFalse(board.getBelow(gem1) == gem2);
	}

	/**
	 * Tests the getLeft method by checking 
	 * if the getLeft method returns the right Gem.
	 */
	@Test
	public final void testGetLeft() {
		board = new Board(2);
		gems = board.getGems();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		Gem gem3 = gems[1][0];
		assertEquals(board.getLeft(gem2), gem1);
		assertFalse(board.getLeft(gem2) == gem3);
	}

	/**
	 * Tests the areNeighbours method, by testing 
	 * neighbours and also testing Gems that aren't neighbours.
	 */
	@Test
	public final void testAreNeighbours() {
		board = new Board(2);
		gems = board.getGems();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		Gem gem3 = gems[1][0];
		Gem gem4 = gems[1][1];
		assertTrue(board.areNeighbours(gem1, gem2));
		assertTrue(board.areNeighbours(gem1, gem3));
		assertFalse(board.areNeighbours(gem1, gem4));
	}

	/**
	 * Tests the setSelectedgem and getSelectedgem methods, by first 
	 * setting a Gem on selected and then checking with getSelectedgem
	 * if it was selected.
	 */
	@Test
	public final void selectedGemTest() {
		board = new Board(2);
		gems = board.getGems();
		Gem gem = gems[0][0];
		assertNull(board.getSelectedgem());
		board.setSelectedgem(gem);
		assertEquals(board.getSelectedgem(), gem);
	}
	
	/**
	 * Tests the setSecondGem and getSecondGem methods, by first setting a Gem
	 * on second and then checking with getSecondGem if it was second.
	 */
	@Test
	public final void secondGemTest() {
		board = new Board(2);
		gems = board.getGems();
		Gem gem = gems[0][0];
		assertNull(board.getSecondGem());
		board.setSecondGem(gem);
		assertEquals(board.getSecondGem(), gem);
	}
	
	/**
	 * Tests the setGems and getGems methods, by first 
	 * setting Gems[][] testgems, and then checking whether it was 
	 * set with the getGems method.
	 */
	@Test
	public final void setGemTest() {
		board = new Board(2);
		Gem[][] testgems = new Gem[2][2];
		board.setGems(testgems);
		assertArrayEquals(testgems, board.getGems());
	}
	
	/**
	 * Tests the equals method by comparing 2 equals 
	 * board and the different boards.
	 */
	@Test
	public final void testEquals() {
		board = new Board(2);
		Board board2 = new Board(2);
		Board board3 = new Board(3);
		assertEquals(board, board2);
		assertFalse(board.equals(board3));
	}
}


