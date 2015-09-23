package bejeweled.game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;

import bejeweled.Main;
import bejeweled.gui.Buttons;
import bejeweled.state.Logger;

/**
 * @author Timo
 * This class is our Panel, handling mouse events and drawing the game
 */
public class GameScene extends Scene {
//TODO: public static Sounds GameSounds = new Sounds();

	
//to use the sounds in this class
	private GraphicsContext gc;
	private GameLogic gamelogic;
	public final int OFFSETX = 235;
	public final int OFFSETY = 115;
	
	/**
	 * GameScene Constructor.
	 * Prepares the UI of the root and mouseclick handlers.
	 */
	public GameScene(Group root) {
		super(root);
		Canvas canvas = new Canvas(800, 600);
   		root.getChildren().add(canvas);
		
   		Image saveIcon = new Image("Images/save.png");
   		Button saveButton = Buttons.subMenuButton(null, saveIcon, 700, 565);
		
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//Write gamestate to file to save the game.
			}
		});
		
		root.getChildren().add(saveButton);
   		gc = canvas.getGraphicsContext2D();
   		gamelogic = new GameLogic(OFFSETX, OFFSETY, true);
   		
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
	
	public GraphicsContext getGraphicsContext() {
		return gc;
	}
}
