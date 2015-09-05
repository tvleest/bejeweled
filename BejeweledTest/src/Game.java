import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;


/**
 * @author Timo
 * This class is our Panel, handling mouse events and drawing the game
 */
public class Game extends JPanel implements MouseListener{
	public static Sounds GameSounds = new Sounds(); // to use the sounds in this class
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
		//get the coordinates of the mouse pressed event
		int xvar = e.getX() - 235;
        int yvar = e.getY() - 115;
        //calculate which column and row are clicked, integers will be rounded down by default
        int col = xvar/40;
        int row = yvar/40;
        //check if the col and row are inside the board
        if (col < 8 && row < 8 && col >= 0 && row >= 0) {
        	Game.GameSounds.playAudio("select");
        	if(board.selectedgem==null){ //first click, select the gem that is clicked
        		board.selectedgem=board.gems[row][col];
        		board.gems[row][col].setSelected(true);
        	}
        	else //second click, check if the gems are neighbors and can be swapped
        	{	
        		board.secondGem = board.gems[row][col];
        		if (board.areNeighbours(board.selectedgem, board.secondGem)){
        			board.swap(board.selectedgem.row, board.selectedgem.col, row, col);
        			boolean first = board.deleteRows(board.selectedgem);
        			boolean second = board.deleteRows(board.secondGem);
        			if(first == false && second == false) { //if there are no combinations found after the move
        				board.swap(board.secondGem.row, board.secondGem.col, row, col);
        				//error sound
        			}
        		}
        		else{
        			//error sound;
        		}
        		board.selectedgem.setSelected(false);
        		board.selectedgem=null; //deselect gem
        		board.secondGem=null; 
        	}
            repaint(); //update the panel
        }
    }

	@Override
	public void mouseReleased(MouseEvent e) {}
}
