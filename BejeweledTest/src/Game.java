import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Timo
 * This class is our Panel, handling mouse events and drawing the game
 */
public class Game extends Scene{
	//TODO: public static Sounds GameSounds = new Sounds(); // to use the sounds in this class
	Board board = new Board(8);
	GraphicsContext gc;
	
	private int score;
	private int scorePerGem = 10; //TODO: implement score per gem
	
	private int time;
	private final int TIMEPERGEM = 5;
	private final int DELAY = 1000;
	
	/**
	 * Constructor
	 */
	public Game(Group root){
		super(root);
		Canvas canvas = new Canvas( 800, 600 );
   		root.getChildren().add( canvas );
   		gc = canvas.getGraphicsContext2D();
   		this.setOnMousePressed(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                    	//get the coordinates of the mouse pressed event
                		int xvar = (int) (e.getX() - 235);
                        int yvar = (int) (e.getY() - 115);
                        //calculate which column and row are clicked, integers will be rounded down by default
                        int col = xvar/40;
                        int row = yvar/40;
                        //check if the col and row are inside the board
                        if (col < 8 && row < 8 && col >= 0 && row >= 0) {
                        	handleMouseClicked(row, col);
                        }
                    }
                });
        draw();

		score = 0;
		time = 90;
	}
	
	public void draw() {
			board.draw(gc);
			drawScore();
			drawTime();
	    }

	private void handleMouseClicked(int row, int col) {
//		Game.GameSounds.playAudio("select");
    	if(board.selectedgem==null){ //first click, select the gem that is clicked
    		board.selectedgem=board.gems[row][col];
    		board.gems[row][col].setSelected(true);
    	}
    	else //second click, check if the gems are neighbors and can be swapped
    	{	
    		board.secondGem = board.gems[row][col];
    		if (board.swap(board.selectedgem.getRow(), board.selectedgem.getCol(), row, col)){
    			boolean first = board.deleteRows(board.selectedgem); 
    			boolean second = board.deleteRows(board.secondGem);
        		if (first) {
        			updateScore();
        			updateTime();
        		}
    			if(first == false && second == false) { //if there are no combinations found after the move
    				board.swap(board.secondGem.getRow(), board.secondGem.getCol(), row, col); //switches the two switched gems back
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
	}

	public void updateScore() {
		score += scorePerGem;
	}
	
	public int getScore() {
		return score;
	}
	
	public void drawScore() {
		String s = "Score: ";
		s += score;
	    gc.fillText( s, 60, 80 );
	}
	
	public void updateTime() {
		time += TIMEPERGEM;
	}
	
	public int getTime() {
		return time;
	}
	
	public void drawTime() {
		String s = "Time left: ";
		int minutes = time / 60;
		int seconds = time % 60;
		if(seconds < 10) {
			s += minutes + ":" + 0 + seconds;
		} else {
			s += minutes + ":" + seconds;
		}
		gc.fillText(s, 60, 60 );
	}
}
