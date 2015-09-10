import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

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
	public void testFillBoardIntInt() {
		board = new Board(3,0,0,false);
		board.fillBoard(0,0);
		gems = board.getGems();
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				assertTrue(1 <= gems[row][col].getType());
				assertTrue(gems[row][col].getType() <= 6);
			}
		}
	}

	/**
	 * Tests the rowCheck method, by changing some types in a row
	 * and checking if the returned boolean is right.
	 */
	@Test
	public void testRowCheck() {
		board = new Board(3,0,0,false);
		gems = board.getGems();
		gems[0][0].setType(1);
		gems[0][1].setType(1);
		assertFalse(board.rowCheck(0, 2, 1));
		gems[0][0].setType(2);
		assertTrue(board.rowCheck(0, 2, 1));
	}

	/**
	 * Tests the colCheck method, by changing some types in a col
	 * and checking if the returned boolean is right.
	 */
	@Test
	public void testColCheck() {
		board = new Board(3,0,0,false);
		gems = board.getGems();
		gems[0][0].setType(1);
		gems[1][0].setType(1);
		assertFalse(board.colCheck(2, 0, 1));
		gems[0][0].setType(2);
		assertTrue(board.colCheck(2, 0, 1));
	}

	/**
	 * Tests the delete method, by deleting a gem and checking if it has disappeared
	 * and if the other gems are on the right place.
	 */
	@Test
	public void testDelete() {
		board = new Board(3,0,0,false);
		gems = board.getGems();
		gems[0][0].setType(1);
		gems[1][0].setType(1);
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[1][0];
		board.delete(2, 0);
		assertEquals(gems[2][0], gem2);
		assertEquals(gems[1][0], gem1);
	}

	/**
	 * Tests the swap method by swapping two gems and checking if their position has changed.
	 * Also, it checks whether the gems are not changed if they are not next to each other.
	 */
	@Test
	public void testSwap() {
		board = new Board(2,0,0,false);
		assertFalse(board.swap(0, 1, 1, 0));
		gems = board.getGems();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		assertTrue(board.swap(0, 0, 0, 1));
		assertEquals(gems[0][0], gem2);
		assertEquals(gems[0][1], gem1);
		assertTrue(board.swap(0, 0, 1, 0));
	}

	/**
	 * Tests the getUpper method by checking if the getUpper method returns the right Gem.
	 */
	@Test
	public void testGetUpper() {
		board = new Board(2,0,0,false);
		gems = board.getGems();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		Gem gem3 = gems[1][0];
		assertEquals(board.getUpper(gem3), gem1);
		assertFalse(board.getUpper(gem3) == gem2);
	}

	/**
	 * Tests the getRight method by checking if the getRight method returns the right Gem.
	 */
	@Test
	public void testGetRight() {
		board = new Board(2,0,0,false);
		gems = board.getGems();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		Gem gem3 = gems[1][1];
		assertEquals(board.getRight(gem1), gem2);
		assertFalse(board.getRight(gem1) == gem3);
	}

	/**
	 * Tests the getBelow method by checking if the getBelow method returns the right Gem.
	 */
	@Test
	public void testGetBelow() {
		board = new Board(2,0,0,false);
		gems = board.getGems();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		Gem gem3 = gems[1][0];
		assertEquals(board.getBelow(gem1), gem3);
		assertFalse(board.getBelow(gem1) == gem2);
	}

	/**
	 * Tests the getLeft method by checking if the getLeft method returns the right Gem.
	 */
	@Test
	public void testGetLeft() {
		board = new Board(2,0,0,false);
		gems = board.getGems();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[0][1];
		Gem gem3 = gems[1][0];
		assertEquals(board.getLeft(gem2), gem1);
		assertFalse(board.getLeft(gem2) == gem3);
	}

	/**
	 * Tests the areNeighbours method, by testing neighbors and also testing Gems
	 * that aren't neighbors.
	 */
	@Test
	public void testAreNeighbours() {
		board = new Board(2,0,0,false);
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
	 * Tests the deleteRows method by setting up some combinations and testing
	 * whether the method finds them.
	 */
	@Test
	public void testDeleteRows() {
		board = new Board(3,0,0,false);
		gems = board.getGems();
		gems[0][0].setType(1);
		gems[0][1].setType(1);
		gems[0][2].setType(1);
		assertTrue(board.deleteRows(gems[0][0]));
		gems[0][1].setType(2);
		gems[1][0].setType(2);
		assertFalse(board.deleteRows(gems[0][0]));
	}

	/**
	 * Tests the deleteAll method, by filling an ArrayList with gems and
	 * checking if those gems have been deleted.
	 */
	@Test
	public void testDeleteAll() {
		board = new Board(3,0,0,false);
		gems = board.getGems();
		ArrayList<Gem> array = new ArrayList<Gem>();
		Gem gem1 = gems[0][0];
		Gem gem2 = gems[1][0];
		Gem gem3 = gems[2][0];
		array.add(gem3);
		array.add(gem1);
		array.add(gem2);
		board.deleteAll(array);
		assertFalse(gem1 == gems[0][0]);
		assertFalse(gem2 == gems[1][0]);
		assertFalse(gem3 == gems[2][0]);
		assertFalse(gem2 == gems[2][0]);
	}

	/**
	 * Tests the deleteHorizontal method, by making a nice board with horizontal combinations of
	 * 5,4 and 3 Gems and checking if the method finds those combinations.
	 */
	@Test
	public void testDeleteHorizontal() {
		board = new Board(5,0,0,false);
		gems = board.getGems();
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 5 - row; col++) {
				gems[row][col].setType(1);
			}
		}
		gems[1][4].setType(2);
		gems[2][3].setType(2);
		gems[2][4].setType(2);
		ArrayList<Gem> array1 = new ArrayList<Gem>();
		array1.add(gems[0][1]);
		array1.add(gems[0][2]);
		array1.add(gems[0][3]);
		array1.add(gems[0][4]);
		ArrayList<Gem> array2 = board.deleteHorizontal(gems[0][0]);
		assertEquals(array1, array2);
		
		ArrayList<Gem> array3 = new ArrayList<Gem>();
		array3.add(gems[1][1]);
		array3.add(gems[1][0]);
		array3.add(gems[1][3]);
		ArrayList<Gem> array4 = board.deleteHorizontal(gems[1][2]);
		assertEquals(array3, array4);
		
		ArrayList<Gem> array5 = new ArrayList<Gem>();
		array5.add(gems[2][0]);
		array5.add(gems[2][2]);
		ArrayList<Gem> array6 = board.deleteHorizontal(gems[2][1]);
		assertEquals(array5, array6);
	}

	/**
	 * Tests the deleteVertical method, by making a nice board with vertical combinations of
	 * 5,4 and 3 Gems and checking if the method finds those combinations.
	 */
	@Test
	public void testDeleteVertical() {
		board = new Board(5,0,0,false);
		gems = board.getGems();
		for(int col = 0; col < 5; col++) {
			for(int row = 0; row < 5 - col; row++) {
				gems[row][col].setType(1);
			}
		}
		gems[4][1].setType(2);
		gems[3][2].setType(2);
		gems[4][2].setType(2);
		ArrayList<Gem> array1 = new ArrayList<Gem>();
		array1.add(gems[1][0]);
		array1.add(gems[2][0]);
		array1.add(gems[3][0]);
		array1.add(gems[4][0]);
		ArrayList<Gem> array2 = board.deleteVertical(gems[0][0]);
		assertEquals(array1, array2);
		
		ArrayList<Gem> array3 = new ArrayList<Gem>();
		array3.add(gems[1][1]);
		array3.add(gems[0][1]);
		array3.add(gems[3][1]);
		ArrayList<Gem> array4 = board.deleteVertical(gems[2][1]);
		assertEquals(array3, array4);
		
		ArrayList<Gem> array5 = new ArrayList<Gem>();
		array5.add(gems[0][2]);
		array5.add(gems[2][2]);
		ArrayList<Gem> array6 = board.deleteVertical(gems[1][2]);
		assertEquals(array5, array6);
	}

	/**
	 * Tests the setSelectedgem and getSelectedgem methods, by first setting a Gem
	 * on selected and then checking with getSelectedgem if it was selected.
	 */
	@Test
	public void selectedGemTest() {
		board = new Board(2,0,0,false);
		gems = board.getGems();
		Gem gem = gems[0][0];
		assertNull(board.getSelectedgem());
		board.setSelectedgem(gem);
		assertEquals(board.getSelectedgem(), gem);
	}
	
	/**
	 * Tests the updateScore and getScore methods. First, the score should be 0. Then the score
	 * is updated with 20 (2 Gems) and then the score should be 20.
	 */
	@Test
	public void scoreTest() {
		board = new Board(2,0,0,false);
		assertEquals(board.getScore(), 0);
		board.updateScore(2);
		assertEquals(board.getScore(), 20);
	}
	
	/**
	 * Tests the setSecondGem and getSecondGem methods, by first setting a Gem
	 * on second and then checking with getSecondGem if it was second.
	 */
	@Test
	public void secondGemTest() {
		board = new Board(2,0,0,false);
		gems = board.getGems();
		Gem gem = gems[0][0];
		assertNull(board.getSecondGem());
		board.setSecondGem(gem);
		assertEquals(board.getSecondGem(), gem);
	}
	
	/**
	 * Tests the setGems and getGems methods, by first setting Gems[][] testgems,
	 * and then checking whether it was set with the getGems method.
	 */
	@Test
	public void setGemTest() {
		board = new Board(2,0,0,false);
		Gem[][] testgems = new Gem[2][2];
		board.setGems(testgems);
		assertArrayEquals(testgems, board.getGems());
	}
}


