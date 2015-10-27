package bejeweled.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

import bejeweled.Difficulties;
import bejeweled.Main;
import bejeweled.Sounds;
import bejeweled.board.Gem;
import bejeweled.gui.Buttons;
import bejeweled.gui.Popups;
import bejeweled.state.Score;

/**
 * @author Timo This class is our Panel, handling mouse events and drawing the
 *         game
 */
public final class GameScene extends Scene implements Observer {
	// TODO: public static Sounds GameSounds = new Sounds();

	// to use the sounds in this class
	private static GraphicsContext gc;
	private static GameLogic gamelogic;
	private static final int OFFSETX = 248;
	private static final int OFFSETY = 88;
	private Label score;
	private boolean easterAvailable = true;

	/**
	 * GameScene Constructor. Prepares the UI of the root and mouseclick
	 * handlers.
	 */
	public GameScene(Group root, Stage stage, Difficulties dif) {
		super(root);
		this.getStylesheets().add("Style.css");
		Canvas canvas = new Canvas(800, 600);
		root.getChildren().add(canvas);

		Image hintIcon = new Image("Images/hintbutton.png");
		Button hintButton = Buttons.subMenuButton(null, hintIcon, 80, 480);

		hintButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gamelogic.getBoard().hintCheck(0, 0, true);
			}
		});

		Image pauseIcon = new Image("Images/pause.png");
		Button pauseButton = Buttons.subMenuButton(null, pauseIcon, 120, 480);
		pauseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				showPopup(stage, root);
			}
		});

		root.getChildren().addAll(hintButton, pauseButton);
		gc = canvas.getGraphicsContext2D();
		gc.setFont(new Font("Helvetica", 15));
		gamelogic = new GameLogic(dif);
		gamelogic.getScoreObject().addObserver(gamelogic.getBoard());
		gamelogic.getScoreObject().addObserver(this);
		

		// this will handle mouse clicks
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				// get the coordinates of the mouse pressed event
				int xvar = (int) (e.getX() - OFFSETX);
				int yvar = (int) (e.getY() - OFFSETY);
				// calculate which column and row are clicked
				int col = xvar / Gem.getDimension();
				int row = yvar / Gem.getDimension();
				// check if the col and row are inside the board
				if (col < 8 && row < 8 && col >= 0 && row >= 0) {
					gamelogic.handleMouseClicked(row, col);
				}
			}
		});
		
		Circle circleScore = new Circle(120, 115, 40, Color.LIGHTGREY);
		circleScore.setStrokeWidth(2);
		circleScore.setStroke(Color.BLACK);
		root.getChildren().add(circleScore);
		
		score = new Label(""+0);
		score.setLayoutX(80);
		score.setLayoutY(100);
		score.setId("score");
		score.setPrefWidth(80);
		score.setMaxWidth(80);
		score.setAlignment(Pos.CENTER);


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
		if(easterAvailable && scoreObject.getScore() >= 1000) {
			easterAvailable = false;
			Sounds.getInstance().stopBackgroundSound();
			Sounds.getInstance().playRickRoll();
		}
	}
	
	public void showPopup(Stage stage, Group root) {
		if(Sounds.getInstance().backgroundSoundPlaying() || Sounds.getInstance().rickrollSoundPlaying()) {
			Popup popup = Popups.pausePopup(root, gamelogic, true);
			popup.show(stage);
		} else {
			Popup popup = Popups.pausePopup(root, gamelogic, false);
			popup.show(stage);
		}
	}

}
