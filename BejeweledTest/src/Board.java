import java.awt.Graphics;
import java.util.Random;

public class Board {
	Gem[][] gems;
	int dimension;
	Random random = new Random();
	
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
	
}
