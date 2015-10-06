package bejeweled.board;

import java.util.ArrayList;
import bejeweled.Sounds;
import bejeweled.state.Logger;
import bejeweled.Main;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Group 30 Holds the double dimension array with gems and the board
 *         methods. 
 */
public final class Board {
	private Gem[][] gems;
	private int dimension;
	private Gem selectedgem = null;
	private Gem hintedgem = null;
	private Gem secondGem = null;;
	private boolean loadImages;

	/**
	 * @param dimension
	 *            - The dimensions of the board.
	 * @param offsetx
	 *            - standard offset in x, used for drawing and clicking.
	 * @param offsety
	 *            - standard offset in y, used for drawing and clicking.
	 */
	public Board(int dimension, boolean loadImages) {
		this.dimension = dimension;
		this.loadImages = loadImages;
		gems = new Gem[dimension][dimension];
		fillBoard(0, 0);
	}

	/**
	 * @param col
	 *            - column index
	 * @param row
	 *            - row index Backtrack method
	 */
	public void fillBoard(int col, int row) {
		GemType type = GemType.getRandomGemType();
		if (rowCheck(row, col, type) && colCheck(row, col, type)) {
			Gem gem = new Gem(row, col, type, loadImages);
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
	public Gem[][] getGems() {
		return gems;
	}

	/**
	 * Checks whether the gem would form a combination in its row.
	 * 
	 * @param row
	 *            - row of the gem
	 * @param col
	 *            - column of the gem
	 * @param type
	 *            - type of the gem
	 * @return - boolean based on the gem forming a combination or not.
	 */
	public boolean rowCheck(int row, int col, GemType type) {
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
	 * 
	 * @param row
	 *            - row of the gem
	 * @param col
	 *            - column of the gem
	 * @param type
	 *            - type of the gem
	 * @return - boolean based on the gem forming a combination or not.
	 */
	public boolean colCheck(int row, int col, GemType type) {
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
	 *            draws the board
	 */
	public void draw(GraphicsContext gc) {
		for (Gem[] gemss : gems) {
			for (Gem gem : gemss) {
				gem.draw(gc);
			}
		}
	}

	/**
	 * Checks if a hint can be given.
	 * 
	 * @param col
	 * @param row
	 */

	public void hintCheck(int col, int row) {
		GemType type = gems[row][col].getType();
		if (getRight(gems[row][col]) != null && getRight(gems[row][col]).getType() == type) {
			checkSurround(col, row, type, 1);
		} else if (getBelow(gems[row][col]) != null && getBelow(gems[row][col]).getType() == type) {
			checkSurround(col, row, type, -1);
		} else if (col < dimension - 1) {
			hintCheck(col + 1, row);
		} else if (row < dimension - 1) {
			hintCheck(0, row + 1);
		}
	}

	/**
	 * Checks if there can be made combinations and illustrates the hint.
	 * 
	 * @param col
	 * @param row
	 * @param type
	 * @param site
	 */
	public void checkSurround(int col, int row, GemType type, int site) {
		if (site == 1) {
			if (getLeft(getLeft(gems[row][col])) != null && getLeft(getLeft(gems[row][col])).getType() == type) {
				gems[row][col - 2].setHinted(true);
				setSelectedgem(gems[row][col - 2]);
			} else
				if (getBelow(getLeft(gems[row][col])) != null && getBelow(getLeft(gems[row][col])).getType() == type) {
				gems[row + 1][col - 1].setHinted(true);
				setSelectedgem(gems[row + 1][col - 1]);
			} else if (getUpper(getLeft(gems[row][col])) != null
					&& getUpper(getLeft(gems[row][col])).getType() == type) {
				gems[row - 1][col - 1].setHinted(true);
				setSelectedgem(gems[row - 1][col - 1]);
			} else if (getRight(getRight(gems[row][col + 1])) != null
					&& getRight(getRight(gems[row][col + 1])).getType() == type) {
				gems[row][col + 3].setHinted(true);
				setSelectedgem(gems[row][col + 3]);
			} else if (getBelow(getRight(gems[row][col + 1])) != null
					&& getBelow(getRight(gems[row][col + 1])).getType() == type) {
				gems[row + 1][col + 2].setHinted(true);
				setSelectedgem(gems[row + 1][col + 2]);
			} else if (getUpper(getRight(gems[row][col + 1])) != null
					&& getUpper(getRight(gems[row][col + 1])).getType() == type) {
				gems[row - 1][col + 2].setHinted(true);
				setSelectedgem(gems[row - 1][col + 2]);
			} else if (col < dimension - 1) {
				hintCheck(col + 1, row);
			} else if (row < dimension - 1) {
				hintCheck(0, row + 1);
			}
		} else {
			if (getLeft(getUpper(gems[row][col])) != null && getLeft(getUpper(gems[row][col])).getType() == type) {
				gems[row - 1][col - 1].setHinted(true);
				setSelectedgem(gems[row - 1][col - 1]);
			} else if (getRight(getUpper(gems[row][col])) != null
					&& getRight(getUpper(gems[row][col])).getType() == type) {
				gems[row - 1][col + 1].setHinted(true);
				setSelectedgem(gems[row - 1][col + 1]);
			} else if (getUpper(getUpper(gems[row][col])) != null
					&& getUpper(getUpper(gems[row][col])).getType() == type) {
				gems[row - 2][col].setHinted(true);
				setSelectedgem(gems[row - 2][col]);
			} else if (getRight(getBelow(gems[row + 1][col])) != null
					&& getRight(getBelow(gems[row + 1][col])).getType() == type) {
				gems[row + 2][col + 1].setHinted(true);
				setSelectedgem(gems[row + 2][col + 1]);
			} else if (getBelow(getBelow(gems[row + 1][col])) != null
					&& getBelow(getBelow(gems[row + 1][col])).getType() == type) {
				gems[row + 3][col].setHinted(true);
				setSelectedgem(gems[row + 3][col]);
			} else if (getLeft(getBelow(gems[row + 1][col])) != null
					&& getLeft(getBelow(gems[row + 1][col])).getType() == type) {
				gems[row + 2][col - 1].setHinted(true);
				setSelectedgem(gems[row + 2][col - 1]);
			} else if (col < dimension - 1) {
				hintCheck(col + 1, row);
			} else if (row < dimension - 1) {
				hintCheck(0, row + 1);
			}
		}
	}

	/**
	 * @param row
	 *            - row number integer
	 * @param col
	 *            - column number integer Deletes a gem based on column and row,
	 *            moves all the gems above this gem a place down
	 */
	public void delete(int row, int col) {
		// move all blocks above the deleted block down
		for (int r = row; r >= 0; r--) {
			if (r >= 1) {
				gems[r - 1][col].setPosition(r, col);
				gems[r][col] = gems[r - 1][col];
			} else {
				GemType type = GemType.getRandomGemType();
				Gem gem = new Gem(0, col, type, loadImages);
				gems[0][col] = gem;
			}
		}
	}

	/**
	 * @param row1
	 *            - row number of the first gem to be swapped.
	 * @param col1
	 *            - column number of the first gem to be swapped.
	 * @param row2
	 *            - row number of the second gem to be swapped.
	 * @param col2
	 *            - column number of the second gem to be swapped.
	 * @return - boolean based on the possibility of the swap. Swaps two gems
	 *         and returns a boolean whether the swap is possible or not.
	 */
	public boolean swap(int row1, int col1, int row2, int col2) {
		if (!areNeighbours(gems[row1][col1], gems[row2][col2])) {
			Sounds.getInstance().playErrorSound();
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
	 * @param g
	 *            - The gem of which the upper neighbour is gotten.
	 * @return - The upper neighbour of the gem.
	 */
	public Gem getUpper(Gem g) {
		if (g == null) {
			return null;
		} else if (g.getRow() > 0) {
			return (gems[g.getRow() - 1][g.getCol()]);
		}
		return null;
	}

	/**
	 * @param g
	 *            - The gem of which the right hand neighbour is gotten.
	 * @return - The right hand neighbour of the gem.
	 */
	public Gem getRight(Gem g) {
		if (g == null) {
			return null;
		} else if (g.getCol() < dimension - 1) {
			return (gems[g.getRow()][g.getCol() + 1]);
		}
		return null;
	}

	/**
	 * @param g
	 *            - The gem of which the neighbour below it is gotten.
	 * @return - The gem below the gem passed in.
	 */
	public Gem getBelow(Gem g) {
		if (g == null) {
			return null;
		} else if (g.getRow() < dimension - 1) {
			return (gems[g.getRow() + 1][g.getCol()]);
		}
		return null;
	}

	/**
	 * @param g
	 *            - The gem of which the left hand neighbour is gotten.
	 * @return - The left hand neighbour of the gem.
	 */
	public Gem getLeft(Gem g) {
		if (g == null) {
			return null;
		} else if (g.getCol() > 0) {
			return (gems[g.getRow()][g.getCol() - 1]);
		}
		return null;
	}

	/**
	 * @param gem1
	 *            - One of the gems involved in the check
	 * @param gem2
	 *            - The second of the gems involved in the check
	 * @return - true if two gems are neighbours
	 */
	public boolean areNeighbours(Gem gem1, Gem gem2) {
		return (getUpper(gem1) == gem2 || getBelow(gem1) == gem2 || getLeft(gem1) == gem2 || getRight(gem1) == gem2);
	}

	/**
	 * Will delete all combinations of Gems, by first finding them with
	 * deleteHorizontal and deleteVertical and then deleting them with
	 * deleteAll.
	 * 
	 * @param g
	 *            - Gem as starting point
	 * @return true, iff there are rows to be deleted
	 */
	public int deleteRows(Gem g) {
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
	 * @param array
	 *            - Array of gems involved in the deletion.
	 */
	public void deleteAll(ArrayList<Gem> array) {
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
	 * @param g
	 *            - Gem to be checked for a combination.
	 * @return array - array of a combination. Empty if there is none.
	 */
	public ArrayList<Gem> deleteHorizontal(Gem g) {
		GemType type = g.getType();
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
			Logger.getInstance().writeLineToLogger("A horizontal combination of " + array.size() + " gems of type "
					+ type + " was formed and deleted.");
			return array;
		} else {
			return new ArrayList<Gem>();
		}
	}

	/**
	 * Finds all vertical combinations of Gems with Gem g, places the gems in
	 * these combinations in the ArrayList array.
	 * 
	 * @param g
	 *            - Gem to be checked for a combination.
	 * @return array - array of a combination. Empty if there is none.
	 */
	public ArrayList<Gem> deleteVertical(Gem g) {
		GemType type = g.getType();
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
			Logger.getInstance().writeLineToLogger(
					"A vertical combination of " + array.size() + " gems of type " + type + " was formed and deleted.");
			return array;
		} else {
			return new ArrayList<Gem>();
		}
	}

	/**
	 * Gives back the gem currently selected by the player.
	 * 
	 * @return - The selected gem.
	 */
	public Gem getSelectedgem() {
		return selectedgem;
	}

	public Gem getHintedgem() {
		return hintedgem;
	}

	/**
	 * Set the currently selected gem by the player.
	 */
	public void setSelectedgem(Gem selectedgem) {
		this.selectedgem = selectedgem;
	}

	public void setHintedgem(Gem hintedgem) {
		this.hintedgem = hintedgem;
	}

	/**
	 * Gives back the gem currently selected by the player.
	 * 
	 * @return - The second gem selected.
	 */
	public Gem getSecondGem() {
		return secondGem;
	}

	/**
	 * Set the second currently selected gem by the player.
	 */
	public void setSecondGem(Gem secondGem) {
		this.secondGem = secondGem;
	}

	/**
	 * Set the two-dimensional gem array.
	 * 
	 * @param gems
	 *            - Two-dimensional array of gems to set the old one as.
	 */
	public void setGems(Gem[][] g) {
		gems = g;
	}

	/**
	 * @return - true iff Object other is equal to this.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Board) {
			Board that = (Board) other;
			if (this.dimension == that.dimension) {
				return true;
			}
		}
		return false;
	}

}
