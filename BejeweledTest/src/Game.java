import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * @author Timo
 * This class is our Panel, handling mouse events and drawing the game
 */
public class Game extends JPanel implements MouseListener, ActionListener{
	public static Sounds GameSounds = new Sounds(); // to use the sounds in this class
	Board board = new Board(8);
	private int score;
	private int threeScore = 10;
	private int fourScore = 15;
	
	private Timer timer;
	private int time;
	private final int THREETIME = 5;
	private final int DELAY = 1000;
	
	/**
	 * Constructor
	 */
	public Game(){
		addMouseListener(this); //binds the mouse to the JPanel
		score = 0;
		time = 90;
		timer = new Timer(DELAY, this);
        timer.start();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        	board.draw(g);
        	drawScore(g);
        	drawTime(g);
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
            		if (first) {
            			updateScore();
            			updateTime();
            		}
        			if(first == false && second == false) { //if there are no combinations found after the move
        				board.swap(board.secondGem.row, board.secondGem.col, row, col); //switches the two switched gems back
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
	
	public void updateScore() {
		score += threeScore;
	}
	
	public int getScore() {
		return score;
	}
	
	public void drawScore(Graphics g) {
		String s = "Score: ";
		s += score;
		g.setFont(new Font("Cambria", Font.BOLD, 14));
		g.drawString(s, 480, 460);
	}
	
	public void updateTime() {
		time += THREETIME;
	}
	
	public int getTime() {
		return time;
	}
	
	public void drawTime(Graphics g) {
		String s = "Time left: ";
		int minutes = time / 60;
		int seconds = time % 60;
		if(seconds < 10) {
			s += minutes + ":" + 0 + seconds;
		} else {
			s += minutes + ":" + seconds;
		}
		g.drawString(s, 245, 460);
        Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		time -= 1;
		if(time < 1) {
			int reply = JOptionPane.showConfirmDialog(this, "Time's up!\n Would you like to start a new game?", "Game Over", JOptionPane.YES_NO_OPTION);
			if(reply == JOptionPane.OK_OPTION) {
				
			}
			if(reply == JOptionPane.NO_OPTION) {
				
			}
		}
		repaint();
	}
}
