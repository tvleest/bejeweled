import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Timo
 * Holds the double dimension array with gems and the board methods
 */
public class Board {
	Gem[][] gems;
	int dimension;
	Random random = new Random();
	Gem selectedgem = null;
	Gem secondGem = null;
	
	public Board(int dimension){
		this.dimension = dimension;
		gems = new Gem[dimension][dimension];
		fillBoard();
	}
	
	/**
	 * Fills the board with random gems
	 */
	public void fillBoard(){
		/*
		int[] types = new int[7];
		for(int i=0; i<6; i++){
			types[i]=i+1;
		}
		fillBoard(0,0,types);
		*/
		int row,col;
        for (col=0;col<dimension;col++){
            for (row=0;row<8;row++){
            	int type = random.nextInt(6)+1; //take a random number out of 1,2,3,4,5,6
    			Gem gem = new Gem(row, col, type);
                gems[row][col] = gem;
            }
        }
        
        for(int j = 0; j < 8; j++) {
			for (int k = 0; k < 8; k++) {
				Gem temp = gems[j][k];
				deleteRows(temp);
			}
		}
	}
	
	/**
	 * @param col
	 * @param row
	 * @param types
	 * Backtrack method
	 */
	public void fillBoard(int col, int row, int[] types){
		//shuffle array
		
		for(int i =0; i<6; i++){
			if(rowCheck(row,col,i) && colCheck(row,col,i)){
				Gem gem = new Gem(row, col, i);
				gems[row][col]=gem;
				if(col<7){
					fillBoard(col+1, row, types);
				} else{
					fillBoard(0,row+1, types); 
				}
			}
			
		}
	}
	
	/**
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean rowCheck(int row, int col, int type){
		if(col<=1){
			return true;
		} else if(gems[row][col-1].type==type && gems[row][col-2].type==type){
			return false;
		} else{
			return true;
		}
	}
	
	/**
	 * @param row
	 * @param col
	 * @param type
	 * @return
	 */
	public boolean colCheck(int row, int col, int type){
		if(row<=1){
			return true;
		} else if(gems[row-1][col].type==type && gems[row-2][col].type==type){
			return false;
		} else{
			return true;
		}
	}

	/**
	 * @param g
	 * draws the board
	 */
	public void draw(Graphics g) {
		for (Gem[] gemss : gems)
    		for(Gem gem : gemss)
    				gem.draw(g);
	}

	/**
	 * @param row
	 * @param col
	 * Deletes a gem based on column and row, moves all the gems above this gem a place down
	 */
	public void delete(int row, int col) {
		gems[row][col].delete();
		
		//move all blocks above the deleted block down
        for(int r=row; r>=0; r--){
        	if(r>=1){
		        gems[r-1][col].setPosition(r, col);
		        gems[r][col]= gems[r-1][col];
        	}
        	else{
        		int type = random.nextInt(6)+1; //take a random number out of 1,2,3,4,5,6
    			Gem gem = new Gem(0, col, type);
                gems[0][col] = gem;
        	}
        }
	}
	
	/**
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 * swaps two gems
	 */
	public void swap(int row1, int col1, int row2, int col2){
		Gem tempgem = gems[row1][col1];
		gems[row2][col2].setPosition(row1, col1);
		gems[row1][col1] = gems[row2][col2];
		tempgem.setPosition(row2, col2);
		gems[row2][col2] = tempgem;
	}
	
	/**
	 * @param g
	 * @return
	 */
	public Gem getUpper(Gem g) {
		if(g.row > 0) {
			return(gems[g.row - 1][g.col]);
		}
		return null;
	}
	
	/**
	 * @param g
	 * @return
	 */
	public Gem getRight(Gem g) {
		if(g.col < 7) {
			return(gems[g.row][g.col + 1]);
		}
		return null;
	}
	
	/**
	 * @param g
	 * @return
	 */
	public Gem getBelow(Gem g) {
		if(g.row < 7) {
			return(gems[g.row + 1][g.col]);
		}
		return null;
	}
	
	/**
	 * @param g
	 * @return
	 */
	public Gem getLeft(Gem g) {
		if(g.col > 0) {
			return(gems[g.row][g.col-1]);
		}
		return null;
	}
	
