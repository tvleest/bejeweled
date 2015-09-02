import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{
	Random random =  new Random();
	
	Gem[][] gems = new Gem[8][8];
	
	public Board(){
		addMouseListener(this);
		
		int row,col;
        for (col=0;col<8;col++){
            for (row=0;row<8;row++){
            	int type = random.nextInt(6)+1; //take a random number out of 1,2,3,4,5,6
    			Gem gem = new Gem(row, col, type);
                gems[row][col] = gem;
            }
        }
	}

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        	for (Gem[] gemss : gems)
        		for(Gem gem : gemss)
        				gem.draw(g);
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		int xvar = e.getX();
        int yvar = e.getY();
        //calc which diamond is clicked
        int col = xvar/40;
        int row = yvar/40;
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
        repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
