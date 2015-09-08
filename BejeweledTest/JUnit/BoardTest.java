import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
public class BoardTest {

	//just to see how Travis CI works
	@Test
	public void testTest(){
		assertTrue(true);
	}
//	Board board;
//	Gem[][] gems;
//
//	@Test
//	public void testFillBoardIntInt() {
//		board = new Board(3);
//		board.fillBoard(0,0);
//		gems = board.getGems();
//		for(int row = 0; row < 3; row++) {
//			for(int col = 0; col < 3; col++) {
//				assertTrue(1 <= gems[row][col].getType());
//				assertTrue(gems[row][col].getType() <= 6);
//			}
//		}
//	}
//
//	@Test
//	public void testRowCheck() {
//		board = new Board(3);
//		gems = board.getGems();
//		gems[0][0].setType(1);
//		gems[0][1].setType(1);
//		assertFalse(board.rowCheck(0, 2, 1));
//		gems[0][0].setType(2);
//		assertTrue(board.rowCheck(0, 2, 1));
//	}
//
//	@Test
//	public void testColCheck() {
//		board = new Board(3);
//		gems = board.getGems();
//		gems[0][0].setType(1);
//		gems[1][0].setType(1);
//		assertFalse(board.colCheck(2, 0, 1));
//		gems[0][0].setType(2);
//		assertTrue(board.colCheck(2, 0, 1));
//	}
//
//	@Test
//	public void testDelete() {
//		board = new Board(3);
//		gems = board.getGems();
//		gems[0][0].setType(1);
//		gems[1][0].setType(1);
//		Gem gem1 = gems[0][0];
//		Gem gem2 = gems[1][0];
//		board.delete(2, 0);
//		assertEquals(gems[2][0], gem2);
//		assertEquals(gems[1][0], gem1);
//	}
//
//	@Test
//	public void testSwap() {
//		board = new Board(2);
//		assertFalse(board.swap(0, 1, 1, 0));
//		gems = board.getGems();
//		Gem gem1 = gems[0][0];
//		Gem gem2 = gems[0][1];
//		assertTrue(board.swap(0, 0, 0, 1));
//		assertEquals(gems[0][0], gem2);
//		assertEquals(gems[0][1], gem1);
//		assertTrue(board.swap(0, 0, 1, 0));
//	}
//
//	@Test
//	public void testGetUpper() {
//		board = new Board(2);
//		gems = board.getGems();
//		Gem gem1 = gems[0][0];
//		Gem gem2 = gems[0][1];
//		Gem gem3 = gems[1][0];
//		assertEquals(board.getUpper(gem3), gem1);
//		assertFalse(board.getUpper(gem3) == gem2);
//	}
//
//	@Test
//	public void testGetRight() {
//		board = new Board(2);
//		gems = board.getGems();
//		Gem gem1 = gems[0][0];
//		Gem gem2 = gems[0][1];
//		Gem gem3 = gems[1][1];
//		assertEquals(board.getRight(gem1), gem2);
//		assertFalse(board.getRight(gem1) == gem3);
//	}
//
//	@Test
//	public void testGetBelow() {
//		board = new Board(2);
//		gems = board.getGems();
//		Gem gem1 = gems[0][0];
//		Gem gem2 = gems[0][1];
//		Gem gem3 = gems[1][0];
//		assertEquals(board.getBelow(gem1), gem3);
//		assertFalse(board.getBelow(gem1) == gem2);
//	}
//
//	@Test
//	public void testGetLeft() {
//		board = new Board(2);
//		gems = board.getGems();
//		Gem gem1 = gems[0][0];
//		Gem gem2 = gems[0][1];
//		Gem gem3 = gems[1][0];
//		assertEquals(board.getLeft(gem2), gem1);
//		assertFalse(board.getLeft(gem2) == gem3);
//	}
//
//	@Test
//	public void testAreNeighbours() {
//		board = new Board(2);
//		gems = board.getGems();
//		Gem gem1 = gems[0][0];
//		Gem gem2 = gems[0][1];
//		Gem gem3 = gems[1][0];
//		Gem gem4 = gems[1][1];
//		assertTrue(board.areNeighbours(gem1, gem2));
//		assertTrue(board.areNeighbours(gem1, gem3));
//		assertFalse(board.areNeighbours(gem1, gem4));
//	}
//
//	@Test
//	public void testDeleteRows() {
//		board = new Board(3);
//		gems = board.getGems();
//		gems[0][0].setType(1);
//		gems[0][1].setType(1);
//		gems[0][2].setType(1);
//		assertTrue(board.deleteRows(gems[0][0]));
//		gems[0][1].setType(2);
//		gems[1][0].setType(2);
//		assertFalse(board.deleteRows(gems[0][0]));
//	}
//
//	@Test
//	public void testDeleteAll() {
//		board = new Board(3);
//		gems = board.getGems();
//		ArrayList<Gem> array = new ArrayList<Gem>();
//		Gem gem1 = gems[0][0];
//		Gem gem2 = gems[1][0];
//		Gem gem3 = gems[2][0];
//		array.add(gem3);
//		array.add(gem1);
//		array.add(gem2);
//		board.deleteAll(array);
//		assertFalse(gem1 == gems[0][0]);
//		assertFalse(gem2 == gems[1][0]);
//		assertFalse(gem3 == gems[2][0]);
//		assertFalse(gem2 == gems[2][0]);
//	}
//
//	@Test
//	public void testDeleteHorizontal() {
//		board = new Board(5);
//		gems = board.getGems();
//		for(int row = 0; row < 5; row++) {
//			for(int col = 0; col < 5 - row; col++) {
//				gems[row][col].setType(1);
//			}
//		}
//		gems[1][4].setType(2);
//		gems[2][3].setType(2);
//		gems[2][4].setType(2);
//		ArrayList<Gem> array1 = new ArrayList<Gem>();
//		array1.add(gems[0][1]);
//		array1.add(gems[0][2]);
//		array1.add(gems[0][3]);
//		array1.add(gems[0][4]);
//		ArrayList<Gem> array2 = board.deleteHorizontal(gems[0][0]);
//		assertEquals(array1, array2);
//		
//		ArrayList<Gem> array3 = new ArrayList<Gem>();
//		array3.add(gems[1][1]);
//		array3.add(gems[1][0]);
//		array3.add(gems[1][3]);
//		ArrayList<Gem> array4 = board.deleteHorizontal(gems[1][2]);
//		assertEquals(array3, array4);
//		
//		ArrayList<Gem> array5 = new ArrayList<Gem>();
//		array5.add(gems[2][0]);
//		array5.add(gems[2][2]);
//		ArrayList<Gem> array6 = board.deleteHorizontal(gems[2][1]);
//		assertEquals(array5, array6);
//	}
//
//	@Test
//	public void testDeleteVertical() {
//		board = new Board(5);
//		gems = board.getGems();
//		for(int col = 0; col < 5; col++) {
//			for(int row = 0; row < 5 - col; row++) {
//				gems[row][col].setType(1);
//			}
//		}
//		gems[4][1].setType(2);
//		gems[3][2].setType(2);
//		gems[4][2].setType(2);
//		ArrayList<Gem> array1 = new ArrayList<Gem>();
//		array1.add(gems[1][0]);
//		array1.add(gems[2][0]);
//		array1.add(gems[3][0]);
//		array1.add(gems[4][0]);
//		ArrayList<Gem> array2 = board.deleteVertical(gems[0][0]);
//		assertEquals(array1, array2);
//		
//		ArrayList<Gem> array3 = new ArrayList<Gem>();
//		array3.add(gems[1][1]);
//		array3.add(gems[0][1]);
//		array3.add(gems[3][1]);
//		ArrayList<Gem> array4 = board.deleteVertical(gems[2][1]);
//		assertEquals(array3, array4);
//		
//		ArrayList<Gem> array5 = new ArrayList<Gem>();
//		array5.add(gems[0][2]);
//		array5.add(gems[2][2]);
//		ArrayList<Gem> array6 = board.deleteVertical(gems[1][2]);
//		assertEquals(array5, array6);
//	}

}