	/**
	 * @param gem1
	 * @param gem2
	 * @return true if two gems are neighbours
	 */
	public boolean areNeighbours(Gem gem1, Gem gem2) {
		return(getUpper(gem1)==gem2 || getBelow(gem1)==gem2 || getLeft(gem1)==gem2 || getRight(gem1)==gem2);
	}
	
	/**
	 * Will delete all combinations of Gems, by first finding them with
	 * deleteHorizontal and deleteVertical and then deleting them with deleteAll.
	 * @param g
	 * @return true, iff there are rows to be deleted
	 */
	public boolean deleteRows(Gem g) {
		ArrayList<Gem> array1 = deleteHorizontal(g);
		ArrayList<Gem> array2 = deleteVertical(g);
		if(!array1.isEmpty()) {
			if(!array2.isEmpty()){
				array1.addAll(array2);
			}
		}
		else if(array2.isEmpty()){
			return false;
		}
		else {
			array1 = array2;
		}
		array1.add(g);
		deleteAll(array1);
		return true;
	}
	
	/**
	 * First sorts the arrayList array, then deletes all the Gems from the list
	 * @param array
	 */
	public void deleteAll(ArrayList<Gem> array) {
		ArrayList<Gem> array2 = new ArrayList<Gem>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < array.size(); j++) {
				Gem temp = array.get(j);
				if(temp.row == i) {
					array2.add(array.get(j));
				}
			}
		}
		for(int k = 0; k < array2.size(); k++) {
			delete(array2.get(k).row, array2.get(k).col);
		}
		for(int j = 0; j < 8; j++) {
			for (int k = 0; k < 8; k++) {
				Gem temp = gems[j][k];
				deleteRows(temp);
			}
		}
	}
	
	/**
	 * Finds all horizontal combinations of Gems with Gem g, places the gems in 
	 * these combinations in the ArrayList array
	 * @param g
	 * @return array
	 */
	public ArrayList<Gem> deleteHorizontal(Gem g) {
		int type1 = g.type;
		Gem leftgem = getLeft(g);
		Gem rightgem = getRight(g);
		ArrayList<Gem> array = new ArrayList<Gem>();
		
		if(leftgem != null && leftgem.type == type1){
			if(getLeft(leftgem) != null && getLeft(leftgem).type == type1){
				if(rightgem != null && rightgem.type == type1){
					if(getRight(rightgem) != null && getRight(rightgem).type == type1){
						array.add(getRight(rightgem));
					}
					array.add(rightgem);
				}
				array.add(getLeft(leftgem));
				array.add(leftgem);
			}
			else if(rightgem != null && rightgem.type == type1){
				if(getRight(rightgem) != null && getRight(rightgem).type == type1){
					array.add(getRight(rightgem));
				}
				array.add(rightgem);
				array.add(leftgem);
			}
		}
		else if(rightgem != null && getRight(rightgem) != null && rightgem.type == type1 && getRight(rightgem).type == type1) {
			array.add(getRight(rightgem));
			array.add(rightgem);
		}
		return array;
	}
	
	/**
	 * Finds all vertical combinations of Gems with Gem g, places the gems in 
	 * these combinations in the ArrayList array
	 * @param g
	 * @return array
	 */
	public ArrayList<Gem> deleteVertical(Gem g) {
		int type1 = g.type;
		Gem upgem = getUpper(g);
		Gem downgem = getBelow(g);
		ArrayList<Gem> array = new ArrayList<Gem>();
		
		if(upgem != null && upgem.type == type1){
			if(getUpper(upgem) != null && getUpper(upgem).type == type1){
				if(downgem != null && downgem.type == type1){
					if(getBelow(downgem) != null && getBelow(downgem).type == type1){
						array.add(getBelow(downgem));
					}
						array.add(downgem);
				}
				array.add(getUpper(upgem));
				array.add(upgem);
			}
			else if(downgem != null && downgem.type == type1){
				if(getBelow(downgem) != null && getBelow(downgem).type == type1){
					array.add(getBelow(downgem));
				}
				array.add(downgem);
				array.add(upgem);
			}
		}
		else if(downgem != null && getBelow(downgem) != null && downgem.type == type1 && getBelow(downgem).type == type1) {
			array.add(getBelow(downgem));
			array.add(downgem);
		}
		return array;
	}
	
}
