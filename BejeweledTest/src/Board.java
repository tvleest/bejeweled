import java.awt.Graphics;
import java.util.Random;

public class Board {
	Gem[][] gems;
	int dimension;
	Random random = new Random();
	Gem selectedgem = null;
	
	public Board(int dimension){
		this.dimension = dimension;
		gems = new Gem[dimension][dimension];
		fillBoard();
	}
	
	public void fillBoard(){
		int row,col;
        for (col=0;col<dimension;col++){
            for (row=0;row<8;row++){
            	int type = random.nextInt(6)+1; //take a random number out of 1,2,3,4,5,6
    			Gem gem = new Gem(row, col, type);
                gems[row][col] = gem;
            }
        }
	}
	
	public boolean rowCheck(int row, int col, int type){
		if(col<=1){
			return true;
		} else if(gems[row][col-1].type==type && gems[row][col-2].type==type){
			return false;
		} else{
			return true;
		}
	}
	
	public boolean colCheck(int row, int col, int type){
		if(row<=1){
			return true;
		} else if(gems[row-1][col].type==type && gems[row-2][col].type==type){
			return false;
		} else{
			return true;
		}
	}

	public void draw(Graphics g) {
		for (Gem[] gemss : gems)
    		for(Gem gem : gemss)
    				gem.draw(g);
	}

	public void delete(int row, int col) {
		gems[row][col].delete();
		
		//this should probably get its own method
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
	
	public void swap(int row1, int col1, int row2, int col2){
		Gem tempgem = gems[row1][col1];
		gems[row2][col2].setPosition(row1, col1);
		gems[row1][col1] = gems[row2][col2];
		tempgem.setPosition(row2, col2);
		gems[row2][col2] = tempgem;
	}
	
}
