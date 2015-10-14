package bejeweled.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

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
public final class GameScene extends Scene {
	// TODO: public static Sounds GameSounds = new Sounds();

	// to use the sounds in this class
	private static GraphicsContext gc;
	private static GameLogic gamelogic;
	private static final int offsetx = 235;
	private static final int offsety = 115;

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
		Button hintButton = Buttons.subMenuButton(null, hintIcon, 655, 565);

		hintButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gamelogic.getBoard().hintCheck(0, 0, true);
			}
		});

		Image pauseIcon = new Image("Images/pause.png");
		Button pauseButton = Buttons.subMenuButton(null, pauseIcon, 610, 565);
		pauseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Main.getTimeline().pause();
				Popup popup = Popups.pausePopup(root, gamelogic);
				popup.show(stage);
				root.setDisable(true);

			}
		});

		Image musicIcon = new Image("Images/music2.png");
		Button musicButton = Buttons.subMenuButton(null, musicIcon, 565, 565);
		
		Line mute = new Line();
		mute.setStartX(575);
		mute.setStartY(587);
		mute.setEndX(603);
		mute.setEndY(575);
		mute.setStrokeWidth(1.0);
		mute.setVisible(false);

		musicButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (Sounds.getInstance().backgroundSoundPlaying()) {
					Sounds.getInstance().stopBackgroundSound();
					mute.setVisible(true);
				} else {
					Sounds.getInstance().playBackgroundSound();
					mute.setVisible(false);
				}
			}
		});

		root.getChildren().addAll(hintButton, pauseButton, musicButton, mute);
		gc = canvas.getGraphicsContext2D();
		gc.setFont(new Font("Helvetica", 15));
		gamelogic = new GameLogic();
		gamelogic.getScoreObject().addObserver(gamelogic);
		gamelogic.getScoreObject().addObserver(gamelogic.getBoard());

		// this will handle mouse clicks
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				// get the coordinates of the mouse pressed event
				int xvar = (int) (e.getX() - offsetx);
				int yvar = (int) (e.getY() - offsety);
				// calculate which column and row are clicked
				int col = xvar / 40;
				int row = yvar / 40;
				// check if the col and row are inside the board
				if (col < 8 && row < 8 && col >= 0 && row >= 0) {
					gamelogic.handleMouseClicked(row, col);
				}
			}
		});
		draw();
	}

	/**
	 * requests the game logic to draw the UI.
	 */
	public static void draw() {
		gamelogic.draw(gc);
		gamelogic.getTime().drawTime(gc);
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
		return offsetx;
	}

	public static int getOffsety() {
		return offsety;
	}
	
	

}
