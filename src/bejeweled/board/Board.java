package bejeweled.board;
import java.util.ArrayList;
import java.util.Random;
import bejeweled.Sounds;
import bejeweled.Main;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Group 30
 * Holds the double dimension array with gems and the board methods.
 */
public class Board {
	private Gem[][] gems;
	private int dimension;
	private Random random = new Random();
	private Gem selectedgem = null;
	private Gem secondGem = null;
	private int offsetx;
	private int offsety;
	private boolean loadImages;
	private static int score;
	private final int scorePerGem = 10;
	private Sounds sound;

	/**
	 * @param dimension - The dimensions of the board.
	 * @param offsetx - standard offset in x, used for drawing and clicking. 
	 * @param offsety - standard offset in y, used for drawing and clicking. 
	 */
	public Board(int dimension, int offsetx, int offsety, boolean loadImages) {
		score = 0;
		this.dimension = dimension;
		this.offsetx = offsetx;
		this.offsety = offsety;
		this.loadImages = loadImages;
		gems = new Gem[dimension][dimension];
		fillBoard(0, 0);
	}

	/**
	 * @param col - column index
	 * @param row - row index
	 *            Backtrack method
	 */
	public final void fillBoard(int col, int row) {
		int type = random.nextInt(6) + 1;
		if (rowCheck(row, col, type) && colCheck(row, col, type)) {
			Gem gem = new Gem(row, col, offsetx, offsety, type, loadImages);
			gems[row][col] = gem;
			if (col < dimension - 1) {
				fillBoard(col + 1, row);
			} else if (row < dimension - 1) {
				fillBoard(0, row + 1);
			}
		} else {
			fillBoard(col, row);
		}
	}

	/**
	 * getGems method.
	 * 
	 * @return Gem[][] gems
	 */
	public final Gem[][] getGems() {
		return gems;
	}

