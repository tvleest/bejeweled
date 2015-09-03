import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 * @author Timo
 * This class is our Panel, handeling mouseevents and drawing the game
 */
public class Game extends JPanel implements MouseListener{
	
	Board board = new Board(8);
	
	/**
	 * Constructor
	 */
	public Game(){
		addMouseListener(this); //binds the mouse to the JPanel
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        	board.draw(g);
    }

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		//get the coordinates of the mousepressed event
		int xvar = e.getX() - 235;
        int yvar = e.getY() - 115;
        //calculate which column and row are clicked, integers will be rounded down by default
        int col = xvar/40;
        int row = yvar/40;
        //check if the col and row are inside the board
        if (col < 8 && row < 8 && col >= 0 && row >= 0) {
        	if(board.selectedgem==null){ //first click, select the gem that is clicked
        		board.selectedgem=board.gems[row][col];
        		board.gems[row][col].setSelected(true);
        	}
        	else //second click, check if the gems are neighbours and can be swapped
        	{	
        		Gem clickedGem = board.gems[row][col];
        		if (board.areNeighbours(board.selectedgem, clickedGem)){
        			board.swap(board.selectedgem.row, board.selectedgem.col, row, col);
        		}
        		else{
        			//error sound;
        		}
        		board.selectedgem.setSelected(false);
        		board.selectedgem=null; //deselect
        	}
            repaint(); //update the panel
        }
    }

	@Override
	public void mouseReleased(MouseEvent e) {}
}
