package bejeweled.board;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import bejeweled.Sounds;
import bejeweled.state.Logger;
import bejeweled.Main;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Group 30 Holds the double dimension array with gems and the board
 *         methods. 
 */
public final class Board implements Observer{
	private Gem[][] gems;
	private int dimension;
	private Gem selectedgem = null;
	private Gem hintedgem = null;
	private Gem secondGem = null;
	private GameFactory gf;

	/**
	 * @param dimension
	 *            - The dimensions of the board.
	 * @param offsetx
	 *            - standard offset in x, used for drawing and clicking.
	 * @param offsety
	 *            - standard offset in y, used for drawing and clicking.
	 */
	public Board(int dimension, GameFactory gf) {
		this.gf = gf;
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
		Gem gem = gf.createGem(row, col, null, SpecialType.NORMAL);
		if (rowCheck(row, col, gem.getType()) && colCheck(row, col, gem.getType())) {
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

	public void hintCheck(int row, int col, boolean hint) {
		GemType type = gems[row][col].getType();
		if (doubleRow(row, col, type)) {
			checkSurroundRow(row, col, type, hint);
		} else if (doubleCol(row, col, type)) {
			checkSurroundCol(row, col, type, hint);
		} else if (col < dimension - 1) {
			hintCheck(row, col + 1, hint);
		} else if (row < dimension - 1) {
			hintCheck(row + 1, 0, hint);
		} else {
			Main.gameOver();
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
	public void checkSurroundRow(int row, int col, GemType type, boolean hint) {
		if (doubleLeft(row, col, type)) {
			gems[row][col - 2].setHinted(hint);
		} else if (downLeft(row, col, type)) {
			gems[row + 1][col - 1].setHinted(hint);
		} else if (upperLeft(row, col, type)) {
			gems[row - 1][col - 1].setHinted(hint);
		} else if (doubleRight(row, col + 1, type)) {
			gems[row][col + 3].setHinted(hint);
		} else if (downRight(row, col + 1, type)) {
			gems[row + 1][col + 2].setHinted(hint);
		} else if (upperRight(row, col + 1, type)) {
			gems[row - 1][col + 2].setHinted(hint);
		} else if (col < dimension - 1) {
			hintCheck(row, col + 1, hint);
		} else if (row < dimension - 1) {
			hintCheck(row + 1, 0, hint);
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
	public void checkSurroundCol(int row, int col, GemType type, boolean hint) {
		if (upperLeft(row, col, type)) {
			gems[row - 1][col - 1].setHinted(hint);
		} else if (upperRight(row, col, type)) {
			gems[row - 1][col + 1].setHinted(hint);
		} else if (doubleUpper(row, col, type)) {
			gems[row - 2][col].setHinted(hint);
		} else if (downRight(row + 1, col, type)) {
			gems[row + 2][col + 1].setHinted(hint);
		} else if (doubleDown(row + 1, col, type)) {
			gems[row + 3][col].setHinted(hint);
		} else if (downLeft(row + 1, col, type)) {
			gems[row + 2][col - 1].setHinted(hint);
		} else if (col < dimension - 1) {
			hintCheck(row, col + 1, hint);
		} else if (row < dimension - 1) {
			hintCheck(row + 1, 0, hint);
		} 
	}

	/**
	 * @return the gems that are part of a combination
	 * checks for every gem if it's part of a combination
	 * TODO: this together with the checkforcombinations(gem) can be way more efficient
	 */
	public ArrayList<Combination> checkForCombinations() {
		ArrayList<Gem> combinations = new ArrayList<Gem>();
		//check for each gem if its part of combinations;
		for (Gem[] gemss : gems) {
			for (Gem gem : gemss) {
				if(checkForCombinations(gem)){
					combinations.add(gem);
				}
			}
		}
		return toCombinations(combinations);
	}

	/**
	 * @param gem the gem to be checked
	 * @return true if this gem is part of a combination
	 */
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
	
	public ArrayList<Combination> toCombinations(ArrayList<Gem> g) {
		ArrayList<Combination> res = new ArrayList<Combination>();
		ArrayList<Gem> blue = new ArrayList<Gem>();
		ArrayList<Gem> red = new ArrayList<Gem>();
		ArrayList<Gem> orange = new ArrayList<Gem>();
		ArrayList<Gem> yellow = new ArrayList<Gem>();
		ArrayList<Gem> pink = new ArrayList<Gem>();
		ArrayList<Gem> green = new ArrayList<Gem>();
		ArrayList<Gem> purple = new ArrayList<Gem>();

		
		for(int i = 0; i < g.size(); i++) {
			if(g.get(i).type == GemType.BLUE) 
				blue.add(g.get(i));
			if(g.get(i).type == GemType.RED) 
				red.add(g.get(i));
			if(g.get(i).type == GemType.ORANGE) 
				orange.add(g.get(i));
			if(g.get(i).type == GemType.YELLOW) 
				yellow.add(g.get(i));
			if(g.get(i).type == GemType.PINK) 
				pink.add(g.get(i));
			if(g.get(i).type == GemType.GREEN) 
				green.add(g.get(i));
			if(g.get(i).type == GemType.PURPLE) 
				purple.add(g.get(i));
		}
		
		if(blue.size() > 2) {
			Combination temp = new Combination(blue);
			res.add(temp);
		}
		if(red.size() > 2) {
			Combination temp = new Combination(red);
			res.add(temp);
		}
		if(orange.size() > 2) {
			Combination temp = new Combination(orange);
			res.add(temp);
		}
		if(yellow.size() > 2) {
			Combination temp = new Combination(yellow);
			res.add(temp);
		}
		if(pink.size() > 2) {
			Combination temp = new Combination(pink);
			res.add(temp);
		}
		if(green.size() > 2) {
			Combination temp = new Combination(green);
			res.add(temp);
		}
		if(purple.size() > 2) {
			Combination temp = new Combination(purple);
			res.add(temp);
		}
		return res;
	}

	
	/**
	 * @param combinations the gems to be deleted
	 * calls delete for all gems in an arraylist
	 */
	public void delete(ArrayList<Combination> combinations) {
		for(int i = 0; i < combinations.size(); i++) {
			Combination c = combinations.get(i);
			int size = combinations.get(i).getSize();
			if(size > 3) {
				for(Gem g : c.gems){
					if(g != selectedgem && g != secondGem) {
						delete(g, null);
					}
					else {
						if(size > 4 && size < 8) {
							delete(g, SpecialType.CROSS);
						}
						else if(size == 4) {
							delete(g, SpecialType.DOUBLE);
						}
						else {
							delete(g, null);
						}
					}
				}
			}
			else{
				for(Gem g : c.gems){
					delete(g, null);
				}
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
	public void delete(Gem g, SpecialType specialtype) {
		int row = g.getRow();
		int col = g.getCol();
		if (specialtype != null) {
			Gem gem = gf.createGem(g.row, g.col, g.type, specialtype);
			gems[g.row][g.col] = gem;
		} 
		// move all blocks above the deleted block down
		else {
			for (int r = row; r >= 0; r--) {
				if (r >= 1) {
					if (!gems[r - 1][col].isMoving()) {
						gems[r - 1][col].setCurrentPositionsAsAnimationPositions();
					}
					gems[r - 1][col].setMoving(true);
					gems[r - 1][col].setPosition(r, col);
					gems[r][col] = gems[r - 1][col];
				} else {
					Gem gem = gf.createGem(0, col, null, SpecialType.NORMAL);
					if (!gem.isMoving()) {
						gem.setAnimationx(gem.getCurrentx());
						gem.setAnimationy(gem.getCurrenty() - Gem.getDimension());
						for(int i = dimension-1; i>0; i--)
							if(gems[i][col].getAnimationy()<=(gem.getCurrenty() - Gem.getDimension()))
								gem.setAnimationy(gem.getAnimationy() - Gem.getDimension());
					}
					gem.setMoving(true);
					gems[0][col] = gem;
				}
			}
		}
	}

	/**
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
	
	public Combination deleteRowAndCol(Gem g, ArrayList<Combination> c) {
		ArrayList<Gem> check = new ArrayList<Gem>();
		for(int i = 0; i < c.size(); i++) {
			for(Gem gem : c.get(i).getGems()) {
				check.add(gem);
			}
		}
		ArrayList<Gem> combinations = new ArrayList<Gem>();
		for(int col = 0; col < 8; col++) {
			if(!check.contains(gems[g.row][col]))
				combinations.add(gems[g.row][col]);
		}
		for(int row = 0; row < 8; row++) {
			if(!check.contains(gems[row][g.col]))
				combinations.add(gems[row][g.col]);
		}
		return new Combination(combinations);
	}
	
	public Boolean handleMouseClickedBoard(final int row, final int col) {
		
		if (getHintedgem() != null) {
			getHintedgem().setHinted(false);
		}
		Logger.getInstance().writeLineToLogger("Mouse clicked on row " + row + " and col " + col);
		Sounds.getInstance().playSelectSound();
		//select if there is already a first selectedgem
		if (getSelectedgem() == null) {
			setSelectedgem(getGems()[row][col]);
			getGems()[row][col].setSelected(true);
			return false;
		//apparently this is the second gem we select
		//we should swap these two gems and handle animations and combinations
		} else {
			setSecondGem(getGems()[row][col]);
			if (swap(getSelectedgem(), getSecondGem())) {
				Logger.getInstance()
				.writeLineToLogger("The Gems on (" + getSelectedgem().getCol() + ","
						+ getSelectedgem().getRow() + ") and (" + getSecondGem().getCol() + ","
						+ getSecondGem().getRow() + ") are swapped.");
				//swap animation
				return true;
			}
			else{
				getSelectedgem().setSelected(false);
				setSelectedgem(null);
				return false;
			}
		}
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
	
	/**
	 * Checks if the gem at row, col has a gem of the same type
	 * 2 columns to the left of it.
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean doubleLeft(int row, int col, GemType type) {
		if (getLeft(getLeft(gems[row][col])) != null 
				&& getLeft(getLeft(gems[row][col])).getType() == type) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the gem at row, col has a gem of the same type
	 * 1 column to the left and 1 up from it.
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean upperLeft(int row, int col, GemType type) {
		if (getUpper(getLeft(gems[row][col])) != null 
				&& getUpper(getLeft(gems[row][col])).getType() == type) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the gem at row, col has a gem of the same type
	 * 1 column to the left and 1 down from it.
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean downLeft(int row, int col, GemType type) {
		if (getBelow(getLeft(gems[row][col])) != null 
				&& getBelow(getLeft(gems[row][col])).getType() == type) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the gem at row, col has a gem of the same type
	 * 2 columns to the right of it.
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean doubleRight(int row, int col, GemType type) {
		if (getRight(getRight(gems[row][col])) != null 
				&& getRight(getRight(gems[row][col])).getType() == type) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the gem at row, col has a gem of the same type
	 * 1 column to the right and 1 down from it.
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean upperRight(int row, int col, GemType type) {
		if (getUpper(getRight(gems[row][col])) != null 
				&& getUpper(getRight(gems[row][col])).getType() == type) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the gem at row, col has a gem of the same type
	 * 1 column to the right and 1 down from it.
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean downRight(int row, int col, GemType type) {
		if (getBelow(getRight(gems[row][col])) != null 
				&& getBelow(getRight(gems[row][col])).getType() == type) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the gem at row, col has a gem of the same type
	 * 2 rows on top of it.
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean doubleUpper(int row, int col, GemType type) {
		if (getUpper(getUpper(gems[row][col])) != null 
				&& getUpper(getUpper(gems[row][col])).getType() == type) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the gem at row, col has a gem of the same type
	 * 2 rows below it.
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean doubleDown(int row, int col, GemType type) {
		if (getBelow(getBelow(gems[row][col])) != null 
				&& getBelow(getBelow(gems[row][col])).getType() == type) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the gem at that row and column has a gem of the same type
	 * to the right of it.
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean doubleRow(int row, int col, GemType type) {
		if(getRight(gems[row][col]) != null && getRight(gems[row][col]).getType() == type) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the gem at that row and column has a gem of the same type
	 * below it.
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean doubleCol(int row, int col, GemType type) {
		if (getBelow(gems[row][col]) != null && getBelow(gems[row][col]).getType() == type) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks the board for possible moves everytime the score changes
	 * (and therefore the board changes)
	 */
	@Override
	public void update(Observable o, Object arg) {
		hintCheck(0, 0, false);
	}
}
