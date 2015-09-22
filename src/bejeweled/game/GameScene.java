package bejeweled.game;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import bejeweled.Main;
import bejeweled.state.Logger;
import bejeweled.state.Time;

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
	
	public void DrawPopup() { // Not shure if this is the right spot to place this code
		Time startTime = gamelogic.getTime();
		
		while (GameLogic.getTime()+1 <= startTime){ 
			// How do I get the current time en add 1 second to it?
					
		ArrayList<String> shouts = new ArrayList<>();
		shouts.add(1, "Good Job!");
		shouts.add(2, "Keep on going!");
		shouts.add(3, "Nice Work!");
		
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(shouts.size());
		String item = shouts.get(index);
				
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		 
		Text t = new Text();
		t.setEffect(ds);
		t.setCache(true);
		t.setX(150);
		t.setY(270);
		t.setFill(Color.GOLD);
		t.setText(item);
		t.setFont(Font.font("Arial", FontWeight.BOLD, 72));
		}
	}
	
	
}
