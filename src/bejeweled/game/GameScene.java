package bejeweled.game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import bejeweled.Main;
import bejeweled.board.Board;
import bejeweled.board.Gem;
import bejeweled.gui.Buttons;
import bejeweled.state.Logger;
import bejeweled.state.Time;

/**
 * @author Timo
 * This class is our Panel, handling mouse events and drawing the game
 */
public class GameScene extends Scene {
//TODO: public static Sounds GameSounds = new Sounds();

	
//to use the sounds in this class
	private static GraphicsContext gc;
	private static GameLogic gamelogic;
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
		
   		Image saveIcon = new Image("Images/save2.png");
   		Button saveButton = Buttons.subMenuButton(null, saveIcon, 700, 565);
		
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Time time = GameLogic.getTime();
				String stime = time.toString().substring(11, time.toString().length());
				int score = Board.getScore();
				Gem[][] board = Board.getGems();
				String save = stime + "\n" + score + "\n";
				System.out.println(board.length);
				for(int row = 0; row < board.length; row++) {
					for(int col = 0; col < board.length; col++) {
						save += board[row][col].getType() + "\n";
					}
				}
				try {
					FileWriter fw = new FileWriter("savefile.txt");
					fw.write(save);
					fw.close();
				} catch (IOException e1) {
					System.out.println("Something went wrong with the FileWriter in GameScene");
				}
			}
		});
		
		Image hintIcon = new Image("Images/hintbutton.png");
		Button hintButton = Buttons.subMenuButton(null, hintIcon, 655, 565);
		
		hintButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//VOOR DE HINT FEATURE
				//Highlight gem that can be moved on the board
			}
		});
		
		root.getChildren().addAll(hintButton, saveButton);
   		gc = canvas.getGraphicsContext2D();
   		gc.setFont(new Font("Helvetica", 15));
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