	/**
	 * Checks whether the gem would form a combination in its row.
	 * @param row - row of the gem
	 * @param col - column of the gem
	 * @param type - type of the gem
	 * @return - boolean based on the gem forming a combination or not.
	 */
	public final boolean rowCheck(int row, int col, int type) {
		if (col <= 1) {
			return true;
		} else if (gems[row][col - 1].getType() == type && gems[row][col - 2].getType() == type) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks whether the gem would form a combination in its column.
	 * @param row - row of the gem
	 * @param col - column of the gem
	 * @param type - type of the gem
	 * @return - boolean based on the gem forming a combination or not.
	 */
	public final boolean colCheck(int row, int col, int type) {
		if (row <= 1) {
			return true;
		} else if (gems[row - 1][col].getType() == type && gems[row - 2][col].getType() == type) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param gc
	 * 			  draws the board
	 */
	public final void draw(GraphicsContext gc) {
		for (Gem[] gemss : gems) {
			for (Gem gem : gemss) {
				gem.draw(gc);
			}
		}
	}

	/**
	 * @param row - row number integer
	 * @param col - column number integer
	 *            Deletes a gem based on column and row, moves all the gems
	 *            above this gem a place down
	 */
	public final void delete(int row, int col) {
		gems[row][col].delete();

		// move all blocks above the deleted block down
		for (int r = row; r >= 0; r--) {
			if (r >= 1) {
				gems[r - 1][col].setPosition(r, col);
				gems[r][col] = gems[r - 1][col];
			} else {
				int type = random.nextInt(6) + 1; // take a random number out of
													// 1,2,3,4,5,6
				Gem gem = new Gem(0, col, offsetx, offsety, type, loadImages);
				gems[0][col] = gem;
			}
		}
	}

	/**
	 * @param row1 - row number of the first gem to be swapped.
	 * @param col1 - column number of the first gem to be swapped.
	 * @param row2 - row number of the second gem to be swapped.
	 * @param col2 - column number of the second gem to be swapped.
	 * @return - boolean based on the possibility of the swap.
	 *            Swaps two gems and returns a boolean whether
	 *            the swap is possible or not.
	 */
	public final boolean swap(int row1, int col1, int row2, int col2) {
		if (!areNeighbours(gems[row1][col1], gems[row2][col2])) {
	   		Sounds.playErrorSound();
			return false;
		}
		Gem tempgem = gems[row1][col1];
		gems[row2][col2].setPosition(row1, col1);
		gems[row1][col1] = gems[row2][col2];
		tempgem.setPosition(row2, col2);
		gems[row2][col2] = tempgem;
		return true;
	}

	/**
	 * @param g - The gem of which the upper neighbour is gotten.
	 * @return - The upper neighbour of the gem.
	 */
	public final Gem getUpper(Gem g) {
		if (g.getRow() > 0) {
			return (gems[g.getRow() - 1][g.getCol()]);
		}
		return null;
	}

	/**
	 * @param g - The gem of which the right hand neighbour is gotten.
	 * @return - The right hand neighbour of the gem.
	 */
	public final Gem getRight(Gem g) {
		if (g.getCol() < dimension - 1) {
			return (gems[g.getRow()][g.getCol() + 1]);
		}
		return null;
	}

	/**
	 * @param g - The gem of which the neighbour below it is gotten.
	 * @return - The gem below the gem passed in.
	 */
	public final Gem getBelow(Gem g) {
		if (g.getRow() < dimension - 1) {
			return (gems[g.getRow() + 1][g.getCol()]);
		}
		return null;
	}

	/**
	 * @param g - The gem of which the left hand neighbour is gotten.
	 * @return - The left hand neighbour of the gem.
	 */
	public final Gem getLeft(Gem g) {
		if (g.getCol() > 0) {
			return (gems[g.getRow()][g.getCol() - 1]);
		}
		return null;
	}

	/**
	 * @param gem1 - One of the gems involved in the check
	 * @param gem2 - The second of the gems involved in the check
	 * @return - true if two gems are neighbours
	 */
	public final boolean areNeighbours(Gem gem1, Gem gem2) {
		return (getUpper(gem1) == gem2 || getBelow(gem1) == gem2 || getLeft(gem1) == gem2 || getRight(gem1) == gem2);
	}

	/**
	 * Will delete all combinations of Gems, by first finding them with
	 * deleteHorizontal and deleteVertical and then deleting them with
	 * deleteAll.
	 * 
	 * @param g - Gem as starting point
	 * @return true, iff there are rows to be deleted
	 */
	public final int deleteRows(Gem g) {
		ArrayList<Gem> array1 = deleteHorizontal(g);
		ArrayList<Gem> array2 = deleteVertical(g);
		if (!array1.isEmpty()) {
			if (!array2.isEmpty()) {
				array1.addAll(array2);
			}
		} else if (array2.isEmpty()) {
			return 0;
		} else {
			array1 = array2;
		}
		array1.add(g);
		deleteAll(array1);
		return array1.size();
	}

	/**
	 * First sorts the arrayList array, then deletes all the Gems from the list.
	 * 
	 * @param array - Array of gems involved in the deletion.
	 */
	public final void deleteAll(ArrayList<Gem> array) {
		ArrayList<Gem> array2 = new ArrayList<Gem>();
		for (int i = 0; i < dimension; i++) { // sorts the array on the row of
												// the gem, up to down
			for (int j = 0; j < array.size(); j++) {
				Gem temp = array.get(j);
				if (temp.getRow() == i) {
					array2.add(array.get(j));
				}
			}
		}
		updateScore(array2.size());
		for (int k = 0; k < array2.size(); k++) {
			delete(array2.get(k).getRow(), array2.get(k).getCol()); // deletes
																	// all gems
																	// from the
																	// board,
																	// from up
																	// to down
		}
		for (int j = 0; j < dimension; j++) { // makes sure that no new
												// combinations form on the
												// board with new gems
			for (int k = 0; k < dimension; k++) {
				Gem temp = gems[j][k];
				deleteRows(temp);
			}
		}
	}

	/**
	 * Finds all horizontal combinations of Gems with Gem g, places the gems in
	 * these combinations in the ArrayList array.
	 * 
	 * @param g - Gem to be checked for a combination.
	 * @return array - array of a combination. Empty if there is none.
	 */
	public final ArrayList<Gem> deleteHorizontal(Gem g) {
		int type = g.getType();
		Gem leftgem = getLeft(g);
		Gem rightgem = getRight(g);
		ArrayList<Gem> array = new ArrayList<Gem>();

		while (leftgem != null && leftgem.getType() == type) {
			array.add(leftgem);
			leftgem = getLeft(leftgem);
		}

		while (rightgem != null && rightgem.getType() == type) {
			array.add(rightgem);
			rightgem = getRight(rightgem);
		}

		if (array.size() >= 2) {
			Main.getLogger().writeLineToLogger("A horizontal combination of " + array.size() + " gems of type " + type + " was formed and deleted.");
			return array;
		} else {
			return new ArrayList<Gem>();
		}
	}

	/**
	 * Finds all vertical combinations of Gems with Gem g, places the gems in
	 * these combinations in the ArrayList array.
	 * 
	 * @param g - Gem to be checked for a combination.
	 * @return array - array of a combination. Empty if there is none.
	 */
	public final ArrayList<Gem> deleteVertical(Gem g) {
		int type = g.getType();
		Gem upgem = getUpper(g);
		Gem downgem = getBelow(g);
		ArrayList<Gem> array = new ArrayList<Gem>();

		while (upgem != null && upgem.getType() == type) {
			array.add(upgem);
			upgem = getUpper(upgem);
		}

		while (downgem != null && downgem.getType() == type) {
			array.add(downgem);
			downgem = getBelow(downgem);
		}

		if (array.size() >= 2) {
			Main.getLogger().writeLineToLogger("A vertical combination of " + array.size() + " gems of type " + type + " was formed and deleted.");
			return array;
		} else {
			return new ArrayList<Gem>();
		}
	}
	
	/**
	 * @param amountOfGems - number of gems deleted due to a formed combination.
	 * Updates score based on an amount of gems
	 */
	public final void updateScore(int amountOfGems) {
		int increase = scorePerGem * amountOfGems;
		score += increase;
		Main.getLogger().writeLineToLogger("The player scores "+increase+" points. The total score is now: "+score);
	}
	
	/**
	 * Gives back the gem currently selected by the player.
	 * @return - The selected gem.
	 */
	public final Gem getSelectedgem() {
		return selectedgem;
	}
	
	/**
	 * Set the currently selected gem by the player.
	 */
	public final void setSelectedgem(Gem selectedgem) {
		this.selectedgem = selectedgem;
	}
	
	/**
	 * Gives back the gem currently selected by the player.
	 * @return - The second gem selected.
	 */
	public final Gem getSecondGem() {
		return secondGem;
	}
	
	/**
	 * Set the second currently selected gem by the player.
	 */
	public final void setSecondGem(Gem secondGem) {
		this.secondGem = secondGem;
	}
	
	/**
	 * Set the two-dimensional gem array.
	 * @param gems - Two-dimensional array of gems to set the old one as.
	 */
	public final void setGems(Gem[][] gems) {
		this.gems = gems;
	}

	/**
	 * @return - The current score
	 */
	public static int getScore() {
		return score;
	}
	
	/**
	 * @return - true iff Object other is equal to this.
	 */
	@Override
	public final boolean equals(Object other) {
		if (other instanceof Board) {
			Board that = (Board) other;
			if (this.dimension == that.dimension
					&& this.offsetx == that.offsetx 
					&& this.offsety == that.offsety) {
				return true;
			}
		}
		return false;
	}
	
	

}
