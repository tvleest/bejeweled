import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * @author Timo
 * This class is our Panel, handling mouse events and drawing the game
 */
public class GameScene extends Scene {
//TODO: public static Sounds GameSounds = new Sounds();

	
//to use the sounds in this class
	GraphicsContext gc;
	private GameLogic gamelogic;
	public final int OFFSETX = 235;
	public final int OFFSETY = 115;
	
	/**
	 * GameScene Constructor.
	 * Prepares the UI of the root and mouseclick handlers.
	 */
	public GameScene(Group root, Main main) {
		super(root);
		Canvas canvas = new Canvas(800, 600);
   		root.getChildren().add(canvas);
   		gc = canvas.getGraphicsContext2D();
   		gamelogic = new GameLogic(OFFSETX, OFFSETY, main, true);
   		
   		//this will handle mouseclicks
   		this.setOnMousePressed(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e) {
                    	//get the coordinates of the mouse pressed event
                		int xvar = (int) (e.getX() - OFFSETX);
                        int yvar = (int) (e.getY() - OFFSETY);
                        //calculate which column and row are clicked
                        int col = xvar / 40;
                        int row = yvar / 40;
                        //check if the col and row are inside the board
                        if (col < 8 && row < 8 && col >= 0 && row >= 0) {
                        	gamelogic.handleMouseClicked(row, col);
                        }
                    }
                });
        draw();
	}
	
	/**
	 * requests the gamelogic to draw the UI.
	 */
	public final void draw() {
			gamelogic.draw(gc);
			gamelogic.getTime().drawTime(gc);
	    }
	
	/**
	 * requests to decrement the timer (each second).
	 */
	public final void decrementTime() {
		gamelogic.getTime().decrementTime();
	}
	
	public GameLogic getGameLogic() {
		return gamelogic;
	}
}
