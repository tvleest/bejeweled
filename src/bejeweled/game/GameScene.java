package bejeweled.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import bejeweled.Main;
import bejeweled.Sounds;
import bejeweled.board.Board;
import bejeweled.board.Gem;
import bejeweled.gui.Buttons;
import bejeweled.gui.Popups;
import bejeweled.state.Score;
import bejeweled.state.Time;

/**
 * @author Timo This class is our Panel, handling mouse events and drawing the
 *         game
 */
public final class GameScene extends Scene implements Observer {
	// TODO: public static Sounds GameSounds = new Sounds();

	// to use the sounds in this class
	private static GraphicsContext gc;
	private static GameLogic gamelogic;
	private static final int OFFSETX = 235;
	private static final int OFFSETY = 115;
	Label score;

	/**
	 * GameScene Constructor. Prepares the UI of the root and mouseclick
	 * handlers.
	 */
	public GameScene(Group root, Stage stage) {
		super(root);
		this.getStylesheets().add("Style.css");
		Canvas canvas = new Canvas(800, 600);
		root.getChildren().add(canvas);

		Image hintIcon = new Image("Images/hintbutton.png");
		Button hintButton = Buttons.subMenuButton(null, hintIcon, 80, 440);

		hintButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gamelogic.getBoard().hintCheck(0, 0, true);
			}
		});

		Image pauseIcon = new Image("Images/pause.png");
		Button pauseButton = Buttons.subMenuButton(null, pauseIcon, 120, 440);
		pauseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Main.getTimeline().pause();
				Popup popup = Popups.pausePopup(root, gamelogic);
				popup.show(stage);
				root.setDisable(true);

			}
		});

		root.getChildren().addAll(hintButton, pauseButton);
		gc = canvas.getGraphicsContext2D();
		gc.setFont(new Font("Helvetica", 15));
		gamelogic = new GameLogic();
		gamelogic.getScoreObject().addObserver(gamelogic.getBoard());
		gamelogic.getScoreObject().addObserver(this);
		

		// this will handle mouse clicks
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				// get the coordinates of the mouse pressed event
				int xvar = (int) (e.getX() - OFFSETX);
				int yvar = (int) (e.getY() - OFFSETY);
				// calculate which column and row are clicked
				int col = xvar / 40;
				int row = yvar / 40;
				// check if the col and row are inside the board
				if (col < 8 && row < 8 && col >= 0 && row >= 0) {
					gamelogic.handleMouseClicked(row, col);
				}
			}
		});
		
		score = new Label(""+0);
		score.setLayoutX(110);
		score.setLayoutY(85);
		score.setId("score");
		root.getChildren().add(score);
		
		draw();
	}

	/**
	 * requests the game logic to draw the UI.
	 */
	public static void draw() {
		gamelogic.draw(gc);
	}

	/**
	 * requests to decrement the timer (each second).
	 */
	public void decrementTime() {
		gamelogic.getTime().decrementTime();
	}

	public GameLogic getGameLogic() {
		return gamelogic;
	}

	public GraphicsContext getGraphicsContext() {
		return gc;
	}

	public static int getOffsetx() {
		return OFFSETX;
	}

	public static int getOffsety() {
		return OFFSETY;
	}

	@Override
	public void update(Observable obs, Object arg) {
		Score scoreObject = (Score) obs;
		score.setText(scoreObject.getScore()+"");
	}
	
	

}
