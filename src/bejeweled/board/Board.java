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

	/**
	 * @param dimension
	 *            - The dimensions of the board.
	 * @param offsetx
	 *            - standard offset in x, used for drawing and clicking.
	 * @param offsety
	 *            - standard offset in y, used for drawing and clicking.
	 */
	public Board(int dimension) {
		this.dimension = dimension;
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
			Gem gem = new Gem(row, col, type);
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
	public void delete(Gem g) {
		int row = g.getRow();
		int col = g.getCol();
		// move all blocks above the deleted block down
		for (int r = row; r >= 0; r--) {
			if (r >= 1) {
				gems[r-1][col].setCurrentPositionsAsAnimationPositions();
				gems[r-1][col].setMoving(true);
				gems[r - 1][col].setPosition(r, col);
				gems[r][col] = gems[r - 1][col];
			} else {
				GemType type = GemType.getRandomGemType();
				Gem gem = new Gem(0, col, type);
				gem.setCurrentPositionsAsAnimationPositions();
				gem.setMoving(true);
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
	public boolean swap(Gem gem1, Gem gem2) {
		int row1 = gem1.getRow();
		int col1 = gem1.getCol();
		int row2 = gem2.getRow();
		int col2 = gem2.getCol();
		
		if (!areNeighbours(gems[row1][col1], gems[row2][col2])) {
			Sounds.getInstance().playErrorSound();
			return false;
		}
		Gem tempgem = gems[row1][col1];
		gems[row2][col2].setMoving(true);
		gems[row2][col2].setCurrentPositionsAsAnimationPositions();
		gems[row2][col2].setPosition(row1, col1);
		gems[row1][col1].setMoving(true);
		gems[row1][col1].setCurrentPositionsAsAnimationPositions();
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

	public ArrayList<Gem> checkForCombinations() {
		ArrayList<Gem> combinations = new ArrayList<Gem>();
		//check for each gem if its part of combinations;
		for (Gem[] gemss : gems) {
			for (Gem gem : gemss) {
				if(checkForCombinations(gem)){
					combinations.add(gem);
				}
			}
		}
		return combinations;
	}

	//TODO: Fix this worst method ever
	private boolean checkForCombinations(Gem gem) {
		GemType type = gem.getType();
		if (getUpper(gem) != null && getUpper(gem).getType() == type) {
			if (getUpper(getUpper(gem)) != null && getUpper(getUpper(gem)).getType() == type) {
				return true;
			}
			if (getBelow(gem) != null && getBelow(gem).getType() == type) {
				return true;
			}
		}
		if (getBelow(gem) != null && getBelow(gem).getType() == type) {
			if (getBelow(getBelow(gem)) != null && getBelow(getBelow(gem)).getType() == type) {
				return true;
			}
			if (getUpper(gem) != null && getUpper(gem).getType() == type) {
				return true;
			}
		}
		if (getRight(gem) != null && getRight(gem).getType() == type) {
			if (getRight(getRight(gem)) != null && getRight(getRight(gem)).getType() == type) {
				return true;
			}
			if (getLeft(gem) != null && getLeft(gem).getType() == type) {
				return true;
			}
		}
		if (getLeft(gem) != null && getLeft(gem).getType() == type) {
			if (getLeft(getLeft(gem)) != null && getLeft(getLeft(gem)).getType() == type) {
				return true;
			}
			if (getRight(gem) != null && getRight(gem).getType() == type) {
				return true;
			}
		}
		return false;
	}

	public void delete(ArrayList<Gem> combinations) {
		for(Gem g : combinations){
			delete(g);
		}
	}

}
